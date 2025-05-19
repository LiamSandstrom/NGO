/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class MyProjectsCost extends ContentPanelStructure {
    private String id;
    private InfDB idb;
    private double totalCost;
    
    
    public MyProjectsCost(User user, UIStructure oneMorePanel) {
        super(user, oneMorePanel);
        try{
            setBackground(Color.gray);
            id = user.getId();
            idb = user.getDb();
            totalCost = 0;
            
            ArrayList<String> sqlQuery = idb.fetchColumn("select kostnad from projekt where projektchef = '" + id + "'");
            for(String cost : sqlQuery){
                double tot = Double.parseDouble(cost);
                totalCost += tot;
            }
            JTextArea showCost = new JTextArea(10, 30);
            showCost.setLineWrap(true);
            showCost.setWrapStyleWord(true);
            showCost.setEditable(false);
            
            String sql = "select projektnamn, kostnad, status from projekt where projektchef = '" + id + "'";
            ArrayList<HashMap<String, String>> allProjects = idb.fetchRows(sql);
            StringBuilder result = new StringBuilder();
            for(HashMap<String, String> row : allProjects){
                result.append("Name: ").append(row.get("projektnamn"))
                        .append(" , Cost: ").append(row.get("kostnad"))
                        .append(" , Status: ").append(row.get("status"))
                        .append("\n \n");
            }
            result.append("Total cost for my projects: ").append(totalCost);
            showCost.setText(result.toString());
            JScrollPane scrollPane = new JScrollPane(showCost);
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
