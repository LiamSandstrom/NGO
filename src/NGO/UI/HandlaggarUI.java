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

		changeContentPanel(new WelcomePanel(user, this));
		ProjectWindow coolPanel = new ProjectWindow(user, this);
		addButton("Cool knapp", coolPanel);
                addButton("Project", coolPanel);
                
                MyPersonalInfoPanel myInfo = new MyPersonalInfoPanel(user, this);
                addButton("My Info", myInfo);
                
                ShowMyProjects showMyProject = new ShowMyProjects(user, this);
                addButton("Show my projects", showMyProject);
                
                GlobalGoalsPanel globalGoals = new GlobalGoalsPanel(user, this);
                addButton("Show Global Goals", globalGoals);
                
		bottomMargin();
               // addButton("Mina Uppgifter", minaUppgifter );
	}
}
