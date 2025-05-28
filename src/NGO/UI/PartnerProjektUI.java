/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Dimension;
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
public class PartnerProjektUI extends SettingsPanelFramework{
    private InfDB idb;
    private User user;
    private String id;
    private JComboBox<String> comboBox;
    private JButton btnAdd;
    //private JPanel mainPanel;
    
    public PartnerProjektUI(User user, String id) {
        super(user, id);
        this.user = user;
        this.idb = user.getDb();
        this.id = id;
        //mainPanel = new JPanel();
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //JScrollPane scrollPane = new JScrollPane(mainPanel);
        //add(scrollPane);

        //loadData();
        
        getProjects(id);
        
    }
    
    private void getProjects(String userid){
        try{
            ArrayList<String> projList = idb.fetchColumn("select pid from projekt where projektchef = '" + userid + "'");
            for(String pid : projList){
                addInfo(" ", pid);
                getPartners(pid);
            }
            setInfo();
        }catch(InfException e){
            JOptionPane.showMessageDialog(this, e);
        }
        
        
    }
    
    private void getPartners(String pid){
        try{
            ArrayList<String> partners = idb.fetchColumn("select namn from projekt_partner join partner on partner_pid = partner.pid join projekt on projekt_partner.pid = projekt.pid where projekt.pid = '" + pid + "'");
            for(String part : partners){
                addInfo(" ", part);
                String partID = idb.fetchSingle("select pid from partner where namn = '" + part + "'");
                removeButton(pid, partID);
                addButton(pid);
            }
            setInfo();
        }catch(InfException e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void removeButton(String projId, String partId) {
        //JPanel partnerPanel = panel;
        String projektID = projId;
        String partnerID = partId;
        JButton btnRemove = new JButton("Remove partner");
        btnRemove.addActionListener(e -> {
            try {
                String query = "delete from projekt_partner where pid = '" + projektID + "' and partner_pid = '" + partnerID + "'";
                idb.delete(query);
                System.out.println("Partner removed.");
                //loadData();
            } catch (InfException error) {
                System.out.println(error.getMessage());
            }
        });
        add(btnRemove);
        //partnerPanel.add(btnRemove);
    }
    
    private void addButton(String id) {
        String projektID = id;
        //JPanel partnerPanel = panel;
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
                                //loadData();
                            } else {
                                System.out.println("Partner is already existing!");
                            }
                        } catch (InfException ee) {
                            System.out.println(ee.getMessage());
                        }
                    });
                    add(comboBox);
                    add(btnConfirm);

                    revalidate();
                    repaint();
                }

            } catch (InfException exception) {
                System.out.println(exception.getMessage());
            }
        });
        add(btnAdd);
    }
}
