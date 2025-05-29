/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.UI.Cards.CardProjektPartner;
import NGO.User;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class PartnerProjekt extends ContentPanelStructure{
    private InfDB idb;
    private User user;
    private String id;
    
    
    public PartnerProjekt(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        setLayout(new GridBagLayout());
        //GridBagConstraints gbc = new GridBagConstraints();
        
        this.user = user;
        idb = user.getDb();
        id = user.getId();
        
        try {
            //lista med alla projekt id där användaren är projekt chef
            ArrayList<String> projID = idb.fetchColumn("select pid from projekt where projektchef = '" + id + "';");
            //skapar en scrollListpanel och skickar med lista med id, samt skapar en kort för varje id
            ScrollListPanel cardList = new ScrollListPanel(projID, () -> new CardProjektPartner(20, user));
            add(cardList);

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, e);
        }
       
    }
        
}

