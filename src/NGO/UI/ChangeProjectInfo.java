/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class ChangeProjectInfo extends ContentPanelStructure {
    private User user;
    private String id;
    private InfDB idb;
    
    public ChangeProjectInfo (User user, UIStructure twoPanel){
        super(user, twoPanel); 
        try{
            setBackground(Color.LIGHT_GRAY);
            id = user.getId();
            idb = user.getDb();
            
            JTextField projectid = new JTextField(60);
            JTextField projectname = new JTextField(60);
            JTextField description = new JTextField(60);
            JTextField startdate = new JTextField(60);
            JTextField enddate = new JTextField(60);
            JTextField cost = new JTextField(60);
            JTextField status = new JTextField(60);
            JTextField priority = new JTextField(60);
            JTextField projectmanager = new JTextField(60);
            JTextField country = new JTextField(60);
            
            projectid.setText(idb.fetchSingle("select pid from projekt where projektchef = '" + id + "'"));
            projectname.setText(idb.fetchSingle("select projektnamn from projekt where projektchef = '" + id + "'"));
            description.setText(idb.fetchSingle("select beskrivning from projekt where projektchef = '" + id + "'"));
            startdate.setText(idb.fetchSingle("select startdatum from projekt where projektchef = '" + id + "'"));
            enddate.setText(idb.fetchSingle("select slutdatum from projekt where projektchef = '" + id + "'"));
            cost.setText(idb.fetchSingle("select kostnad from projekt where projektchef = '" + id + "'"));
            status.setText(idb.fetchSingle("select status from projekt where projektchef = '" + id + "'"));
            priority.setText(idb.fetchSingle("select prioritet from projekt where projektchef = '" + id + "'"));
            projectmanager.setText(idb.fetchSingle("select projektchef from projekt where projektchef = '" + id + "'"));
            country.setText(idb.fetchSingle("select land from projekt where projektchef = '" + id + "'"));
            
            add(projectid);
            add(projectname);
            add(description);
            add(startdate);
            add(enddate);
            add(cost);
            add(status);
            add(priority);
            add(projectmanager);
            add(country);
            
            JButton btnSpara = new JButton("Save my changes");
            add(btnSpara);
            btnSpara.addActionListener(e -> {
                try{
                    String newProjectid = projectid.getText();
                    String newProjectname = projectname.getText();
                    String newDescription = description.getText();
                    String newStartdate = startdate.getText();
                    String newEnddate = enddate.getText();
                    String newCost = cost.getText();
                    String newStatus = status.getText();
                    String newPriority = priority.getText();
                    String newProjectmanager = projectmanager.getText();
                    String newCountry = country.getText();
            
                    idb.update("UPDATE anstalld SET pid = '" + newProjectid + 
                            "', projektnamn = '" + newProjectname + 
                            "', beskrivning = '" + newDescription + 
                            "', startdatum = '" + newStartdate + 
                            "', slutdatum = '" + newEnddate + 
                            "', kostnad = '" + newCost + 
                            "', status = '" + newStatus + 
                            "', prioritet = '" + newPriority + 
                            "', projeckchef = '" + newProjectmanager + 
                            "', land = '" + newCountry + 
                            "' WHERE projektchef = '" + id);
                }catch(InfException error){
                    System.out.println(error);
                }
            });
            revalidate();
            repaint();
            
        }catch(InfException e){
            System.out.println(e);
        }
    }
}
