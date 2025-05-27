/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.Admin;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import oru.inf.InfDB;

/**
 *
 * @author Pontus
 */
public class DepartmentSettingsUI extends SettingsPanelFramework{
    InfDB idb;

	public DepartmentSettingsUI(User user, String id) {
		super(user, id);

		Validate n = new Validate(user);
		idb = user.getDb();

		String avdid = "";
		String name = "";
		String description = "";
		String address = "";
		String epost = "";
		String phone = "";
		String city = "";
		String manager = "";

		try {
			avdid = idb.fetchSingle("select avdid from avdelning where avdid = " + id + ";");
			addInfo("ID", avdid);

			name = idb.fetchSingle("select namn from avdelning where avdid = " + id + ";");
			addInfo("Name", name);
                        
                        description = idb.fetchSingle("select beskrivning from avdelning where avdid = " + id + ";");
			addInfo("Description", description);

			address = idb.fetchSingle("select adress from avdelning where avdid = " + id + ";");
			addInfo("Address", address);

			epost = idb.fetchSingle("select epost from avdelning where avdid = " + id + ";");
			addInfo("Epost", epost);

			phone = idb.fetchSingle("select telefon from avdelning where avdid = " + id + ";");
			addInfo("Employed date", phone);

			city = idb.fetchSingle("select stad from avdelning where aid = " + id + ";");
			addInfo("Password", city);

			manager = idb.fetchSingle("select chef from avdelning where avdid = " + id + ";");
			addInfo("Department", manager);

		} catch (Exception e) {
			System.out.println(e);
		}

		setEditInfo();
		JButton saveBtnRef = addSaveButton();

		saveBtnRef.addActionListener(e -> {

			ArrayList<String> listRef = getTextInTextfields();
			String newID = listRef.get(0);
			String newName = listRef.get(1);
			String newDescription = listRef.get(2);
			String newAddress = listRef.get(3);
			String newEpost = listRef.get(4);
			String newPhone = listRef.get(5);
			String newCity = listRef.get(6);
			String newManager = listRef.get(7);

			
				try {
					idb.update("update avdelning set avdid = '"
						+ newID + "', namn = '"
						+ newName + "', beskrivning = '"
						+ newDescription + "', adress = '"
						+ newAddress + "', epost = '"
						+ newEpost + "', telefon = '"
						+ newPhone + "', avdelning = '"
						+ newCity + "', losenord = '"
						+ newManager + "' where aid = '" + id + "'");

					System.out.println("SAVED");
					JOptionPane.showMessageDialog(null, "Saved!");
				} catch (Exception ex) {
				}
			
		});

		
	}

}

