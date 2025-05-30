/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class AddRemovePartner extends ContentPanelStructure {

    private InfDB idb;
    private String id;
    private JComboBox<String> comboBox;
    private JButton btnAdd;
    private JPanel mainPanel;

    public AddRemovePartner(User user, UIStructure newPanel) {
        super(user, newPanel);
        try {
            setBackground(Color.gray);
            id = user.getId();
            idb = user.getDb();

            mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(mainPanel);
            add(scrollPane);

            loadData();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void loadData() {
        try {
            mainPanel.removeAll();

            ArrayList<String> onlyProj = idb.fetchColumn("select distinct projekt.pid from projekt_partner join projekt on projekt_partner.pid = projekt.pid where projektchef = '" + id + "'");
            for (int i = 0; i < onlyProj.size(); i++) {
                JPanel partnerPanel = new JPanel();
                partnerPanel.setLayout(new BoxLayout(partnerPanel, BoxLayout.Y_AXIS));

                String ettID = onlyProj.get(i);

                JTextField projectID = new JTextField("Project " + ettID);
                projectID.setEditable(false);
                partnerPanel.add(projectID);

                ArrayList<HashMap<String, String>> onlyPart = idb.fetchRows("select projekt.pid, projektnamn, partner_pid, namn from projekt_partner join partner on partner_pid = partner.pid join projekt on projekt_partner.pid = projekt.pid where projektchef = '" + id + "' and projekt.pid = '" + ettID + "'");
                for (HashMap<String, String> partners : onlyPart) {

                    String partnerId = partners.get("partner_pid");

                    JTextField partnername = new JTextField(partners.get("namn"), 60);
                    partnername.setEditable(false);
                    partnerPanel.add(partnername);

                    removeButton(ettID, partnerId, partnerPanel);
                }
                addButton(ettID, partnerPanel);
                mainPanel.add(partnerPanel);
            }
            mainPanel.revalidate();
            mainPanel.repaint();
        } catch (InfException error) {
            System.out.println(error.getMessage());
        }

    }

    public void addButton(String id, JPanel panel) {
        String projektID = id;
        JPanel partnerPanel = panel;
        btnAdd = new JButton("Add partner");
        btnAdd.addActionListener(e -> {
            try {
                if (comboBox == null) {

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                    ArrayList<String> allPartner = idb.fetchColumn("select namn from partner");
                    for (String row : allPartner) {
                        model.addElement(row);
                    }
                    comboBox = new JComboBox(model);
                    comboBox.setMinimumSize(new Dimension(10, 30));
                    comboBox.setPreferredSize(new Dimension(10, 30));

                    JButton btnConfirm = new JButton("Confirm");
                    btnConfirm.addActionListener(action -> {
                        try {
                            String selectedPartner = (String) comboBox.getSelectedItem();
                            String choice = idb.fetchSingle("select pid from partner where namn = '" + selectedPartner + "'");

                            String checkQuery = "select * from projekt_partner where pid = " + projektID + " and partner_pid = " + choice;
                            if (idb.fetchRows(checkQuery).isEmpty()) {
                                String query = "insert into projekt_partner (pid, partner_pid) values (" + projektID + ", " + choice + ")";
                                idb.insert(query);
                                System.out.println("New partner is added!");
                                comboBox = null;
                                loadData();
                            } else {
                                System.out.println("Partner is already existing!");
                            }
                        } catch (InfException ee) {
                            System.out.println(ee.getMessage());
                        }
                    });
                    partnerPanel.add(comboBox);
                    partnerPanel.add(btnConfirm);

                    partnerPanel.revalidate();
                    partnerPanel.repaint();
                }

            } catch (InfException exception) {
                System.out.println(exception.getMessage());
            }
        });
        partnerPanel.add(btnAdd);
    }

    public void removeButton(String projId, String partId, JPanel panel) {
        JPanel partnerPanel = panel;
        String projektID = projId;
        String partnerID = partId;
        JButton btnRemove = new JButton("Remove partner");
        btnRemove.addActionListener(e -> {
            try {
                String query = "delete from projekt_partner where pid = '" + projektID + "' and partner_pid = '" + partnerID + "'";
                idb.delete(query);
                System.out.println("Partner removed.");
                loadData();
            } catch (InfException error) {
                System.out.println(error.getMessage());
            }
        });
        partnerPanel.add(btnRemove);
    }

}
