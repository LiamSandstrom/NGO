/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.Dimension;

/**
 *
 * @author rostykmalanchuk
 */
public class MyPersonalInfoPanel extends ContentPanelStructure {
    private User user;
    
    public MyPersonalInfoPanel (User user, UIStructure onePanel){
        super(user, onePanel);
        //setPreferredSize(new Dimension(800, 700));
        setBackground(Color.green);
        
    }

    
    
}
