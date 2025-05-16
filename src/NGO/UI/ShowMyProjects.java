/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.BorderLayout;
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
    private User user;
    private InfDB idb;
    private String id;
    
    public ShowMyProjects(User user, UIStructure anotherPanel){
        super(user, anotherPanel);
        try{
            setBackground(Color.GRAY);
            id = user.getId();
            idb = user.getDb();
            
            JTextArea showProject = new JTextArea(10, 30);
            showProject.setLineWrap(true);
            showProject.setWrapStyleWord(true);
            showProject.setEditable(false);
            
            String sqlFraga = "select * from projekt where projektchef = '" + id + "'";
            ArrayList<HashMap<String, String>> allProjects = idb.fetchRows(sqlFraga);
            StringBuilder resultat = new StringBuilder();
            for (HashMap<String, String> rad : allProjects) {
                resultat.append("Projekt ID: ").append(rad.get("pid"))
                        .append(", Namn: ").append(rad.get("projektnamn"))
                        .append(" , Beskrivning: ").append(rad.get("beskrivning"))
                        .append(" , Startdatum: ").append(rad.get("startdatum"))
                        .append(" , Slutdatum: ").append(rad.get("slutdatum"))
                        .append(" , Kostnad: ").append(rad.get("kostnad"))
                        .append(" , Status: ").append(rad.get("status"))
                        .append(" , Prioritet: ").append(rad.get("prioritet"))
                        .append(" , Projektchef: ").append(rad.get("projektchef"))
                        .append(" , Land: ").append(rad.get("land"))
                        .append("\n \n");
            }
            
            showProject.setText(resultat.toString() ); 
            JScrollPane scrollPane = new JScrollPane(showProject);
            setLayout(null);
            scrollPane.setBounds(10, 10, 780, 300);
            add(scrollPane);
            revalidate();
            repaint();
            
        }catch(InfException e){
            System.out.println(e);
        }  
        
    }
    
}
