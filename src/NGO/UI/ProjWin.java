/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class ProjWin extends ContentPanelStructure {
    //JPanels
    private JPanel resPan;
    private JPanel searchPan;
    //Databas
    private InfDB idb;
    private User user;
    private String id;
    GridBagConstraints gbc;

    public ProjWin(User user, UIStructure parentPanel) throws InfException {
        super(user, parentPanel);
        //this
        this.user = user;
        id = user.getId();
        idb = user.getDb();
        this.setLayout(new BorderLayout());
        
        //Panel i SOUTH
        resPan = new JPanel();
        resPan.setPreferredSize(new Dimension(780, 600));
        add(resPan, BorderLayout.SOUTH);
        resPan.setBackground(Color.GRAY);
        
        //Panel i NORTH
        searchPan = new JPanel();
        searchPan.setPreferredSize(new Dimension(680, 100));
        add(searchPan, BorderLayout.NORTH);
        searchPan.setBackground(Color.GRAY);
        
        gbc = new GridBagConstraints();
        
        //Metod Anrop ---- resPan
        resultDisplay();
        //Metod Anrop ---- searchPan
        searchField();
        searchButton();
        
    }
    
    public void searchField(){
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150, 30));
        searchPan.add(searchField);
        searchField.setVisible(true);
    }
    
    public void searchButton(){
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(50, 40));

        searchPan.add(searchButton);
        searchButton.setVisible(true);
    }
    
    public void resultDisplay(){//ligger i resPan
        try {
            JTextArea res = new JTextArea(40, 54); //JTextarea(antal rader, tecken i x led)
            res.setLineWrap(true);
            res.setWrapStyleWord(true);
            res.setEditable(false);
            
            String sqlQuery = idb.fetchSingle("select avdelning from anstalld where aid = '" + id + "';");
            //Sql frågan nedan är hårdkodad till avdelning 1, sqlQuery över ger InfException
            //ArrayList<HashMap<String, String>> allProj = idb.fetchRows("select distinct * from projekt,ans_proj,anstalld,avdelning where projekt.pid = ans_proj.pid and ans_proj.aid = anstalld.aid and anstalld.avdelning having avdelning.avdid = '" + sqlQuery + "';");
            ArrayList<HashMap<String, String>> allProj = idb.fetchRows("select * from projekt where pid in (select distinct ap.pid from ans_proj ap join anstalld a on ap.aid = a.aid where a.avdelning = " + sqlQuery + ");");
            StringBuilder searchResult = new StringBuilder();
            searchResult.append("Im a Title" + "\n \n");
            for(HashMap<String, String> row: allProj){
                searchResult.append("Project ID: " + row.get("pid") + "\n")
                        .append("Project name: " + row.get("projektnamn") + "\n")
                        .append("Description: " + row.get("beskrivning") + "\n")
                        .append("Start date: " + row.get("startdatum") + "\n")
                        .append("End date: " + row.get("slutdatum") + "\n")
                        .append("Cost: " + row.get("kostnad") + "\n")
                        .append("Status: " + row.get("status") + "\n")
                        .append("Priority: " +row.get("prioritet") + "\n")
                        .append("Project manager: " + row.get("projektchef") + "\n")
                        .append("Country: " + row.get("land") + "\n")
                        .append("\n \n");
            }
            res.setText(searchResult.toString());
            resPan.add(res);
            //ScrollPane
            JScrollPane scroller = new JScrollPane(res);
            //scroller.setBounds(20, 0, 580, 660);
            resPan.add(scroller);
            
            revalidate();
            repaint();
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, "Yup, error fetching from database, gl next time, gg tho!");
            System.out.println("In ProjWin at resultDisplay InfException was caught");
            e.printError();
        }

    }

}
