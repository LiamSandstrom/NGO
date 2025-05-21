/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.util.ArrayList;
import oru.inf.InfDB;

/**
 *
 * @author liam
 */
public class GlobalGoalsUI extends ContentPanelStructure {
	InfDB idb;
	
	public GlobalGoalsUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);
		idb = user.getDb();

		try {
			ArrayList<String> goals = idb.fetchColumn("select hid from hallbarhetsmal;");

			//ScrollListPanel cardList = new ScrollListPanel(goals,
				//() -> )
		} catch (Exception e) {
		}
	}
	
	
}
