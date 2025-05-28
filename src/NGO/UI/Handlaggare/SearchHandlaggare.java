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
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import oru.inf.InfDB;

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

        JPanel inputPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(100, 25));
        JCheckBox departmentFilter = new JCheckBox("My department");
        departmentFilter.setSelected(true);
        inputPanel.add(searchField);
        inputPanel.add(departmentFilter);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;
        add(inputPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton searchButton = new JButton("Search handläggare");

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            boolean filterDepartment = departmentFilter.isSelected();

            String baseQuery = "select * from anstalld";
            String addition = "";

            if (!searchText.isEmpty()) {
                if (searchText.contains("@")) {
                    addition = " where epost like '%" + searchText + "%'";
                } else {
                    String[] name = searchText.split(" ");
                    if (name.length >= 2) {
                        addition = " where fornamn like '" + name[0] + "%' and efternamn like '" + name[1] + "%'";
                    } else {
                        addition = " where fornamn like '" + searchText + "%' or efternamn like '" + searchText + "%'";
                    }
                }
            }

            if (filterDepartment) {
                String avd = user.getAvdelning();
                if (addition.isEmpty()) {
                    addition = " where avdelning = '" + avd + "'";
                } else {
                    addition += " and avdelning = '" + avd + "'";
                }
            }

            createList(baseQuery + addition + ";");
        });

        add(searchButton, gbc);

        String startQuery = "select * from anstalld";
        if (departmentFilter.isSelected()) {
            startQuery += " where avdelning = '" + user.getAvdelning() + "'";
        }
        createList(startQuery);
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
