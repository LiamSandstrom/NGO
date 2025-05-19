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
import static java.awt.font.TextAttribute.FONT;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class AddRemoveHandläggareOnProject extends ContentPanelStructure{
    private User user;
    private InfDB idb;
    private JPanel pan;
    private JComboBox<String> addOrRemove;
    private JTextField alterPerson;
    private JTextField alterToField;
    private String[]  alts = {"add"," remove"};
    //private ArrayList<HashMap<String, String>> chefScope;
    
    public AddRemoveHandläggareOnProject(User user, UIStructure uiStructure){
        super(user, uiStructure);
        this.user = user;
        idb = user.getDb();
        
        this.setLayout(new BorderLayout());
        
        pan = new JPanel();
        pan.setBackground(Color.GRAY);
        add(pan);
        
        //chefScope = new ArrayList<>();
        //canAlter("10");
        theUI();
    }
    
    //VIDARE UTVECKLING: skriva ut i panel, inte genom .showMessageDialog, att hen har lagts till i visst projekt
    
    public void theUI(){
        addOrRemove = new JComboBox<String>();
        JLabel PersText = new JLabel("person:");
        alterPerson = new JTextField("");
        alterPerson.setPreferredSize(new Dimension(100, 30));
        
        JLabel alterToProjText = new JLabel("For project:");
        alterToField = new JTextField("");
        alterPerson.setPreferredSize(new Dimension(100, 30));
        
        JButton executeBtn = new JButton("Execute");
        executeBtn.setPreferredSize(new Dimension(80, 35));
        executeBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        executeBtn.addActionListener(e ->{
            //addOrRemove();
            //chosenAlt();
            chosenAction(alterToField.getText());
        });
        
        addOrRemove = new JComboBox<>(alts);
        pan.add(addOrRemove);
        
        pan.add(PersText);
        pan.add(alterPerson);
        pan.add(alterToProjText);
        pan.add(alterToField);
        pan.add(executeBtn);
    }
    
    private String getAlt(){
        return (String) addOrRemove.getSelectedItem();
    }
    
    public ArrayList<HashMap<String, String>> getChefsScope(){
        ArrayList<HashMap<String, String>> projChefScope = null;
        try{
            projChefScope = idb.fetchRows("select ans_proj.pid, ans_proj.aid from projekt join ans_proj on projekt.pid = ans_proj.pid join anstalld on ans_proj.aid = anstalld.aid where projektchef = '" + user.getId() + "';");
        }catch(InfException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return projChefScope;
    }
    
    
    /*
    private boolean canAlter(String id) {
        boolean canAlter = true;
        //query choice
        String query = "";
        if (id.equals("pid")) {
            query = "select ans_proj.pid from projekt join ans_proj on projekt.pid = ans_proj.pid join anstalld on ans_proj.aid = anstalld.aid where projektchef = '" + user.getId() + "';";
        }else{
            query = "select ans_proj.aid from projekt join ans_proj on projekt.pid = ans_proj.pid join anstalld on ans_proj.aid = anstalld.aid where projektchef = '" + user.getId() + "';";
        }
        //Fetch
        try {
            ArrayList<HashMap<String, String>> workers = idb.fetchRows(query);
            for (HashMap<String, String> a : workers) {
                for (String key : a.keySet()) {
                    //System.out.println("key: " + key + " value " + a.get(key));
                    if (!(a.get(key).equals(id))) {
                        canAlter = false;
                    }
                }
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return canAlter;
    }*/
    
    //Om jag är projektchef för ett visst projekt ska jag kunna lägga till en person i det
    
    private boolean isInMyAukthority(String pid){
        boolean inMyAukthority = false;
        try{
            String query = "select ans_proj.pid from projekt join ans_proj on projekt.pid = ans_proj.pid join anstalld on ans_proj.aid = anstalld.aid where projektchef = '" + user.getId() + "';";
            ArrayList<HashMap<String, String>> projsAndWorkers = idb.fetchRows(query);
            for(HashMap<String, String> aWorkerOnProj : projsAndWorkers){
                for(String idColumn: aWorkerOnProj.keySet()){
                    String pidInDb = aWorkerOnProj.get(idColumn);
                    if(pidInDb.equals(pid)){
                        inMyAukthority = true;
                    }
                }
            }
        }catch(InfException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        return inMyAukthority;
    }
    
    private void chosenAction(String pid) {
        if (isInMyAukthority(pid)) {//Är projektet i fråga ett projekt under projektchefens aukthoritet
            if (getAlt().equals("add")) {//Vad vill användaren göra, add eller remove
                addPerson(alterPerson.getText(), alterToField.getText());
            } else {
                removePerson(alterPerson.getText(), alterToField.getText());
            }
        }else{
            JOptionPane.showMessageDialog(null, "This project is not within your authority");
        }
    }
    private void addPerson(String aid, String pid){
        try{
            idb.insert("insert into ans_proj values('" + pid +"', '" + aid + "')");
            
            String namn = idb.fetchSingle("select fornamn from anstalld where aid = '" + aid + "';");
            String projnamn = idb.fetchSingle("select projektnamn from projekt where pid = '" + pid + "';");
            JOptionPane.showMessageDialog(null, namn + " har lagts till i " + projnamn );
        }catch(InfException e){
            JOptionPane.showMessageDialog(null, "Could not add " + aid + " to project " + pid);
        }
    }
    private void removePerson(String aid, String pid){
        try{
            idb.delete("delete from ans_proj where aid = '" + aid + "' and pid = '" + pid +"';");
            
            String namn = idb.fetchSingle("select fornamn from anstalld where aid = '" + aid + "';");
            String projnamn = idb.fetchSingle("select projektnamn from projekt where pid = '" + pid + "';");
            JOptionPane.showMessageDialog(null, namn + " has been removed from " + projnamn);
        }catch(InfException e){
            JOptionPane.showMessageDialog(null, "Could not remove " + aid + " from project " + pid);
        }

    }
    /*
    private void chosenAlt() {
        if (getAlt().equals("add")) {
            addPerson(alterPerson.getText(), alterToField.getText());
        } else {
            removePerson(alterPerson.getText(), alterToField.getText());
        }
    }*/
 
    /*
    private void addOrRemove() {
        if (canAlter(alterPerson.getText()) && canAlter(alterToField.getText())) {
            if (getAlt().equals("add")) {
                addPerson(alterPerson.getText(), alterToField.getText());
            } else {
                removePerson(alterPerson.getText(), alterToField.getText());
            }
        } 
        else if((alterPerson.getText().isEmpty() || (alterToField.getText().isEmpty()))){
            JOptionPane.showMessageDialog(null, "A field is empty");
        }else {
            JOptionPane.showMessageDialog(null, "This person is not under your authority");
        }
    }*/
}
