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
public class PartnerSettingsUI extends SettingsPanelFramework {

	InfDB idb;
	
	public PartnerSettingsUI(User user, String id) {
		super(user, id);
		
		Validate n = new Validate(user);
		idb = user.getDb();

		try {
			String namn = idb.fetchSingle("SELECT namn FROM partner WHERE pid = " + id + ";");
			addInfo("Name", namn);

			String kontaktPerson = idb.fetchSingle("SELECT kontaktperson FROM partner WHERE pid = " + id + ";");
			addInfo("Contact Person", kontaktPerson);

			String kontaktEpost = idb.fetchSingle("SELECT kontaktepost FROM partner WHERE pid = " + id + ";");
			addInfo("Contact Epost", kontaktEpost);

			String telefon = idb.fetchSingle("SELECT telefon FROM partner WHERE pid = " + id + ";");
			addInfo("Phone number", telefon);

			String adress = idb.fetchSingle("SELECT adress FROM partner WHERE pid = " + id + ";");
			addInfo("Adress", adress);

			String branch = idb.fetchSingle("SELECT branch FROM partner WHERE pid = " + id + ";");
			addInfo("Sector", branch);

			String stad = idb.fetchSingle("SELECT stad FROM partner WHERE pid = " + id + ";");
			addInfo("City", stad);

		} catch (Exception e) {
			System.out.println(e);
		}

		setEditInfo();

		JButton saveBtnRef = addSaveButton();
		JButton deleteBtnRef = addDeleteButton();

		saveBtnRef.addActionListener(e -> {

			ArrayList<String> listRef = getTextInTextfields();
			String newNamn = listRef.get(0);
			String newKontaktperson = listRef.get(1);
			String newKontaktepost = listRef.get(2);
			String newTelefon = listRef.get(3);
			String newAdress = listRef.get(4);
			String newBranch = listRef.get(5);
			String newStad = listRef.get(6);

			if (n.validateString(newNamn)
				&& n.validateString(newKontaktperson)
				&& n.validateEpost(newKontaktepost)
				&& n.validateTelefon(newTelefon)
				&& n.validateAdress(newAdress)
				&& n.branch(newBranch)
				&& n.idExists(newStad, "sid", "stad")) {
				try {
					idb.update("UPDATE partner SET "
						+ "namn = '" + newNamn + "', "
						+ "kontaktperson = '" + newKontaktperson + "', "
						+ "kontaktepost = '" + newKontaktepost + "', "
						+ "telefon = '" + newTelefon + "', "
						+ "adress = '" + newAdress + "', "
						+ "branch = '" + newBranch + "', "
						+ "stad = '" + newStad + "' "
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
				idb.delete("DELETE FROM partner WHERE pid = " + id + ";");
				System.out.println("DELETED id: " + id);
			} catch (Exception ex) {
				System.out.println("Delete fail: " + ex);
			}
		});
	}
	
}
