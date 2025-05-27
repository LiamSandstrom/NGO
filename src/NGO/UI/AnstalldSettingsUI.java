package NGO.UI;

import NGO.Admin;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.management.relation.Role;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import oru.inf.InfDB;

public class AnstalldSettingsUI extends SettingsPanelFramework {

	InfDB idb;

	public AnstalldSettingsUI(User user, String id) {
		super(user, id);

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
			fornamn = idb.fetchSingle("select fornamn from anstalld where aid = " + id + ";");
			addInfo("First name", fornamn);

			efternamn = idb.fetchSingle("select efternamn from anstalld where aid = " + id + ";");
			addInfo("Last name", efternamn);

			adress = idb.fetchSingle("select adress from anstalld where aid = " + id + ";");
			addInfo("Adress", adress);

			epost = idb.fetchSingle("select epost from anstalld where aid = " + id + ";");
			addInfo("Epost", epost);

			telefonnummer = idb.fetchSingle("select telefon from anstalld where aid = " + id + ";");
			addInfo("Phone number", telefonnummer);

			anstDatum = idb.fetchSingle("select anstallningsdatum from anstalld where aid = " + id + ";");
			addInfo("Employed date", anstDatum);

			losenord = idb.fetchSingle("select losenord from anstalld where aid = " + id + ";");
			addInfo("Password", losenord);

			avdelning = idb.fetchSingle("select avdelning from anstalld where aid = " + id + ";");
			addInfo("Department", avdelning);

		} catch (Exception e) {
			System.out.println(e);
		}

		setEditInfo();
		JButton saveBtnRef = addSaveButtonAnstalld();
		JButton deleteBtnRef = addDeleteButton();
		JButton passwordBtnRef = addPasswordButton();

		saveBtnRef.addActionListener(e -> {

			ArrayList<String> listRef = getTextInTextfields();
			String newFornamn = listRef.get(0);
			String newEfternamn = listRef.get(1);
			String newAdress = listRef.get(2);
			String newEpost = listRef.get(3);
			String newTelefon = listRef.get(4);
			String newEmpDate = listRef.get(5);
			String newLosenord = listRef.get(6);
			String newDepartment = listRef.get(7);

			if (n.epost(newEpost)
				&& n.firstName(newFornamn)
				&& n.lastName(newEfternamn)
				&& n.adress(newAdress)
				&& n.telefon(newTelefon)
				&& n.date(newEmpDate)
				&& n.pass(newLosenord)
				&& n.idExists(newDepartment, "avdid", "avdelning")) {
				try {
					idb.update("update anstalld set fornamn = '"
						+ newFornamn + "', efternamn = '"
						+ newEfternamn + "', adress = '"
						+ newAdress + "', epost = '"
						+ newEpost + "', telefon = '"
						+ newTelefon + "', anstallningsdatum = '"
						+ newEmpDate + "', avdelning = '"
						+ newDepartment + "', losenord = '"
						+ newLosenord + "' where aid = '" + id + "'");

					System.out.println("SAVED");
					JOptionPane.showMessageDialog(null, "Saved!");
				} catch (Exception ex) {
				}
			}
		});

		deleteBtnRef.addActionListener(e -> {
			try {
				idb.update("update handlaggare set mentor = NULL where mentor = " + id + ";");
				idb.update("update avdelning set chef = NULL where chef = " + id + ";");
				idb.delete("delete from ans_proj where aid = " + id + ";");

				idb.delete("delete from handlaggare where aid = " + id + ";");
				idb.delete("delete from admin where aid = " + id + ";");

				idb.delete("delete from anstalld where aid = " + id + ";");

				JOptionPane.showMessageDialog(null, "Deleted!");
			} catch (Exception ex) {
				System.out.println(ex);
			}
		});

		passwordBtnRef.addActionListener(e -> {

			Admin admin = (Admin) user;
			String rand = admin.generateRandomPassword(10);
			getTextField(6).setText(rand);
		});
	}

}
