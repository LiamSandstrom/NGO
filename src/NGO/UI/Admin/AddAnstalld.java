package NGO.UI.Admin;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import oru.inf.InfDB;

public class AddAnstalld extends SettingsPanelFramework {

	InfDB idb;

	public AddAnstalld(User user, String id) {
		super(user, id);
		idb = user.getDb();

		Validate n = new Validate(user);
		idb = user.getDb();

		addInfo("aid", "");
		addInfo("First name", "");
		addInfo("Last name", "");
		addInfo("Adress", "");
		addInfo("Epost", "");
		addInfo("Phone number", "");
		addInfo("Employed date", "");
		addInfo("Password", "");
		addInfo("Department", "");

		setEditInfo();
		JButton saveBtnRef = addSaveButton();
		saveBtnRef.addActionListener(e -> {

			ArrayList<String> listRef = getTextInTextfields();
			String newAid = listRef.get(0);
			String newFornamn = listRef.get(1);
			String newEfternamn = listRef.get(2);
			String newAdress = listRef.get(3);
			String newEpost = listRef.get(4);
			String newTelefon = listRef.get(5);
			String newEmpDate = listRef.get(6);
			String newLosenord = listRef.get(7);
			String newDepartment = listRef.get(8);


			if (n.epost(newEpost)
				&& n.idNotExists(newAid, "aid", "anstalld")
				&& n.firstName(newFornamn)
				&& n.lastName(newEfternamn)
				&& n.adress(newAdress)
				&& n.telefon(newTelefon)
				&& n.date(newEmpDate)
				&& n.pass(newLosenord)
				&& n.idExists(newDepartment, "avdid", "avdelning")) {
				try {

					idb.insert("insert into anstalld (aid, fornamn, efternamn, adress, epost, telefon, anstallningsdatum, avdelning, losenord) "
						+ "values (" + newAid + ", '"
						+ newFornamn + "', '"
						+ newEfternamn + "', '"
						+ newAdress + "', '"
						+ newEpost + "', '"
						+ newTelefon + "', '"
						+ newEmpDate + "', "
						+ newDepartment + ", '"
						+ newLosenord + "')");

					System.out.println("SAVED");
					JOptionPane.showMessageDialog(null, "Saved!");
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});

	}

}
