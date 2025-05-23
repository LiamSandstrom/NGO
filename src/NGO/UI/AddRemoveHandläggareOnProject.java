/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;
import NGO.Validate;
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
    private Validate val;
    private JPanel pan;
    private JComboBox<String> addOrRemove;
    private JTextField alterPerson;
    private JTextField alterToField;
    private String[]  alts = {"add"," remove"};
    private JLabel feedBack;
    
    public AddRemoveHandläggareOnProject(User user, UIStructure uiStructure){
        super(user, uiStructure);
        feedBack = new JLabel();
        val = new Validate(user);
        this.user = user;
        idb = user.getDb();
        
        this.setLayout(new BorderLayout());
        
        pan = new JPanel();
        pan.setBackground(new Color(40, 40, 40));
        add(pan);
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
            chosenAction(alterToField.getText());
        });
        
        addOrRemove = new JComboBox<>(alts);
        
        pan.add(addOrRemove);
        pan.add(PersText);
        pan.add(alterPerson);
        pan.add(alterToProjText);
        pan.add(alterToField);
        pan.add(executeBtn);
        pan.add(feedBack);

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
    
    private void chosenAction(String pid) {
        String aid = alterPerson.getText();
        if (val.id(aid) && val.id(pid)) {//Är formaten rätt skrivna
            if (val.isInMyAuthority(pid)) {//Är projektet i fråga ett projekt under projektchefens aukthoritet
                if (getAlt().equals("add")) {//Vad vill användaren göra, add eller remove
                    addPerson(alterPerson.getText(), alterToField.getText());
                } else {
                    removePerson(alterPerson.getText(), alterToField.getText());
                }
            }
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

}
