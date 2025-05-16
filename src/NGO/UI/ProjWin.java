/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
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
    //Databas
    private InfDB idb;
    private User user;
    private String id;
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
        
        //gbc = new GridBagConstraints();
        
        //Metod Anrop ---- resPan
        resultDisplay(false);
        //Metod Anrop ---- searchPan
        searchFields();
        searchButton();
        
    }
    
    public void searchFields(){
        //Datum från
        searchFieldFrom = new JTextField();
        searchFieldFrom.setPreferredSize(new Dimension(150, 30));
        searchPan.add(searchFieldFrom);
        searchFieldFrom.setVisible(true);
        
        //Datum till
        searchFieldTo = new JTextField();
        searchFieldTo.setPreferredSize(new Dimension(150, 30));
        searchPan.add(searchFieldTo);
        searchFieldTo.setVisible(true);
    }
    
    //Måste känna igen om projekt status är aktiv, sedan idb.fetchRows + "... and startdatum >= från and slutdatum <= till and status = 'pågående'"
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
    
    private String getQuery(boolean buttonClicked) {//Bestäm sql fråga beroende på om man redan sökt
        String avdelningsProjQuery = "";
        try {
            String avdelningsIdQuery = idb.fetchSingle("select avdelning from anstalld where aid = '" + id + "';");
 
            if (buttonClicked) {//Pågående projekt inom sökt datum spann
                //FEL I DENNA SQL FRÅGA: i ordet Pågående finns två å som blir till P?g?ende, alt lösning -Dfile.encoding=UTF-8 i run VMoptions för projektet
                avdelningsProjQuery = "select * from projekt"
                        + " where pid in (select distinct ap.pid from ans_proj ap join anstalld a on ap.aid = a.aid "
                        + "where a.avdelning = '" + avdelningsIdQuery + "') "
                        + "and status = 'Pågående' and startdatum >= '" + searchFieldFrom.getText() +"' and slutdatum <= '" + searchFieldTo.getText() + "';";//Glöm ej att ha in värden från textfield här
                System.out.println(avdelningsProjQuery);
            }
            else{//Alla projekt på avdelningen
                avdelningsProjQuery = "select * from projekt "
                    + "where pid in (select distinct ap.pid from ans_proj ap join anstalld a on ap.aid = a.aid "
                    + "where a.avdelning = " + avdelningsIdQuery + ");";
                System.out.println(avdelningsProjQuery);
                System.out.println("I am false");
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, "There are no active project for those dates. Or there was and error with the database");
            e.printError();
        }
        System.out.println("DEBUG Query = " + avdelningsProjQuery);
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
            
            //String sqlQuery = idb.fetchSingle("select avdelning from anstalld where aid = '" + id + "';");
          //ArrayList<HashMap<String, String>> allProj = idb.fetchRows("select * from projekt where pid in (select distinct ap.pid from ans_proj ap join anstalld a on ap.aid = a.aid where a.avdelning = " + sqlQuery + ");");
            String q = getQuery(buttonClicked);
            System.out.println(q);
            ArrayList<HashMap<String, String>> allProj = idb.fetchRows(getQuery(buttonClicked));
            StringBuilder searchResult = new StringBuilder();
            searchResult.append("Im a Title" + "\n \n");
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
            //resPan.add(res); La jag till med denna res två
            //ScrollPane
            JScrollPane scroller = new JScrollPane(res);
            //scroller.setBounds(20, 0, 580, 660);
            resPan.add(scroller, BorderLayout.CENTER);
            //resPan.add(scroller, BorderLayout.CENTER);
            resPan.revalidate();
            resPan.repaint();
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, "Yup, error fetching from database, gl next time, gg tho!");
            System.out.println("In ProjWin at resultDisplay InfException was caught");
            e.printError();
        }

    }

}
