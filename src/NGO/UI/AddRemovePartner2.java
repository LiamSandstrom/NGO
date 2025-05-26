/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class AddRemovePartner2 extends SettingsPanelFramework {

    private InfDB idb;
    private String id;
    private User user;
    private JComboBox<String> comboBox;
    private JButton btnAdd;
    private JPanel mainPanel;

    public AddRemovePartner2(User user, String id) {
        super(user, id);
        this.id = user.getId();
        this.user = user;
        this.idb = user.getDb();
        
        getContentPanel().setLayout(new GridBagLayout());

        initCustomComponents();
        loadData();

    }

    private void initCustomComponents() {
        
        btnAdd = new JButton("Add partner");
        btnAdd.setPreferredSize(new Dimension(120, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1000;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        btnAdd.addActionListener(e -> {
            showAddPartnerComboBox();
        });

        getContentPanel().add(btnAdd, gbc);
    }

    private void showAddPartnerComboBox() {
        if (comboBox == null) {
            try {
                ArrayList<String> allPartners = idb.fetchColumn("select namn from partner");
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                for (String p : allPartners) {
                    model.addElement(p);
                }
                comboBox = new JComboBox<>(model);
                comboBox.setPreferredSize(new Dimension(200, 30));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 1001;
                gbc.insets = new Insets(5, 0, 10, 0);
                gbc.anchor = GridBagConstraints.CENTER;
                getContentPanel().add(comboBox, gbc);
                revalidate();
                repaint();

                comboBox.addActionListener(ev -> {
                    String selected = (String) comboBox.getSelectedItem();
                    if (selected != null) {
                        addPartner(selected);
                        removeComboBox();
                    }
                });

            } catch (InfException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void removeComboBox() {
        if (comboBox != null) {
            getContentPanel().remove(comboBox);
            comboBox = null;
            revalidate();
            repaint();

        }
    }

    private void addPartner(String partnerName) {
        try {
            System.out.println("ett id" + id);
            
            String partnerId = idb.fetchSingle("select pid from partner where namn = '" + partnerName + "'");
            ArrayList<String> projektIdList = idb.fetchColumn("select pid from projekt where projektchef = '" + id + "'");
            
            System.out.println("size " + projektIdList.size());
            System.out.println("content" + projektIdList);
            
            if (projektIdList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "no projects");
                return;
            }

            for (String projektID : projektIdList) {
                String checkQuery = "select * from projekt_partner where projekt_partner.pid  = " + projektID + " and partner_pid = " + partnerId;
                if (idb.fetchRows(checkQuery).isEmpty()) {
                    String insertQuery = "insert into projekt_partner (pid, partner_pid) values (" + projektID + ", " + partnerId + ")";
                    idb.insert(insertQuery);
                    
                }
            }
            JOptionPane.showMessageDialog(this, "partner tillagd");
            loadData();

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "error");
        }

    }

    private void loadData() {
        try {
            getInfoMap().clear();
            getContentPanel().removeAll();

            ArrayList<String> projectIds = idb.fetchColumn("select pid from projekt where projektchef = '" + id + "'");
            int counter = 0;
            for (String pid : projectIds) {
                System.out.println("ettpid "+ pid);
                String projName = idb.fetchSingle("select projektnamn from projekt where pid = '" + pid + "'");
                System.out.println("namn " + projName);
                addInfo("Projekt ", projName);
                ArrayList<HashMap<String, String>> partners = idb.fetchRows("select partner_pid, namn from projekt_partner join partner on partner_pid = partner.pid where projekt_partner.pid = '" + pid + "'");
                System.out.println("content " + partners);
                if (partners != null && !partners.isEmpty()) {
                    for (HashMap<String, String> p : partners) {
                        String partnerId = p.get("partner_pid");
                        String namn = p.get("namn");

                        JButton btnRemove = new JButton("Remove");
                        btnRemove.setPreferredSize(new Dimension(90, 30));
                        btnRemove.setBackground(Color.red);

                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.gridx = 1;
                        gbc.gridy = counter;
                        gbc.insets = new Insets(5, 5, 5, 5);
                        gbc.anchor = GridBagConstraints.EAST;
                        final String currentPid= pid;
                        final String currentPartnerId = partnerId;

                        btnRemove.addActionListener(ev -> {
                            try {
                                String delQuery = "delete from projekt_partner where projekt_partner.pid = '" + currentPid + "' and partner_pid = '" + currentPartnerId + "'";
                                idb.delete(delQuery);
                                JOptionPane.showMessageDialog(this, "partner deleted");
                                loadData();

                            } catch (InfException ex) {
                                JOptionPane.showMessageDialog(this, "error");
                            }
                        });
                        getContentPanel().add(btnRemove, gbc);
                        counter++;

                    }

                } else {
                    addInfo("partner : ", "No partners");
                }
                counter++;

            }
            GridBagConstraints gbcBtn = new GridBagConstraints();
            gbcBtn.gridx = 0;
            gbcBtn.gridy = counter + 1;
            gbcBtn.insets = new Insets(20, 0, 10, 0);
            gbcBtn.anchor = GridBagConstraints.CENTER;

            getContentPanel().add(btnAdd, gbcBtn);

            setInfo();

            revalidate();
            repaint();

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "error");
        }
    }

}
