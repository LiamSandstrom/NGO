/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;

import NGO.UI.ContentPanelStructure;
import NGO.UI.UIStructure;
import NGO.User;
import NGO.Validate;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import static java.awt.font.TextAttribute.FONT;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
    //J
    private JPanel resPan;
    private JPanel searchPan;
    private JTextField searchFieldFrom;
    private JTextField searchFieldTo;
    private JComboBox<String> combo;
    //Databas
    private InfDB idb;
    private User user;
    private String id;
    private String[] alternatives = {"Unspecified", "Pågående", "Planerat", "Avslutat"};
    private Validate val;
    //GridBagConstraints gbc;

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
        resPan.setBackground(Color.GRAY);
        //resPan.setLayout(new BorderLayout());
        //resPan.setLayout(new BorderLayout());
        add(resPan, BorderLayout.SOUTH);
        
        //Panel i NORTH
        searchPan = new JPanel();
        searchPan.setPreferredSize(new Dimension(680, 100));
        add(searchPan, BorderLayout.NORTH);
        searchPan.setBackground(Color.GRAY);
        
        //Metod Anrop ---- resPan
        resultDisplay(false);
        //Metod Anrop ---- searchPan
        createStatusComboBox();
        searchFields();
        searchButton();
        val = new Validate(user);
    }
    
    private void createStatusComboBox(){
        combo = new JComboBox<>(alternatives);
        searchPan.add(combo);
    }
    
    private String getStatusChoice(){
        String sqlAddition = "and Status = " + "'" + ((String) combo.getSelectedItem()) + "'";
        if(((String) combo.getSelectedItem()).equals("Unspecified")){
            sqlAddition = "";
        }
        return sqlAddition;
    }
    
    public void searchFields(){//Skapa fälten
        //From Label
        JLabel from = new JLabel("Date from:");
        from.setForeground(Color.BLACK);
        searchPan.add(from);
        //Datum från
        searchFieldFrom = new JTextField("Date");
        searchFieldFrom.setPreferredSize(new Dimension(150, 30));
        searchPan.add(searchFieldFrom);
        searchFieldFrom.setVisible(true);
        removeTextInField(searchFieldFrom);
        
        //To Label
        JLabel to = new JLabel("To:");
        to.setForeground(Color.BLACK);
        searchPan.add(to);
        //Datum till
        searchFieldTo = new JTextField("Date");
        searchFieldTo.setPreferredSize(new Dimension(150, 30));
        searchPan.add(searchFieldTo);
        searchFieldTo.setVisible(true);
        removeTextInField(searchFieldTo);
    }
    
    private void removeTextInField(JTextField aField){//Set texten i text fält till empty string när man klickar på det
        aField.addMouseListener(new java.awt.event.MouseAdapter() {
            boolean fieldIsClicked = false;
            public void mouseClicked(java.awt.event.MouseEvent e){
                if(!fieldIsClicked){
                    aField.setText("");
                    fieldIsClicked = true;
                }
            }
        });
    }

    public void searchButton(){
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(70, 40));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 11));
        //onClick
        searchButton.addActionListener(e ->{
            resultDisplay(true);
        });
        searchPan.add(searchButton);
        searchButton.setVisible(true);
    }
    
    //Används inte för tillfället
    private String checkInput(String date){//Om date inte har precis rätt format, sätt date till tom string som tvingar InfException, därmed felmeddelande i vilket formatet förklaras
        if(!((date.length() == 10) && (date.substring(4,5).equals("-") && (date.substring(6,7).equals("-"))))){
            date = "";
        }
        return date;
    }
    
    private String getQuery(boolean buttonClicked) {//Bestäm sql fråga beroende på om man redan sökt eller om man precis klickat på project knappen
        //String from = checkInput(searchFieldFrom.getText());
        //String to = checkInput(searchFieldTo.getText());
        String avdelningsProjQuery = "";

        try {
            String avdelningsIdQuery = idb.fetchSingle("select avdelning from anstalld where aid = '" + id + "';");

            if (buttonClicked) {//Pågående projekt inom sökt datum spann
                String fom = searchFieldFrom.getText();
                String to = searchFieldTo.getText();
                if (val.date(fom) && val.date(to)) {
                    avdelningsProjQuery = "select * from projekt"
                            + " where pid in (select distinct ap.pid from ans_proj ap join anstalld a on ap.aid = a.aid "
                            + "where a.avdelning = '" + avdelningsIdQuery + "') "
                            + getStatusChoice() + " and startdatum >= '" + searchFieldFrom.getText() + "' and slutdatum <= '" + to + "';";
                }
            } else {//Alla projekt på avdelningen
                avdelningsProjQuery = "select * from projekt "
                        + "where pid in (select distinct ap.pid from ans_proj ap join anstalld a on ap.aid = a.aid "
                        + "where a.avdelning = " + avdelningsIdQuery + ");";
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, "There was an error with the database");
            e.printError();
        }
        return avdelningsProjQuery;
    }
    
    public void resultDisplay(boolean buttonClicked){//ligger i resPan
        try {
            if(buttonClicked){
                resPan.removeAll();
            }
            JTextArea res = new JTextArea(40, 54); //JTextarea(antal rader, tecken i x led)
            res.setLineWrap(true);
            res.setWrapStyleWord(true);
            res.setEditable(false);
            
            ArrayList<HashMap<String, String>> allProj = idb.fetchRows(getQuery(buttonClicked));
            StringBuilder searchResult = new StringBuilder();
            
            String title = "";
            if(buttonClicked){
                if(!(allProj.size() == 1)){
                    title = "There was " + allProj.size() + " results \n \n";
                }else{
                    title = "There was " + allProj.size() + " result \n \n";
                }
            }else{
                title = "These are all projects for your section: " + allProj.size() +" \n \n";
            }
            searchResult.append(title);
            System.out.println(searchResult.toString());
            if (allProj.size() > 0) {
                for (HashMap<String, String> row : allProj) {
                    searchResult.append("Project ID: " + row.get("pid") + "\n")
                            .append("Project name: " + row.get("projektnamn") + "\n")
                            .append("Description: " + row.get("beskrivning") + "\n")
                            .append("Start date: " + row.get("startdatum") + "\n")
                            .append("End date: " + row.get("slutdatum") + "\n")
                            .append("Cost: " + row.get("kostnad") + "\n")
                            .append("Status: " + row.get("status") + "\n")
                            .append("Priority: " + row.get("prioritet") + "\n")
                            .append("Project manager: " + row.get("projektchef") + "\n")
                            .append("Country: " + row.get("land") + "\n")
                            .append("\n \n");
                }
            }else{
                searchResult.append("There are no active projects within these dates in your section");
            }
            res.setText(searchResult.toString());
            //ScrollPane
            JScrollPane scroller = new JScrollPane(res);
            resPan.add(scroller, BorderLayout.CENTER);
            resPan.revalidate();
            resPan.repaint();
        } catch (InfException e) {
            //JOptionPane.showMessageDialog(null, "There was an error with the database");
            e.printError();
        }

    }

}
