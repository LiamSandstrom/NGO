/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class ChangePartnerInfo extends ContentPanelStructure {
    private User user;
    private InfDB idb;
    private String id;
    
    public ChangePartnerInfo(User user, UIStructure newPanel){
        super(user, newPanel);
        try{
            id = user.getId();
            idb = user.getDb();
            String rows = idb.fetchSingle("select count(*)  from partner "
                                            + "join stad on stad = sid "
                                            + "join projekt_partner on partner.pid = projekt_partner.partner_pid "
                                            + "join projekt on projekt_partner.pid = projekt.pid "
                                            + "where projektchef = '" + id + "'");
            
            
        }catch(InfException e){
            System.out.println(e);
        }
        
        
        
    }
    
}
