/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author Pontus
 */
public class ProjectsCostInfoPanel extends SettingsPanelFramework{
    
    private User user;
    private Validate val;
    String pid;
    String projectname;
    String description;
    String startdate;
    String enddate;
    String cost;
    String status;
    String priority;
    InfDB idb;
    
    public ProjectsCostInfoPanel(User user, String id) throws InfException {
        super(user, id);
        this.user = user;
        val = new Validate(user);
        idb = user.getDb();

        getInfo(id);
        addInfo("Name", projectname);
        addInfo("Descripton", description);
        addInfo("Start date", startdate);
        addInfo("End date", enddate);
        addInfo("Cost", cost);
        addInfo("Status", status);
        addInfo("Priority", priority);

        if (isProjChef(id)) {
            setInfo();
            
            JButton savebtn = addSaveButton();
            savebtn.addActionListener(e -> {
                ArrayList<String> projValues = getTextInTextfields();
                String newNamn = projValues.get(0);
                String newDesc = projValues.get(1);
                String newStart = projValues.get(2);
                String newEnd = projValues.get(3);
                String newCost = projValues.get(4);
                String newStatus = projValues.get(5);
                String newPrio = projValues.get(6);
                String a = "update projekt set projektnamn = '" 
                                + newNamn + "', beskrivning = '" 
                                + newDesc + "', startdatum = '" 
                                + newStart + "', slutdatum = '"  
                                + newEnd + "', kostnad = '" 
                                + newCost + "', status = '" 
                                + newStatus + "', prioritet = '"
                                + newPrio;
                
                if(val.projectName(newNamn)
                        && val.description(newDesc)
                        && val.date(newStart)
                        && val.date(newEnd)
                        && val.cost(newCost)
                        && val.status(newStatus)
                        && val.prio(newPrio)){
                    try{
                        idb.update("update projekt set projektnamn = '" 
                                + newNamn + "', beskrivning = '" 
                                + newDesc + "', startdatum = '" 
                                + newStart + "', slutdatum = '"  
                                + newEnd + "', kostnad = '" 
                                + newCost + "', status = '"
                                + newStatus + "', prioritet = '" 
                                + newPrio + "'"
                                + "where pid = '"+ pid +"';");
                        
                        JOptionPane.showMessageDialog(null, "The projects information has been changed.");
                        
                    }catch(InfException ex){
                        ex.printError();
                    }
                }
            });
           
        } else {
            setInfo();
        }
    }

    private void getInfo(String id) {
        try {
            pid = idb.fetchSingle("select pid from projekt where pid = " + id + ";");
            projectname = idb.fetchSingle("select projektnamn from projekt where pid = " + id + ";");
            description = idb.fetchSingle("select beskrivning from projekt where pid = " + id + ";");
            startdate = idb.fetchSingle("select startdatum from projekt where pid = " + id + ";");
            enddate = idb.fetchSingle("select slutdatum from projekt where pid = " + id + ";");
            cost = idb.fetchSingle("select kostnad from projekt where pid = " + id + ";");
            status = idb.fetchSingle("select status from projekt where pid = " + id + ";");
            priority = idb.fetchSingle("select prioritet from projekt where pid = " + id + ";");

        } catch (Exception e) {
        }

    }
    
        private boolean isProjChef(String id) throws InfException{
        boolean isProjChef = false;
        try{
            ArrayList<String> projChefIdn = user.getDb().fetchColumn("select projektchef from projekt");
            for(String ettId: projChefIdn){
                if(ettId.equals(user.getId())){
                    isProjChef = true;
                }
            }
        }catch(InfException e){
            e.printError();
        }
        return isProjChef;
    }
}
    
