/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;

import NGO.UI.AddRemovePartner;
import NGO.UI.AddRemovePartner2;
import NGO.UI.CardStructure;
import NGO.UI.SettingsJFrameHandler;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class ProjPartCard extends CardStructure{
    
    private User user;
    private String ID;
    
    public ProjPartCard(int radius, User user){
        super(radius, user);
        this.user = user;
        
    }

    @Override
    public void initCard(String id) {
        try{
            this.ID = id;
            
            String projectName = user.getDb().fetchSingle("select pid from projekt where projektchef = '" + ID + "'");
            JLabel projectname = new JLabel(projectName);
            projectname.setFont(new Font("Arial", Font.PLAIN, 20));
            add(projectname, BorderLayout.CENTER);
            
            JButton btnShow = new JButton("Partners");
            btnShow.setPreferredSize(new Dimension(100, 33));
            btnShow.setFont(new Font("Arial", Font.PLAIN, 16));
            btnShow.setBackground(new Color(63, 81, 181));
            add(btnShow, BorderLayout.EAST);
            
            btnShow.addActionListener(e ->{
                SettingsJFrameHandler.addPanel(new AddRemovePartner2(user, ID));
                
            });
            
            
        }catch(InfException e){
            e.printStackTrace();
        }
    }
    
}
