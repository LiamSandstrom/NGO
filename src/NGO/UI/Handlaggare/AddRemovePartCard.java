/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;

import NGO.UI.Cards.ProjPartCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
import NGO.UI.UIStructure;
import NGO.User;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class AddRemovePartCard extends ContentPanelStructure{
    
    private InfDB idb;
    private String id;
    
    public AddRemovePartCard(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        idb = user.getDb();
        id = user.getId();
        
        try{
            ArrayList<String> proj_part = idb.fetchColumn("select pid from projekt where projektchef = '" + id + "'");
            
            ScrollListPanel cardlist = new ScrollListPanel(proj_part, () -> new ProjPartCard(20, user));
            add(cardlist);
            
        }catch(InfException e){
            System.out.println(e.getMessage());
        }
        
    }
    
}


