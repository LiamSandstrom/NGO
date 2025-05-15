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
    
    public ProjectWindow(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        this.setBackground(Color.GRAY);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

        for (int i = 0; i < dbVal.size(); i++) {
            JPanel div = new JPanel();
            this.add(div);
            div.setLayout(new BoxLayout(div, BoxLayout.Y_AXIS));
            div.setForeground(Color.gray);
            JLabel projectName = new JLabel("Project name: " + getProjektNamn(i).toUpperCase());
            div.add(projectName);
            projectName.setForeground(Color.WHITE);
            JLabel description = new JLabel("Description " + getBeskrivning(i));
            div.add(description);
            description.setForeground(Color.WHITE);
            JLabel startdate = new JLabel("Start date " + getStartDatum(i));
            div.add(startdate);
            startdate.setForeground(Color.WHITE);
            JLabel slutdate = new JLabel("End date: " + getSlutDatum(i));
            div.add(slutdate);
            slutdate.setForeground(Color.WHITE);
            JLabel cost = new JLabel("Cost: " + getKostnad(i));
            div.add(cost);
            cost.setForeground(Color.WHITE);
            JLabel status = new JLabel("Status " + getStatus(i));
            div.add(status);
            status.setForeground(Color.WHITE);
            JLabel priority = new JLabel("Priority " + getPrioritet(i));
            div.add(priority);
            priority.setForeground(Color.WHITE);
            JLabel projectManagerName = new JLabel("Project manager name: " + getStartDatum(i));
            div.add(projectManagerName);
            projectManagerName.setForeground(Color.WHITE);
            JLabel pro= new JLabel("-------------------------------------------");
            div.add(pro);
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
