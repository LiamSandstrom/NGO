/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;

import NGO.UI.Cards.SearchHandlaggareCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
import NGO.UI.UIStructure;
import NGO.User;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextField;
import oru.inf.InfDB;

/**
 *
 * @author Pontus
 */
public class SearchHandlaggare extends ContentPanelStructure {

    InfDB idb;
    User user;
    ScrollListPanel cardList;
    GridBagConstraints gbc;
    String query;

    public SearchHandlaggare(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        this.user = user;
        cardList = null;
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        idb = user.getDb();
        query = "";

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(100, 25));

        add(searchField, gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton searchButton = new JButton("Search handlaggare");

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            if (!searchText.isEmpty()) {
                if (searchText.contains("@")) {
                    query = "select aid,fornamn,efternamn,adress,epost,telefon,anstallningsdatum,avdelning from anstalld where epost like '" + searchText + "%'";
                    createList(query);
                } else {
                    String[] name = searchText.trim().split(" ");
                    if (name.length >= 2) {
                        String firstName = name[0];
                        String lastName = name[1];
                        query = "select aid,fornamn,efternamn,adress,epost,telefon,anstallningsdatum,avdelning from anstalld where fornamn like '" + firstName + "%' and efternamn like '" + lastName + "%'";
                        createList(query);
                    } else {
                        String firstOrLastName = searchText;
                        query = "select aid,fornamn,efternamn,adress,epost,telefon,anstallningsdatum,avdelning from anstalld where fornamn like '" + firstOrLastName + "%' or efternamn like '" + firstOrLastName + "%'";
                        createList(query);
                    }
                }
            } else {
                createList("select * from anstalld;");
            }
        });

        add(searchButton, gbc);

        createList("select * from anstalld;");
        gbc.gridy = 0;
        gbc.gridx = 1;
    }

    public void createList(String query) {
        if (cardList != null) {
            remove(cardList);
            cardList = null;
        }
        try {

            ArrayList<String> handlaggare = idb.fetchColumn(query);

            cardList = new ScrollListPanel(
                    handlaggare,
                    () -> new SearchHandlaggareCard(20, user));
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;
            gbc.weighty = 1;
            add(cardList, gbc);
            revalidate();
            repaint();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
