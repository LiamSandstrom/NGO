/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
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
    
    public AddRemovePartner(User user, UIStructure newPanel){
        super(user, newPanel);
        try{
            setBackground(Color.gray);
            id = user.getId();
            idb = user.getDb();
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            
            ArrayList<HashMap<String, String>> allPartners = idb.fetchRows("select projekt.pid, projektnamn, partner_pid, namn from projekt_partner join partner on partner_pid = partner.pid join projekt on projekt_partner.pid = projekt.pid where projektchef = '" + id + "'");
            for(HashMap<String, String> partners : allPartners){
                JPanel partnerPanel = new JPanel();
                partnerPanel.setLayout(new BoxLayout(partnerPanel, BoxLayout.Y_AXIS));
                String projektID = partners.get("pid");
                String partnerID = partners.get("partner_pid");
                JTextField projectid = new JTextField(partners.get("pid"), 60);
                JTextField projectname = new JTextField(partners.get("projektnamn"), 60);
                JTextField partnerid = new JTextField(partners.get("partner_pid"), 60);
                JTextField partnername = new JTextField(partners.get("namn"), 60);
                
                
                JButton btnRemove = new JButton("Remove partner");
                btnRemove.addActionListener(e -> {
                    try{
                        String query = "delete from projekt_partner where pid = '" + projektID + "' and partner_pid = '" + partnerID + "'";
                        idb.delete(query);
                        revalidate();
                        repaint();
                    }catch(InfException error){
                        System.out.println(error.getMessage());
                    }
                });
                
                JButton btnAdd = new JButton("Add partner");
                btnAdd.addActionListener(e -> {
                    try{
                        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                        ArrayList<String> allPartner = idb.fetchColumn("select namn from partner");
                        for(String row : allPartner){
                            model.addElement(row);
                        }
                        JComboBox<String> comboBox = new JComboBox(model);
                        JButton btnConfirm = new JButton("Confirm");
                        
                        btnConfirm.addActionListener( action -> {
                            try{
                                String selectedPartner = (String) comboBox.getSelectedItem();
                                String choice = idb.fetchSingle("select pid from partner where namn = '" + selectedPartner + "'");
                                
                                String checkQuery = "select * from projekt_partner where pid = " + projektID + " and partner_pid = " + choice;
                                if(idb.fetchRows(checkQuery).isEmpty()){
                                    String query = "insert into projekt_partner (pid, partner_pid) values (" + projektID + ", " + choice + ")";
                                    idb.insert(query);
                                    System.out.println("New partner is added!");
                                }else{
                                    System.out.println("Partner is already existing!");
                                }
                            }catch(InfException ee){
                                System.out.println(ee.getMessage());
                            }
                        });
                        partnerPanel.add(comboBox);
                        partnerPanel.add(btnConfirm);
                        partnerPanel.revalidate();
                        partnerPanel.repaint();
                    }catch(InfException exception){
                        System.out.println(exception.getMessage());
                    }
                });
                
                projectid.setEditable(false);
                partnerPanel.add(projectid);
                projectname.setEditable(false);
                partnerPanel.add(projectname);
                partnerid.setEditable(false);
                partnerPanel.add(partnerid);
                partnername.setEditable(false);
                partnerPanel.add(partnername);
                partnerPanel.add(btnRemove);
                partnerPanel.add(btnAdd);
                    
                mainPanel.add(partnerPanel);
                
            }
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            add(scrollPane);
            revalidate();
            repaint();
            
            
        }catch(InfException e){
            System.out.println(e);
        }
        
    }
    
}
