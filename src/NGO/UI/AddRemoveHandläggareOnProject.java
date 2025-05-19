/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import oru.inf.InfDB;

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
    }
}
