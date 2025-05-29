/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;

import NGO.UI.CardStructure;
import NGO.UI.PartnerProjektUI;
import NGO.UI.ProjektPartnerUI;
import NGO.UI.SettingsJFrameHandler;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class CardProjektPartner extends CardStructure{
    
    private User user;
    private InfDB idb;
    private String id;

    public CardProjektPartner(int radius, User user) {
        super(radius, user);
        this.user = user;
        idb = user.getDb();
        id = user.getId();
        
    }

    @Override
    public void initCard(String id) {
        try{
            //väljer namnet där projekt id är den vi får som parameter
            String projName = idb.fetchSingle("select projektnamn from projekt where pid = '" + id + "'");
            //skapar en label med projektets namn
            JLabel cardRbr = new JLabel(projName);
            cardRbr.setFont(new Font("Arial", Font.PLAIN, 20));
            add(cardRbr, BorderLayout.CENTER);
            //och en knapp till höger för varje projekt
            JButton infoBtn = new JButton("Partners");
            infoBtn.setPreferredSize(new Dimension(100, 33));
            infoBtn.setFont(new Font("Arial", Font.PLAIN, 16));
            infoBtn.setBackground(new Color(63, 81, 181));
            add(infoBtn, BorderLayout.EAST);

            infoBtn.addActionListener(e -> {
                //lägger till en ny panel, PartnerProjektUI och skickar med användare och projekt id
                SettingsJFrameHandler.addPanel(new PartnerProjektUI(user, id));
            });
        }catch(InfException e){
            JOptionPane.showMessageDialog(this, e);
        }
        
    }
    
}
