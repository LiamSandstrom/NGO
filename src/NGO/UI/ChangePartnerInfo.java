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
public class ChangePartnerInfo extends ContentPanelStructure {
    private InfDB idb;
    private String id;
    
    public ChangePartnerInfo(User user, UIStructure newPanel){
        super(user, newPanel);
        try{
            setBackground(Color.gray);
            id = user.getId();
            idb = user.getDb();
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            ArrayList<HashMap<String, String>> allPartners = idb.fetchRows("select * from partner join stad on stad = sid join projekt_partner on partner.pid = projekt_partner.partner_pid join projekt on projekt_partner.pid = projekt.pid where projektchef = '" + id + "'");
            for(HashMap<String, String> partners : allPartners){
                JPanel partnerPanel = new JPanel();
                partnerPanel.setLayout(new BoxLayout(partnerPanel, BoxLayout.Y_AXIS));
                String partnerID = partners.get("pid");
                //partnerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Project: " + partners.get("projekt.pid")));
                
                JTextField partnerid = new JTextField(partners.get("pid"), 60);
                JTextField partnername = new JTextField(partners.get("namn"), 60);
                JTextField kontactperson = new JTextField(partners.get("kontaktperson"), 60);
                JTextField kontactemail = new JTextField(partners.get("kontaktepost"), 60);
                JTextField phone = new JTextField(partners.get("telefon"), 60);
                JTextField addres = new JTextField(partners.get("adress"), 60);
                JTextField branch = new JTextField(partners.get("branch"), 60);
                //JTextField city = new JTextField(partners.get("stad.namn"), 60);
                
                JButton btnSave = new JButton("Save my changes");
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
                });
                partnerid.setEditable(false);
                partnerPanel.add(partnerid);
                partnerPanel.add(partnername);
                partnerPanel.add(kontactperson);
                partnerPanel.add(kontactemail);
                partnerPanel.add(phone);
                partnerPanel.add(addres);
                partnerPanel.add(branch);
                //partnerPanel.add(city);
                partnerPanel.add(btnSave);
                
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
