package NGO.UI.Admin;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import oru.inf.InfDB;

public class AddAnstalld extends SettingsPanelFramework {

	InfDB idb;

	public AddAnstalld(User user, String id) {
		super(user, id);
		idb = user.getDb();

		Validate n = new Validate(user);
		idb = user.getDb();

		String fornamn = "";
		String efternamn = "";
		String adress = "";
		String epost = "";
		String telefonnummer = "";
		String anstDatum = "";
		String losenord = "";
		String avdelning = "";

		try {
			addInfo("aid", "");
			addInfo("First name", fornamn);
			addInfo("Last name", efternamn);
			addInfo("Adress", adress);
			addInfo("Epost", epost);
			addInfo("Phone number", telefonnummer);
			addInfo("Employed date", anstDatum);
			addInfo("Password", losenord);
			addInfo("Department", avdelning);
		} catch (Exception e) {
			System.out.println(e);
		}

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

			boolean newDep = false;
			try {
				idb.fetchSingle("select * from avdelning where avdid = " + newDepartment + ";");
				newDep = true;
			} catch (Exception ex) {
				System.out.println(ex);
			}

			if (n.epost(newEpost)
				&& n.firstName(newFornamn)
				&& n.lastName(newEfternamn)
				&& n.adress(newAdress)
				&& n.epost(newEpost)
				&& n.telefon(newTelefon)
				&& n.date(newEmpDate)
				&& n.pass(newLosenord)
				&& newDep == true) {
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
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});

	}

}
