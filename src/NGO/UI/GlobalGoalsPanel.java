package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import oru.inf.InfDB;
import oru.inf.InfException;

public class GlobalGoalsPanel extends ContentPanelStructure{
    
    private User user;
    private String id;
    private InfDB idb;
    private ArrayList<HashMap<String,String>> dbVal;
    GridBagConstraints gbc;

    
    public GlobalGoalsPanel(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        this.setBackground(Color.GRAY);
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        id = user.getId();
        idb = user.getDb();
        dbVal = new ArrayList();
        
        try{
            setBackground(Color.decode("#201c1c"));
            id = user.getId();
            idb = user.getDb();
            
            JTextArea goals = new JTextArea(10, 30);
            goals.setLineWrap(true);
            goals.setWrapStyleWord(true);
            goals.setEditable(false);
            
            String sqlQuestion = "select * from hallbarhetsmal;";
            ArrayList<HashMap<String, String>> allGoals = idb.fetchRows(sqlQuestion);
            StringBuilder results = new StringBuilder();
            for (HashMap<String, String> rad : allGoals){
                results.append("Global Goals ID: ").append(rad.get("hid"))
                        .append(", Name: ").append(rad.get("namn"))
                        .append(", Goal number: ").append(rad.get("malnummer"))
                        .append(" , Description: ").append(rad.get("beskrivning"))
                        .append(" , Priority: ").append(rad.get("prioritet"))
                        .append("\n \n");
            }
            goals.setText(results.toString());
            JScrollPane scrollPane = new JScrollPane(goals);
            setLayout(null);
            scrollPane.setBounds(12, 0, 800, 700);
            add(scrollPane);
            revalidate();
            repaint();
        }
        
        catch(InfException e){
            System.out.println(e);
        }
        
    }
    
}