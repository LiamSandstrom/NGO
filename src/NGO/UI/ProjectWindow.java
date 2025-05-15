/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class ProjectWindow extends ContentPanelStructure {

    private InfDB idb;
    private ArrayList<HashMap<String,String>> dbVal;
    GridBagConstraints gbc;
    
    public ProjectWindow(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        this.setBackground(Color.GRAY);
        this.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        dbVal = new ArrayList();
        this.idb = user.getDb();
        fillArrayList();
        displayProjects();
    }

    public void fillArrayList() {
        try {
            dbVal = idb.fetchRows("SELECT * from projekt");
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, "Error fetching from database");
        }
    }

    public void printAllProjects() {
        for (int i = 0; i < dbVal.size(); i++) {
            System.out.println(getProjektNamn(i).toUpperCase());
            System.out.println(getBeskrivning(i));
            System.out.println(getStartDatum(i));
            System.out.println(getSlutDatum(i));
            System.out.println(getKostnad(i));
            System.out.println(getStatus(i));
            System.out.println(getPrioritet(i));
            System.out.println(getProjektChefNamn(i));
            System.out.println();
        }
    }

    public void displayProjects() {
        int ind = 0;
        for (int i = 0; i < dbVal.size(); i++) {
            GridBagConstraints gbc2 = new GridBagConstraints();
            int i2 = 0;
            JPanel div = new JPanel();
            div.setLayout(new GridBagLayout());
            gbc.gridy = ind;
            this.add(div, gbc);
            div.setForeground(Color.gray);
            
            JLabel projectName = new JLabel("Project name: " + getProjektNamn(i).toUpperCase());
            gbc2.gridy = i2;
            div.add(projectName, gbc2);
            projectName.setForeground(Color.WHITE);
            i2++;
            
            JLabel description = new JLabel("Description " + getBeskrivning(i));
            gbc2.gridy = i2;
            div.add(description, gbc2);
            description.setForeground(Color.WHITE);
            i2++;
            
            JLabel startdate = new JLabel("Start date " + getStartDatum(i));
            gbc2.gridy = i2;
            div.add(startdate, gbc2);
            startdate.setForeground(Color.WHITE);
            i2++;
            
            JLabel slutdate = new JLabel("End date: " + getSlutDatum(i));
            gbc2.gridy = i2;
            div.add(slutdate, gbc2);
            slutdate.setForeground(Color.WHITE);
            i2++;
            
            JLabel cost = new JLabel("Cost: " + getKostnad(i));
            gbc2.gridy = i2;
            div.add(cost, gbc2);
            cost.setForeground(Color.WHITE);
            i2++;
            
            JLabel status = new JLabel("Status " + getStatus(i));
            gbc2.gridy = i2;
            div.add(status, gbc2);
            status.setForeground(Color.WHITE);
            i2++;
            
            JLabel priority = new JLabel("Priority " + getPrioritet(i));
            gbc2.gridy = i2;
            div.add(priority, gbc2);
            priority.setForeground(Color.WHITE);
            i2++;
            
            JLabel projectManagerName = new JLabel("Project manager name: " + getStartDatum(i));
            gbc2.gridy = i2;
            div.add(projectManagerName, gbc2);
            projectManagerName.setForeground(Color.WHITE);
            
            i2++;
            
            ind++;
        }
        revalidate();
        repaint();
    }

    public String getProjektNamn(int i) {
        return dbVal.get(i).get("projektnamn");
    }

    public String getBeskrivning(int i) {
        return dbVal.get(i).get("beskrivning");
    }

    public String getStartDatum(int i) {
        return dbVal.get(i).get("startdatum");
    }

    public String getSlutDatum(int i) {
        return dbVal.get(i).get("slutdatum");
    }

    public String getKostnad(int i) {
        return dbVal.get(i).get("kostnad");
    }

    public String getStatus(int i) {
        return dbVal.get(i).get("status");
    }

    public String getPrioritet(int i) {
        return dbVal.get(i).get("prioritet");
    }

    public String getProjektChefNamn(int i) {// Gör så att man returnerar namnet på chefen direkt, ej id
        String namn = "";
        try {
            namn = idb.fetchSingle("select fornamn from anstalld join projekt on anstalld.aid = projekt.projektchef where projektchef = " + (dbVal.get(i).get("projektchef")));
            if (namn.isEmpty()) {
                namn = "I do not contain a prjektchef name, location: ProjectWindow.java, method: getProjektChefNamn()";
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, "Could not fetch projektchef name");
        }
        return namn;
    }

    public String getLandNamn(int i) {// Gör så att man returnerar namnet på landet direkt, ej id
        String landNamn = "";
        try {
            landNamn = idb.fetchSingle("Select namn from land join projekt on land.lid = projekt.pid where pid = " + dbVal.get(i).get("namn"));
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, "Error fetching land namn from db");
        }
        return landNamn;
    }
    /*
    public String getLand(int i){
        return 
    }
     */

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnMyProj;
    private javax.swing.JLabel lblProjRub;
    // End of variables declaration     

}
