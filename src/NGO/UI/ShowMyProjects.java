/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class ShowMyProjects extends ContentPanelStructure{
    private InfDB idb;
    private String id;
    
    public ShowMyProjects(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        try{
            setBackground(Color.gray);
            id = user.getId();
            idb = user.getDb();
            
            JTextArea showProject = new JTextArea(10, 30);
            showProject.setLineWrap(true);
            showProject.setWrapStyleWord(true);
            showProject.setEditable(false);
            
            String sqlQuery = "select * from projekt where projektchef = '" + id + "'";
            ArrayList<HashMap<String, String>> allProjects = idb.fetchRows(sqlQuery);
            StringBuilder result = new StringBuilder();
            for(HashMap<String, String> row : allProjects){
                result.append("Project ID: ").append(row.get("pid"))
                        .append(", Name: ").append(row.get("projektnamn"))
                        .append(", Description: ").append(row.get("beskrivning"))
                        .append(", Startdate: ").append(row.get("startdatum"))
                        .append(", Enddate: ").append(row.get("slutdatum"))
                        .append(", Cost: ").append(row.get("kostnad"))
                        .append(", Status: ").append(row.get("status"))
                        .append(", Priority: ").append(row.get("prioritet"))
                        .append(", Project manager: ").append(row.get("projektchef"))
                        .append(", Country: ").append(row.get("land"))
                        .append("\n \n");
            }
            
            showProject.setText(result.toString());
            JScrollPane scrollPane = new JScrollPane(showProject);
            setLayout(null);
            scrollPane.setBounds(10, 10, 780, 300);
            add(scrollPane);
            revalidate();
            repaint();
            
         }catch(InfException e){
             System.out.println(e.getMessage());
         }
    }
    
}
