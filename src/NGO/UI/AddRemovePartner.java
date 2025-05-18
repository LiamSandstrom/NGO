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
import javax.swing.JButton;
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
            //ArrayList<HashMap<String, String>> allPartners = idb.fetchRows("select * from partner join stad on stad = sid join projekt_partner on partner.pid = projekt_partner.partner_pid join projekt on projekt_partner.pid = projekt.pid where projektchef = '" + id + "'");
            ArrayList<HashMap<String, String>> allPartners = idb.fetchRows("select projekt_partner.pid, projektnamn, partner_pid, namn from projekt_partner join partner on partner_pid = partner.pid join projekt on projekt_partner.pid = projekt.pid where projektchef = '" + id + "'");
            for(HashMap<String, String> partners : allPartners){
                JPanel partnerPanel = new JPanel();
                partnerPanel.setLayout(new BoxLayout(partnerPanel, BoxLayout.Y_AXIS));
                String partnerID = partners.get("pid");
                //partnerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Project: " + partners.get("projekt.pid")));
                
                JTextField projectid = new JTextField(partners.get("projekt_partner.pid"), 60);
                JTextField projectname = new JTextField(partners.get("projektnamn"), 60);
                
                for(HashMap<String, String> partner : allPartners){
                    JTextField partnerid = new JTextField(partner.get("partner_pid"), 60);
                    JTextField partnername = new JTextField(partner.get("namn"), 60);
                
                
                    /*JButton btnSave = new JButton("Save my changes");
                    btnSave.addActionListener(e -> {
                        try{
                            String query = "update partner set partner.namn = '" + partnername.getText()
                                +"', kontaktperson = '" + kontactperson.getText()
                                +"', kontaktepost = '" + kontactemail.getText()
                                +"', telefon = '" + phone.getText()
                                +"', adress = '" + addres.getText()
                                +"', branch = '" + branch.getText()
                                //+"', stad.namn = '" + city.getText()
                                +"' where pid = '" + partnerID + "'";
                            idb.update(query);
                        }catch(InfException error){
                            System.out.println(error.getMessage());
                        }
                    });*/
                    projectid.setEditable(false);
                    partnerPanel.add(projectid);
                    projectname.setEditable(false);
                    partnerPanel.add(projectname);
                    partnerid.setEditable(false);
                    partnerPanel.add(partnerid);
                    partnername.setEditable(false);
                    partnerPanel.add(partnername);
                    
                    mainPanel.add(partnerPanel);
                }
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
