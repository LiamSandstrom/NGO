/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Admin;

import NGO.UI.Cards.EditGlobalGoalsCard;
import NGO.UI.Cards.GlobalGoalsCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
import NGO.UI.SettingsJFrameHandler;
import NGO.UI.UIStructure;
import NGO.User;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JButton;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class EditGlobalGoalsUI extends ContentPanelStructure{
    private InfDB idb;
    public EditGlobalGoalsUI(User user, UIStructure parentPanel){
        super(user, parentPanel);
        idb = user.getDb();
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JButton addButton = new JButton("Add Goal");
        addButton.setPreferredSize(new Dimension(200, 43));
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 105);
        add(addButton, gbc);
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 1;
        
        addButton.addActionListener(e -> {
            SettingsJFrameHandler.addPanel(new AddGlobalGoal(user, "none"));
        });
        
        try{
            ArrayList<String> hid = idb.fetchColumn("select hid from hallbarhetsmal");
            ScrollListPanel cards = new ScrollListPanel(
                    hid,
                    () -> new EditGlobalGoalsCard(20, user));
            add(cards, gbc);
        }catch(InfException e){
            e.printError();
        }
        
    }
    
}
