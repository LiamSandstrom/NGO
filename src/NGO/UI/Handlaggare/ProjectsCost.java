/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;

import NGO.UI.Cards.CostCard;
import NGO.UI.Cards.ProjectHandlaggareCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
import NGO.UI.UIStructure;
import NGO.User;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author Pontus
 */
public class ProjectsCost extends ContentPanelStructure {

    String id;
    InfDB idb;
    User user;
    ScrollListPanel cardList;
    GridBagConstraints gbc;
    double totalCost;
    ArrayList<String> chefId;
    boolean kontroll;
    

    public ProjectsCost(User user, UIStructure parentPanel) throws InfException {
        super(user, parentPanel);
        this.user = user;
        id = user.getId();
        idb = user.getDb();
        cardList = null;
        chefId = new ArrayList<>();
        chefId = idb.fetchColumn("select projektchef from projekt");
        

        totalCost = 0;
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        for (String ettID : chefId) {
            if (ettID.equals(id)) {
                kontroll = true;
            }
        }
        if (kontroll) {

            ArrayList<String> sqlQuery = idb.fetchColumn("select kostnad from projekt where projektchef = '" + id + "'");
            for (String cost : sqlQuery) {
                double tot = Double.parseDouble(cost);
                totalCost += tot;
            }
            JLabel totalCostLabel = new JLabel("Total cost: " + totalCost + " kr");
            totalCostLabel.setFont(new Font("Arial", Font.PLAIN, 42));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            add(totalCostLabel, gbc);

            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.BOTH;
            createList("select * from projekt where projektchef = " + id + ";");
        }
        else{
		
        createInfoList("select * from projekt where pid in (select pid from ans_proj where aid = " + id + ")");
        }
    }
        
    public void createList(String query) {
        if (cardList != null) {
            remove(cardList);
            cardList = null;
        }
        try {

            ArrayList<String> cost = idb.fetchColumn(query);

            cardList = new ScrollListPanel(
                    cost,
                    () -> new CostCard(20, user));
            add(cardList, gbc);
            revalidate();
            repaint();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createInfoList(String query) {
        if (cardList != null) {
            remove(cardList);
            cardList = null;
        }
        try {

            ArrayList<String> cost = idb.fetchColumn(query);

            cardList = new ScrollListPanel(
                    cost,
                    () -> new ProjectHandlaggareCard(20, user));
            add(cardList, gbc);
            revalidate();
            repaint();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
