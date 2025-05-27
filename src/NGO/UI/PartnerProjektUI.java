/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class PartnerProjektUI extends SettingsPanelFramework{
    private InfDB idb;
    private User user;
    private String id;
    
    public PartnerProjektUI(User user, String id) {
        super(user, id);
        this.user = user;
        this.idb = user.getDb();
        this.id = id;
        
    }
    
    private void getProjects(String userid){
        try{
            ArrayList<String> projList = idb.fetchColumn("select pid from projekt where projektchef = '" + userid + "'");
            for(String pid : projList){
                addInfo(" ", pid);
                
                HashMap<String, String> partners = idb.fetchRow("select partner_pid, namn from projekt_partner join partner on partner_pid = partner.pid join projekt on projekt.pid = projekt_partner.pid where projekt_partner.pid = '" + pid + "'");
                
                
            }
            
            
            setInfo();
        }catch(InfException e){
            JOptionPane.showMessageDialog(this, e);
        }
        
        
    }
    
}
