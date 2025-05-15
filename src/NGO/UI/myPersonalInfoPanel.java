/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import javax.swing.*;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class MyPersonalInfoPanel extends ContentPanelStructure {
    private User user;
    private String id;
    private InfDB idb;
    
    public MyPersonalInfoPanel (User user, UIStructure onePanel){
        super(user, onePanel);
        try{
            
            setBackground(Color.LIGHT_GRAY);
            id = user.getId();
            idb = user.getDb();
            
            JTextField firstname = new JTextField(60);
            JTextField secondname = new JTextField(60);
            JTextField addres = new JTextField(60);
            JTextField email = new JTextField(60);
            JTextField phone = new JTextField(60);
            JTextField password = new JTextField(60);
            
            firstname.setText(idb.fetchSingle("select fornamn from anstalld where aid = '" + id + "'"));
            secondname.setText(idb.fetchSingle("select efternamn from anstalld where aid = '" + id + "'"));
            addres.setText(idb.fetchSingle("select adress from anstalld where aid = '" + id + "'"));
            email.setText(idb.fetchSingle("select epost from anstalld where aid = '" + id + "'"));
            phone.setText(idb.fetchSingle("select telefon from anstalld where aid = '" + id + "'"));
            password.setText(idb.fetchSingle("select losenord from anstalld where aid = '" + id + "'"));
            
            add(firstname);
            add(secondname);
            add(addres);
            add(email);
            add(phone);
            add(password);
            
            JButton btnSpara = new JButton("Spara ändringar");
            add(btnSpara);
            btnSpara.addActionListener(e -> {
                try { 
                    String nyFornamn = firstname.getText();
                    String nyEfternamn = secondname.getText();
                    String nyAdress = addres.getText();
                    String nyEpost = email.getText();
                    String nyPhone = phone.getText();
                    String nyPassword = password.getText();
            
                    idb.update("UPDATE anstalld SET fornamn = '"
                            + nyFornamn + "', efternamn = '" 
                            + nyEfternamn + "', adress = '" 
                            + nyAdress + "', epost = '" 
                            + nyEpost + "', telefon = '" 
                            + nyPhone + "', losenord = '" 
                            + nyPassword + "' WHERE aid = '" + id + "'");
            
                }catch(InfException error){
                    System.out.println(error);
                }
            });
            
            revalidate();
            repaint();
            
            
        }catch(InfException e){
            System.out.println(e);
        }

        
    }

    
    
}
