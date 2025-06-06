/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.UI.Cards.ProjektPartnerCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.UIStructure;
import NGO.User;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class ProjPart extends ContentPanelStructure{
    
    private InfDB idb;
    
    public ProjPart(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        idb = user.getDb();

        try{
            ArrayList<String> projIdn = idb.fetchColumn("select projekt.pid from projekt "
                    + "join ans_proj on projekt.pid = ans_proj.pid "
                    + "join anstalld on ans_proj.aid = anstalld.aid where anstalld.aid = '" + user.getId() +"';");
	    ArrayList<String> chefProjIdn = idb.fetchColumn("select pid from projekt where projektchef = '" + user.getId() + "';");
	    projIdn.addAll(chefProjIdn);
            if (projIdn.size() > 0) {
                ScrollListPanel cardList = new ScrollListPanel(
                        projIdn,
                        () -> new ProjektPartnerCard(20, user));
                add(cardList);
            }else{
                JLabel noProjs = new JLabel("This user does not have any projects");
                noProjs.setFont(new Font("Arial", Font.PLAIN, 20));
                add(noProjs);
            }
            
        }catch(InfException e){
            e.printError();
        }
       
    }
    
}
