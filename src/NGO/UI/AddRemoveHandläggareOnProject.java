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
    
    public AddRemoveHandläggareOnProject(User user, UIStructure uiStructure){
        super(user, uiStructure);
        this.user = user;
        idb = user.getDb();
        
        this.setLayout(new BorderLayout());
        
        pan = new JPanel();
        pan.setBackground(Color.GRAY);
        add(pan);
        
        theUI();
    }
    
    public void theUI(){
        JLabel alterPersText = new JLabel("Alter person:");
        JTextField alterPerson = new JTextField();
        alterPerson.setPreferredSize(new Dimension(100, 30));
        
        JLabel alterToProjText = new JLabel("For project:");
        JTextField alterToField = new JTextField();
        alterPerson.setPreferredSize(new Dimension(100, 30));
        
        JButton executeBtn = new JButton("Execute");
        executeBtn.setPreferredSize(new Dimension(80, 35));
        executeBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        
        pan.add(alterPersText);
        pan.add(alterPerson);
        pan.add(alterToProjText);
        pan.add(alterToField);
        pan.add(executeBtn);
    }
    
    public ArrayList<HashMap<String, String>> getProjectsResponsibleFor(){
        ArrayList<HashMap<String, String>> projChefScope = null;
        try{
            projChefScope = idb.fetchRows("select * from projekt");
        }catch(InfException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return projChefScope;
    }
    
}
