/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Admin;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class AddGlobalGoal extends SettingsPanelFramework {

    private InfDB idb;
    public AddGlobalGoal(User user, String id) {
        super(user, id);
        idb = user.getDb();
        Validate val = new Validate(user);
        addInfo("hid", ""); 
        addInfo("Namn", "");
        addInfo("Målnummer", "");
        addInfo("Beskrivning", "");
        addInfo("Prioritet", "");
        setEditInfo();
        
        JButton saveBtn = addSaveButton();
        saveBtn.addActionListener(e -> {
            ArrayList<String> newValues = getTextInTextfields();
            String newHid = newValues.get(0);
            String newNamn = newValues.get(1);
            String newMalNr = newValues.get(2);
            String newBeskriv = newValues.get(3);
            String newPrio = newValues.get(4);
            
            if(val.idNotExists(newHid, "hid", "hallbarhetsmal") && val.goalName(newNamn) && val.id(newMalNr) && val.description(newBeskriv) && val.prio(newPrio)){
                try{
                    idb.insert("insert into hallbarhetsmal (hid, namn, malnummer, beskrivning, prioritet)"
                            + "values ('" + newHid + "', '" + newNamn + "', '" + newMalNr +"', '" + newBeskriv + "', '" + newPrio + "')");
                    JOptionPane.showMessageDialog(null, newNamn + " has been added as a global goal!");
                }catch(InfException ex){
                    ex.printError();
                }
            }
        });
    }
    
}
