/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;
import NGO.UI.ContentPanelStructure;
import NGO.UI.UIStructure;
import NGO.Validate;
import NGO.User;
import java.awt.Color;
import javax.swing.*;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class MyPersonalInfoPanel extends ContentPanelStructure{
    private String id;
    private InfDB idb;
    
    public MyPersonalInfoPanel(User user, UIStructure onePanel){
        super(user, onePanel);
        Validate n = new Validate();
        try{
            setBackground(Color.gray);
            id = user.getId();
            idb = user.getDb();
            
            JTextField firstname = new JTextField(60);
            JTextField lastname = new JTextField(60);
            JTextField addres = new JTextField(60);
            JTextField email = new JTextField(60);
            JTextField phone = new JTextField(60);
            JTextField password = new JTextField(60);
            
            firstname.setText(idb.fetchSingle("select fornamn from anstalld where aid = '" + id + "'"));
            lastname.setText(idb.fetchSingle("select efternamn from anstalld where aid = '" + id + "'"));
            addres.setText(idb.fetchSingle("select adress from anstalld where aid = '" + id + "'"));
            email.setText(idb.fetchSingle("select epost from anstalld where aid = '" + id + "'"));
            phone.setText(idb.fetchSingle("select telefon from anstalld where aid = '" + id + "'"));
            password.setText(idb.fetchSingle("select losenord from anstalld where aid = '" + id + "'"));
            
            add(firstname);
            add(lastname);
            add(addres);
            add(email);
            add(phone);
            add(password);
            
            JButton btnSave = new JButton("Save my changes");
            add(btnSave);
            btnSave.addActionListener(e -> {
                
                try{
                    String newFirstname = firstname.getText();
                    String newLastname = lastname.getText();
                    String newAddres = addres.getText();
                    String newEmail = email.getText();
                    String newPhone = phone.getText();
                    String newPassword = password.getText();
                    if (n.epost(newEmail) && n.adress(newAddres) && n.telefon(newPhone) && n.pass(newPassword) && n.firstName(newFirstname) && n.lastName(newLastname)) {
                        idb.update("update anstalld set fornamn = '"
                                + newFirstname + "', efternamn = '"
                                + newLastname + "', adress = '"
                                + newAddres + "', epost = '"
                                + newEmail + "', telefon = '"
                                + newPhone + "', losenord = '"
                                + newPassword + "' where aid = '" + id + "'");
                    }
                }catch(InfException error){
                    System.out.println(error.getMessage());
                }
            });
            
            revalidate();
            repaint();
            
        }catch(InfException e){
            System.out.println(e.getMessage());
        }
    }
    
}
