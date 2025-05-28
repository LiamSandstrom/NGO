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
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class GlobalGoalsSettingsUI extends SettingsPanelFramework{
    private InfDB idb; 
    public GlobalGoalsSettingsUI(User user, String id){
        super(user, id);
        this.idb = user.getDb();
        Validate val = new Validate(user);
        try {
            String newNamn = idb.fetchSingle("select namn from hallbarhetsmal where hid = '" + id + "';");
            String newMalNr = idb.fetchSingle("select malnummer from hallbarhetsmal where hid = '" + id + "';");
            String newBeskriv = idb.fetchSingle("select beskrivning from hallbarhetsmal where hid = '" + id + "';");
            String newPrio = idb.fetchSingle("select prioritet from hallbarhetsmal where hid = '" + id + "';");
            
            addInfo("Name", newNamn);
            addInfo("Goal number", newMalNr);
            addInfo("Description", newBeskriv);
            addInfo("Priority", newPrio);
            setEditInfo();
        }catch(InfException e){
            e.printError();
        }
        JButton saveBtn = addSaveButton();
        JButton deleteBtn = addDeleteButton();

        saveBtn.addActionListener(e ->{
            ArrayList<String> newValues = getTextInTextfields();
            String newNamn = newValues.get(0);
            String newMalNr = newValues.get(1);
            String newBeskriv = newValues.get(2);
            String newPrio = newValues.get(3);
            
            if(val.goalName(newNamn) && val.id(newMalNr) && val.description(newBeskriv) && val.prio(newPrio)){
                try{
                    idb.update("update hallbarhetsmal set namn = '"+newNamn+"', malnummer = '"+newMalNr+"', beskrivning = '"+newBeskriv+"', prioritet = '"+newPrio+"'"
                            + "where hid = '"+id+"'");
                    JOptionPane.showMessageDialog(null, newNamn + " has been changed sucessfully!");
                }catch(InfException ex){
                    ex.printError();
                }
            }
        });
        
        deleteBtn.addActionListener(e -> {
            try{
                
                idb.delete("delete from proj_hallbarhet where hid = '" + id +"'");//Radera alla relationer till projekt
                idb.delete("delete from avd_hallbarhet where hid = '" + id +"'");//Radera alla relationer till avdelning
                
                idb.delete("delete from hallbarhetsmal where hid = '" + id + "'");//Radera hålbarhetsmålet i hallbarhetsmal
                
                JOptionPane.showMessageDialog(null, "Global goal has been deleted!");
            }catch(InfException exem){
                exem.printError();
            }
        });
        
    }
}
