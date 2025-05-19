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
        private InfDB idb;
        private boolean kontroll;
        private String id;
        private ArrayList<String> chefId;

	public HandlaggarUI(User user) {
            try{
		this.user = user;
                idb = user.getDb();
                id = user.getId();
                kontroll = false;
                chefId = new ArrayList<>();
                chefId = idb.fetchColumn("select projektchef from projekt");

		changeContentPanel(new WelcomePanel(user, this));
		
                //ProjectWindow coolPanel = new ProjectWindow(user, this);
                ProjWin coolPanel = new ProjWin(user, this);
                addButton("Project", coolPanel);
                
                MyPersonalInfoPanel myInfo = new MyPersonalInfoPanel(user, this);
                addButton("My Info", myInfo);
                
                ShowMyProjects showMyProject = new ShowMyProjects(user, this);
                addButton("Show my projects", showMyProject);
                for(String ettID : chefId ){
                    if(ettID.equals(id)){
                        kontroll = true;
                    }
                }
                if(kontroll){
                    MyProjectsCost costPanel = new MyProjectsCost(user, this);
                    addButton("Show my total cost", costPanel);
                    
                    ChangeProjectInfo projInfo = new ChangeProjectInfo(user, this);
                    addButton("Change project info",  projInfo);
                    
                    AddRemovePartner partnerInfo = new AddRemovePartner(user, this);
                    addButton("Add/Remove partner", partnerInfo);
                    
                    AddRemoveHandläggareOnProject handOnProj = new AddRemoveHandläggareOnProject(user, this);
                    addButton("Add/Remove Handläggare", handOnProj);
                }
            }catch(InfException e){
                System.out.println(e);
            }   
                
                GlobalGoalsPanel globalGoals = new GlobalGoalsPanel(user, this);
                addButton("Show Global Goals", globalGoals);
                
                SearchByHandlaggare searchHandlaggare = new SearchByHandlaggare(user, this);
                addButton("Search Handlaggare", searchHandlaggare);
                
		bottomMargin();
               // addButton("Mina Uppgifter", minaUppgifter );
	}
}
