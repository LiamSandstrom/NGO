/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;

import NGO.UI.Admin.GlobalGoalsSettingsUI;
import NGO.UI.CardStructure;
import NGO.UI.SettingsJFrameHandler;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class EditGlobalGoalsCard extends CardStructure{
    
    private User user;
    private InfDB idb;
    public EditGlobalGoalsCard(int radius, User user){
        super(radius, user);
        this.user = user;
        idb = user.getDb();
    }
    @Override
    public void initCard(String id){
        try{
            String namn = idb.fetchSingle("select namn from hallbarhetsmal where hid = '" + id + "';");
            
            JLabel namnLbl = new JLabel(namn);
            namnLbl.setFont(new Font("Arial", Font.PLAIN, 20));
            add(namnLbl, BorderLayout.CENTER);
            
            JButton editBtn = new JButton("Edit");
            editBtn.setPreferredSize(new Dimension(100, 33));
            editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
            add(editBtn, BorderLayout.EAST);
            
            editBtn.addActionListener(e -> {
                SettingsJFrameHandler.addPanel(new GlobalGoalsSettingsUI(user, id));
            });
            
        }catch(InfException e){
            e.printError();
        }
    }
}
