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


public class SearchByHandlaggare extends ContentPanelStructure{
    
    private User user;
    private String id;
    private InfDB idb;
    private ArrayList<HashMap<String,String>> dbVal;
    private GridBagConstraints gbc;
    private String query;
    
    public SearchByHandlaggare(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        this.setBackground(Color.GRAY);
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        id = user.getId();
        idb = user.getDb();
        dbVal = new ArrayList();
        query = "";
        
        setBackground(Color.decode("#201c1c"));
        id = user.getId();
        idb = user.getDb();
        
        JTextArea handlaggare = new JTextArea(10, 50);
        handlaggare.setLineWrap(true);
        handlaggare.setWrapStyleWord(true);
        handlaggare.setEditable(false);
        JTextField searchField = new JTextField();
        searchField.setBounds(15, 10, 200, 30);
        add(searchField);
        JButton searchButton = new JButton("Search handlaggare");
        searchButton.setBounds(215, 10, 150, 30);
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()){
                if(searchText.contains("@")){
                    query = "select aid,fornamn,efternamn,adress,epost,telefon,anstallningsdatum,avdelning from anstalld where epost like '%" + searchText + "%'";
                    searchBy(query, handlaggare);
                }
                else{
                    String[] name = searchText.trim().split(" ");
                    if(name.length >= 2){
                        String firstName = name[0];
                        String lastName = name[1];
                        query = "select aid,fornamn,efternamn,adress,epost,telefon,anstallningsdatum,avdelning from anstalld where fornamn like '%" + firstName + "%' and efternamn like '%" + lastName + "%'";
                        searchBy(query, handlaggare);
                    }
                    else{
                        String firstOrLastName = searchText;
                        query = "select aid,fornamn,efternamn,adress,epost,telefon,anstallningsdatum,avdelning from anstalld where fornamn like '%" + firstOrLastName + "%' or efternamn like '%" + firstOrLastName + "%'";
                        searchBy(query, handlaggare);
                    }
                }
            } else {
                searchBy("select * from anstalld", handlaggare);
            }
        });
        
        
        add(searchButton);
        JScrollPane scrollPane = new JScrollPane(handlaggare);
        scrollPane.setBounds(15, 50, 700, 685);
        searchBy("select aid,fornamn,efternamn,adress,epost,telefon,anstallningsdatum,avdelning from anstalld", handlaggare);
        setLayout(null);
        add(scrollPane);
        
        revalidate();
        repaint();
    }
    
    private void searchBy(String mysqlQuestion, JTextArea handlaggare) {
    try {
        ArrayList<HashMap<String, String>> allHandlaggare = idb.fetchRows(mysqlQuestion);
        StringBuilder results = new StringBuilder();
        for (HashMap<String, String> rad : allHandlaggare) {
            results.append("ID: ").append(rad.get("aid"))
                    .append("\nFirstname: ").append(rad.get("fornamn"))
                    .append("\nLastname: ").append(rad.get("efternamn"))
                    .append("\nAddress: ").append(rad.get("adress"))
                    .append("\nEpost: ").append(rad.get("epost"))
                    .append("\nPhone nummber: ").append(rad.get("telefon"))
                    .append("\nemployment date: ").append(rad.get("anstallningsdatum"))
                    .append("\nDepartment: ").append(rad.get("avdelning"))
                    .append("\n\n");
        }
        handlaggare.setText(results.toString());
    } catch (InfException ex) {
        ex.printStackTrace();
    }
    }
}