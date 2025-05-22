/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;

import NGO.UI.ContentPanelStructure;
import NGO.UI.UIStructure;
import NGO.User;
import oru.inf.InfDB;

/**
 *
 * @author rostykmalanchuk
 */
public class MyInfo extends ContentPanelStructure {
    private InfDB idb;
    
    
    public MyInfo(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        idb = user.getDb();
        
        
    }
    
    
    
}
