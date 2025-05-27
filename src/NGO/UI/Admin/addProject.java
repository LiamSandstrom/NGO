/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Admin;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import oru.inf.InfDB;

/**
 *
 * @author liam
 */
public class addProject extends SettingsPanelFramework {

	InfDB idb;

	public addProject(User user, String id) {
		super(user, id);

		Validate n = new Validate(user);
		idb = user.getDb();

		String empty = "";

		addInfo("pid", empty);
		addInfo("Name", empty);
		addInfo("Description", empty);
		addInfo("Start date", empty);
		addInfo("End date", empty);
		addInfo("Cost", empty);
		addInfo("Status", empty);
		addInfo("Priority", empty);
		addInfo("Project manager", empty);
		addInfo("Country", empty);

		setEditInfo();

		JButton saveBtnRef = addSaveButton();

		saveBtnRef.addActionListener(e -> {

			ArrayList<String> listRef = getTextInTextfields();
			String newPid = listRef.get(0);
			String newNamn = listRef.get(1);
			String newBeskrivning = listRef.get(2);
			String newStartdatum = listRef.get(3);
			String newSlutdatum = listRef.get(4);
			String newKostnad = listRef.get(5);
			String newStatus = listRef.get(6);
			String newPrioritet = listRef.get(7);
			String newProjektchef = listRef.get(8);
			String newLand = listRef.get(9);

			boolean projektFinns = true;

			try {
				idb.fetchSingle("SELECT * FROM anstalld WHERE aid = " + newProjektchef + ";");
				projektFinns = true;
			} catch (Exception ex) {
				System.out.println("Projekt finns inte: " + ex);
			}

			if (n.projName(newNamn)
				&& n.description(newBeskrivning)
				&& n.date(newStartdatum)
				&& n.date(newSlutdatum)
				&& n.cost(newKostnad)
				&& n.status(newStatus)
				&& n.prio(newPrioritet)
				&& projektFinns) {
				try {
					String sql = "INSERT INTO projekt (pid, projektnamn, beskrivning, startdatum, slutdatum, kostnad, status, prioritet, projektchef, land) "
						+ "VALUES (" + newPid + ", '"
						+ newNamn + "', '"
						+ newBeskrivning + "', '"
						+ newStartdatum + "', '"
						+ newSlutdatum + "', "
						+ newKostnad + ", '"
						+ newStatus + "', '"
						+ newPrioritet + "', '"
						+ newProjektchef + "', '"
						+ newLand + "')";

					System.out.println("DEBUG SQL: " + sql); // <-- här skrivs det ut
					idb.insert(sql);

					System.out.println("SAVED");
					JOptionPane.showMessageDialog(null, "Saved!");
				} catch (Exception ex) {
					System.out.println("Uppdatering misslyckades: " + ex);
				}
			} else {
				System.out.println("Fail if");
			}
		});
	}

}
