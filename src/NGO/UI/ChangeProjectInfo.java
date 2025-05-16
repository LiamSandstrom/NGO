/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class ChangeProjectInfo extends ContentPanelStructure {

    private User user;
    private String id;
    private InfDB idb;

    public ChangeProjectInfo(User user, UIStructure twoPanel) {
        super(user, twoPanel);
        try {
            setBackground(Color.LIGHT_GRAY);
            id = user.getId();
            idb = user.getDb();
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            ArrayList<HashMap<String, String>> allProjects = idb.fetchRows("select * from projekt where projektchef = '" + id + "'");

            for (HashMap<String, String> project : allProjects) {
                JPanel projectPanel = new JPanel();
                projectPanel.setLayout(new BoxLayout(projectPanel, BoxLayout.Y_AXIS));
                final String pID = project.get("pid");
                projectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Project: " + project.get("pid")));

                final JTextField projectid = new JTextField(project.get("pid"), 60);
                final JTextField projectname = new JTextField(project.get("projektnamn"), 60);
                final JTextField description = new JTextField(project.get("beskrivning"), 60);
                final JTextField startdate = new JTextField(project.get("startdatum"), 60);
                final JTextField enddate = new JTextField(project.get("slutdatum"), 60);
                final JTextField cost = new JTextField(project.get("kostnad"), 60);
                final JTextField status = new JTextField(project.get("status"), 60);
                final JTextField priority = new JTextField(project.get("prioritet"), 60);
                final JTextField projectmanager = new JTextField(project.get("projektchef"), 60);
                final JTextField country = new JTextField(project.get("land"), 60);

                JButton btnSave = new JButton("Save my changes");

                btnSave.addActionListener(e -> {
                    try {
                        String query = "UPDATE projekt SET projektnamn = '" + projectname.getText()
                                + "', beskrivning = '" + description.getText()
                                + "', startdatum = '" + startdate.getText()
                                + "', slutdatum = '" + enddate.getText()
                                + "', kostnad = '" + cost.getText()
                                + "', status = '" + status.getText()
                                + "', prioritet = '" + priority.getText()
                                + "', projektchef = '" + projectmanager.getText()
                                + "', land = '" + country.getText()
                                + "' WHERE pid = '" + pID + "'";
                        idb.update(query);
                    } catch (InfException error) {
                        System.out.println(error);
                    }
                });
                projectid.setEditable(false);
                projectPanel.add(projectid);
                projectPanel.add(projectname);
                projectPanel.add(description);
                projectPanel.add(startdate);
                projectPanel.add(enddate);
                projectPanel.add(cost);
                projectPanel.add(status);
                projectPanel.add(priority);
                projectPanel.add(projectmanager);
                projectPanel.add(country);
                projectPanel.add(btnSave);

                mainPanel.add(projectPanel);
            }
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            add(scrollPane);
            revalidate();
            repaint();

        } catch (InfException e) {
            System.out.println(e);
        }
    }
}
