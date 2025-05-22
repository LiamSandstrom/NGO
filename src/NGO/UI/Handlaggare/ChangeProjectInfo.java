/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;
import NGO.UI.ContentPanelStructure;
import NGO.UI.UIStructure;
import NGO.Validate;
import NGO.User;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class ChangeProjectInfo extends ContentPanelStructure{
    private String id;
    private InfDB idb;
    private Validate val;
    
    public ChangeProjectInfo(User user, UIStructure twoPanel){
        super(user, twoPanel);
        val = new Validate(user);
        try{
            setBackground(Color.gray);
            id = user.getId();
            idb = user.getDb();
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            
            ArrayList<HashMap<String, String>> allProject = idb.fetchRows("select * from projekt where projektchef = '" + id + "'");
            
            for(HashMap<String, String> project : allProject){
                JPanel projectPanel = new JPanel();
                projectPanel.setLayout(new BoxLayout(projectPanel, BoxLayout.Y_AXIS));
                String pID = project.get("pid");
                projectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Project: " + project.get("pid")));
                
                JTextField projectid = new JTextField(project.get("pid"), 60);
                JTextField projectname = new JTextField(project.get("projektnamn"), 60);
                JTextField description = new JTextField(project.get("beskrivning"), 60);
                JTextField startdate = new JTextField(project.get("startdatum"), 60);
                JTextField enddate = new JTextField(project.get("slutdatum"), 60);
                JTextField cost = new JTextField(project.get("kostnad"), 60);
                JTextField status = new JTextField(project.get("status"), 60);
                JTextField priority = new JTextField(project.get("prioritet"), 60);
                JTextField projectmanager = new JTextField(project.get("projektchef"), 60);
                JTextField country = new JTextField(project.get("land"), 60);
                
                JButton btnSave = new JButton("Save my changes");
                btnSave.addActionListener(e -> {
                    try {
                        if (val.projName(projectname.getText()) && val.description(description.getText()) && val.date(startdate.getText()) && val.date(enddate.getText()) && val.cost(cost.getText()) && val.status(status.getText()) && val.id(projectmanager.getText()) && val.id(country.getText())&& val.prio(priority.getText())) {
                            String query = "update projekt set projektnamn = '" + projectname.getText()
                                    + "', beskrivning = '" + description.getText()
                                    + "', startdatum = '" + startdate.getText()
                                    + "', slutdatum = '" + enddate.getText()
                                    + "', kostnad = '" + cost.getText()
                                    + "', status = '" + status.getText()
                                    + "', prioritet = '" + priority.getText()
                                    + "', projektchef = '" + projectmanager.getText()
                                    + "', land = '" + country.getText()
                                    + "' where pid = '" + pID + "'";
                            idb.update(query);
                        }
                    } catch (InfException error) {
                        System.out.println(error.getMessage());
                    }
                });
                projectid.setEditable(false);
                projectPanel.add(projectid);
                projectPanel.add(projectname);
                projectPanel.add(description);
                projectPanel.add(startdate);
                projectPanel.add(enddate);
                projectPanel.add(cost);
                projectPanel.add(status);
                projectPanel.add(priority);
                projectPanel.add(projectmanager);
                projectPanel.add(country);
                projectPanel.add(btnSave);
                
                mainPanel.add(projectPanel);
                
            }
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            add(scrollPane);
            revalidate();
            repaint();
            
        }catch(InfException e){
            System.out.println(e.getMessage());
        }
        
    }
    
}
