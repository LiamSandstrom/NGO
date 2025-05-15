/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import javax.swing.*;

/**
 *
 * @author david
 */
public class HandlaggarUI extends UIStructure {

	private User user;

	public HandlaggarUI(User user) {
		this.user = user;

		ContentPanelStructure coolPanel = new ContentPanelStructure(user, this);
		addButton("Cool knapp", coolPanel);
		
                MyPersonalInfoPanel infoPanel = new MyPersonalInfoPanel(user, this);
                addButton("My personal information", infoPanel);
                
                
                
                
                
                
                
                bottomMargin();
	}
}
