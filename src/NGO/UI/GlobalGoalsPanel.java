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
        
        setBackground(Color.decode("#201c1c"));
        id = user.getId();
        idb = user.getDb();
        
        JButton filterButton = new JButton("Filter by ▼");
        JPopupMenu filterMenu = new JPopupMenu();
        JMenuItem filterID = new JMenuItem("ID");
        JMenuItem filterName = new JMenuItem("Name");
        JMenuItem filterPrioritet = new JMenuItem("Priority");
        
        
        JTextArea goals = new JTextArea(10, 30);
        goals.setLineWrap(true);
        goals.setWrapStyleWord(true);
        goals.setEditable(false);
        JTextField searchField = new JTextField();
        searchField.setBounds(15, 10, 200, 30);
        add(searchField);
        JButton searchButton = new JButton("Search name");
        searchButton.setBounds(215, 10, 120, 30);
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                String query = "SELECT * FROM hallbarhetsmal WHERE namn LIKE '%" + searchText + "%'";
                loadGoals(query, goals);
            } else {
                loadGoals("SELECT * FROM hallbarhetsmal", goals);
            }
        });
        
        
        add(searchButton);
        
        filterID.addActionListener(e -> {
            loadGoals("select * from hallbarhetsmal order by hid", goals);
        });
        filterName.addActionListener(e -> {
            loadGoals("select * from hallbarhetsmal order by namn", goals);
        });
        filterPrioritet.addActionListener(e -> {
            loadGoals("select * from hallbarhetsmal order by field(prioritet, 'Hög', 'Medel', 'Låg')", goals);
        });
        filterButton.setBounds(565, 10, 150, 30);
        JScrollPane scrollPane = new JScrollPane(goals);
        scrollPane.setBounds(15, 50, 700, 685);
        filterButton.addActionListener(e -> {
            filterMenu.show(filterButton, 0, filterButton.getHeight());
        });
        
        add(filterButton);
        filterMenu.add(filterID);
        filterMenu.add(filterName);
        filterMenu.add(filterPrioritet);
        loadGoals("SELECT * FROM hallbarhetsmal", goals);
        setLayout(null);
        add(scrollPane);
        
        revalidate();
        repaint();
        
    }
    
    private void loadGoals(String mysqlQuestion, JTextArea goals) {
    try {
        ArrayList<HashMap<String, String>> allGoals = idb.fetchRows(mysqlQuestion);
        StringBuilder results = new StringBuilder();
        for (HashMap<String, String> rad : allGoals) {
            results.append("Global Goals ID: ").append(rad.get("hid"))
                    .append("\nName: ").append(rad.get("namn"))
                    .append("\nGoal number: ").append(rad.get("malnummer"))
                    .append("\nDescription: ").append(rad.get("beskrivning"))
                    .append("\nPriority: ").append(rad.get("prioritet"))
                    .append("\n\n");
        }
        goals.setText(results.toString());
    } catch (InfException ex) {
        ex.printStackTrace();
    }
    }
    
}