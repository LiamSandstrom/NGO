/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.util.ArrayList;
import javax.swing.*;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class HandlaggarUI extends UIStructure {

	private User user;
        private String id;
        private ArrayList<String> chefId;
        private InfDB idb;
        private boolean kontroll;
	public HandlaggarUI(User user) {
            try{
		this.user = user;
                id = user.getId();
                idb = user.getDb();
                chefId = new ArrayList<>();
                kontroll = false;
                chefId = idb.fetchColumn("select projektchef from projekt");

		changeContentPanel(new WelcomePanel(user, this));
		ProjectWindow coolPanel = new ProjectWindow(user, this);
		addButton("Cool knapp", coolPanel);
                addButton("Project", coolPanel);
                
                MyPersonalInfoPanel myInfo = new MyPersonalInfoPanel(user, this);
                addButton("My Info", myInfo);
                
                ShowMyProjects showMyProject = new ShowMyProjects(user, this);
                addButton("Show my projects", showMyProject);
                
                for(String ettid : chefId){
                    if(ettid.equals(id)){
                    
                        kontroll=true;
                    
                    }
                }
                if(kontroll){
                    addButton("Show my", showMyProject);
                }
                
                
		bottomMargin();
               // addButton("Mina Uppgifter", minaUppgifter );
            }catch(InfException e){
                System.out.println(e);
            }
        }    
}
