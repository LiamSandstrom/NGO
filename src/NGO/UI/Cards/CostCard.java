/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;

import NGO.UI.Admin.ProjectSettingsUI;
import NGO.UI.AnstalldSettingsUI;
import NGO.UI.CardStructure;
import NGO.UI.Handlaggare.ProjectsCostInfoPanel;
import NGO.UI.SettingsJFrameHandler;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import oru.inf.InfException;

/**
 *
 * @author Pontus
 */
public class CostCard extends CardStructure {

    private User user;

    public CostCard(int radius, User user) {
        super(radius, user);
        this.user = user;
    }
    
    @Override
    public void initCard(String id) {
        try {
            String name = user.getDb().fetchSingle("select projektnamn from projekt where pid = " + id + ";");
            String projektChef = user.getDb().fetchSingle("select projektchef from projekt where pid = " + id + ";");

            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            add(nameLabel, BorderLayout.CENTER);
            
            JButton infoBtn;

            if (user.getId().equals(projektChef)) {
                infoBtn = new JButton("Edit");
            } else {
                infoBtn = new JButton("Info");
            }
            
            infoBtn.setPreferredSize(new Dimension(100, 33));
            infoBtn.setFont(new Font("Arial", Font.PLAIN, 16));
            infoBtn.setBackground(new Color(63, 81, 181));
            add(infoBtn, BorderLayout.EAST);

            infoBtn.addActionListener(e -> {
                try{
                    SettingsJFrameHandler.addPanel(new ProjectSettingsUI(user, id));
                }catch(Exception ex){
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
