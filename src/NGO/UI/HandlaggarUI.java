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

		ProjectWindow coolPanel = new ProjectWindow(user, this);
		addButton("Cool knapp", coolPanel);
		bottomMargin();
                
                addButton("Project", coolPanel);
                
               // addButton("Mina Uppgifter", minaUppgifter );
	}
}
