/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.UI.Handlaggare.MyPersonalInfoPanel;
import NGO.UI.Handlaggare.GlobalGoals;
import NGO.UI.Handlaggare.Projects;
import NGO.UI.Handlaggare.ProjectsCost;
import NGO.UI.Handlaggare.SearchHandlaggare;
import NGO.User;
import java.util.ArrayList;
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
		
                //skapar en klass 
                Projects projects = new Projects(user, this);
                //anropas metoden från UIStructure som lägger till en kanpp i toolbarPanel och öppnar en panel i contentPanel
                addButton("Projects/department", projects);

                MyPersonalInfoPanel myInfo = new MyPersonalInfoPanel(user, id);
                addDirectLinkButton("MyInfo", myInfo);
                
                ProjectsCost costPanel = new ProjectsCost(user, this);
                addButton("My Projects", costPanel);
                
                ProjPart projektsPartners = new ProjPart(user, this);
                addButton("Project Partners", projektsPartners);
                
                //sker en kontroll om användaren är projektchef, om den är det kan den se vissa knappar som övriga handläggare inte ska se
                for(String ettID : chefId ){
                    if(ettID.equals(id)){
                        kontroll = true;
                    }
                }
                if(kontroll){
                    PartnerProjekt partProj = new PartnerProjekt(user, this);
                    addButton("Add/Remove partner", partProj);
                }
            }catch(InfException e){
                System.out.println(e);
            }   
                GlobalGoals globalGoals = new GlobalGoals(user, this);
                addButton("Show Global Goals", globalGoals);
                
                SearchHandlaggare searchHandlaggare = new SearchHandlaggare(user, this);
                addButton("Search Handlaggare", searchHandlaggare);
                
		bottomMargin();
               
	}
}
