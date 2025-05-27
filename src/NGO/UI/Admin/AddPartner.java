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
public class AddPartner extends SettingsPanelFramework {

	InfDB idb;

	public AddPartner(User user, String id) {
		super(user, id);

		Validate n = new Validate(user);
		idb = user.getDb();

		addInfo("pid", "");
		addInfo("Name", "");
		addInfo("Contact Person", "");
		addInfo("Contact Epost", "");
		addInfo("Phone number", "");
		addInfo("Adress", "");
		addInfo("Sector", "");
		addInfo("City", "");

		setEditInfo();
		JButton saveBtnRef = addSaveButton();
		saveBtnRef.addActionListener(e -> {

			ArrayList<String> listRef = getTextInTextfields();
			String newPid = listRef.get(0);
			String newName = listRef.get(1);
			String newContactperson = listRef.get(2);
			String newContactepost = listRef.get(3);
			String newPhonenumber = listRef.get(4);
			String newAdress = listRef.get(5);
			String newSector = listRef.get(6);
			String newCity = listRef.get(7);

			if (n.idNotExists(newPid, "pid", "partner")
				&& n.validateString(newName)
				&& n.validateString(newContactperson)
				&& n.validateEpost(newContactepost)
				&& n.validateTelefon(newPhonenumber)
				&& n.validateAdress(newAdress)
				&& n.branch(newSector)
				&& n.idExists(newCity, "sid", "stad")) {
				try {

					idb.insert("insert into partner (pid, namn, kontaktperson, kontaktepost, telefon, adress, branch, stad) "
						+ "values (" + newPid + ", '"
						+ newName + "', '"
						+ newContactperson + "', '"
						+ newContactepost + "', '"
						+ newPhonenumber + "', '"
						+ newAdress + "', '"
						+ newSector + "', '"
						+ newCity + "')");

					System.out.println("SAVED");
					JOptionPane.showMessageDialog(null, "Saved!");
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});

	}

}
