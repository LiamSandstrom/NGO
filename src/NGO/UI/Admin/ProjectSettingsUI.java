/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Admin;

import NGO.UI.AnstalldSettingsUI;
import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import oru.inf.InfDB;

/**
 *
 * @author liam
 */
public class ProjectSettingsUI extends SettingsPanelFramework {

	InfDB idb;

	public ProjectSettingsUI(User user, String id) {
		super(user, id);

		Validate n = new Validate(user);
		idb = user.getDb();

		List<String> handlaggare = new ArrayList<>();
		List<String> landList = new ArrayList<>();

		try {
			String namn = idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid = " + id + ";");
			addInfo("Name", namn);

			String beskrivning = idb.fetchSingle("SELECT beskrivning FROM projekt WHERE pid = " + id + ";");
			addInfo("Description", beskrivning);

			String startDatum = idb.fetchSingle("SELECT startdatum FROM projekt WHERE pid = " + id + ";");
			addInfo("Start date", startDatum);

			String slutDatum = idb.fetchSingle("SELECT slutdatum FROM projekt WHERE pid = " + id + ";");
			addInfo("End date", slutDatum);

			String kostnad = idb.fetchSingle("SELECT kostnad FROM projekt WHERE pid = " + id + ";");
			addInfo("Cost", kostnad);

			String status = idb.fetchSingle("SELECT status FROM projekt WHERE pid = " + id + ";");
			addInfo("Status", status);

			String prioritet = idb.fetchSingle("SELECT prioritet FROM projekt WHERE pid = " + id + ";");
			addInfo("Priority", prioritet);

			//Proj Chef
			handlaggare = idb.fetchColumn("SELECT aid FROM handlaggare;");
			HashMap idNamnMap = new HashMap<>();
			for(String aid : handlaggare){
				String handNamn = idb.fetchSingle("SELECT fornamn FROM anstalld where aid = " + aid + ";"); 
				idNamnMap.put(aid, handNamn);
			}
			String currChef = idb.fetchSingle("select projektchef from projekt where pid = " + id + ";");
			String förvaltNamn = idb.fetchSingle("select fornamn from anstalld where aid = " + currChef + ";");
			
			//Land
			landList = idb.fetchColumn("SELECT lid FROM land;");
			HashMap idNamnMap2 = new HashMap<>();
			for(String lid : landList){
				String landNamn = idb.fetchSingle("SELECT namn FROM land where lid = " + lid + ";"); 
				idNamnMap2.put(lid, landNamn);
			}
			String currLand = idb.fetchSingle("select land from projekt where pid = " + id + ";");
			String förvaltNamn2 = idb.fetchSingle("select namn from land where lid = " + currLand + ";");

			setEditInfo();
			addLinkEditInfo("Project Manager", idNamnMap, idInput -> new AnstalldSettingsUI(user, idInput), förvaltNamn);
			addLinkEditInfo("Country", idNamnMap2, idInput -> new LandSettingsUI(user, idInput), förvaltNamn2);

		} catch (Exception e) {
			System.out.println(e);
		}


		JButton saveBtnRef = addSaveButton();
		JButton deleteBtnRef = addDeleteButton();

		saveBtnRef.addActionListener(e -> {

			ArrayList<String> listRef = getTextInTextfields();
			String newNamn = listRef.get(0);
			String newBeskrivning = listRef.get(1);
			String newStartdatum = listRef.get(2);
			String newSlutdatum = listRef.get(3);
			String newKostnad = listRef.get(4);
			String newStatus = listRef.get(5);
			String newPrioritet = listRef.get(6);
			String newProjektchef = listRef.get(7);
			String newLand = listRef.get(8);
			System.out.println(newLand);
			System.out.println(newProjektchef);

			if (n.projName(newNamn)
				&& n.description(newBeskrivning)
				&& n.date(newStartdatum)
				&& n.date(newSlutdatum)
				&& n.cost(newKostnad)
				&& n.status(newStatus)
				&& n.prio(newPrioritet)
				&& n.idExists(newProjektchef, "aid","handlaggare")
				&& n.idExists(newLand, "lid", "land")) {
				try {
					idb.update("UPDATE projekt SET "
						+ "projektnamn = '" + newNamn + "', "
						+ "beskrivning = '" + newBeskrivning + "', "
						+ "startdatum = '" + newStartdatum + "', "
						+ "slutdatum = '" + newSlutdatum + "', "
						+ "kostnad = '" + newKostnad + "', "
						+ "status = '" + newStatus + "', "
						+ "prioritet = '" + newPrioritet + "', "
						+ "projektchef = '" + newProjektchef + "', "
						+ "land = '" + newLand + "' "
						+ "WHERE pid = '" + id + "';");

					System.out.println("SAVED");
				JOptionPane.showMessageDialog(null, "Saved!");
				} catch (Exception ex) {
					System.out.println("Uppdatering misslyckades: " + ex);
				}
			} else {
				System.out.println("Fail if");
			}
		});

		deleteBtnRef.addActionListener(e -> {
			try {
				idb.delete("DELETE FROM projekt WHERE pid = " + id + ";");
				System.out.println("DELETED id: " + id);
			} catch (Exception ex) {
				System.out.println("Delete fail: " + ex);
			}
		});

	}

}
