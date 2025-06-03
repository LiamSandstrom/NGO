package NGO.UI.Admin;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import oru.inf.InfDB;

public class LandUI extends SettingsPanelFramework {

	private InfDB idb;

	public LandUI(User user, String id) {
		super(user, id);
		idb = user.getDb();

		Validate val = new Validate(user);

		String name = "";
		String language = "";
		String currency = "";
		String politicalStructure = "";
		String economy = "";

		try {

			name = idb.fetchSingle("select namn from land where lid = " + id + ";");
			addInfo("Name", name);

			language = idb.fetchSingle("select sprak from land where lid = " + id + ";");
			addInfo("Language", language);

			currency = idb.fetchSingle("select valuta from land where lid = " + id + ";");
			addInfo("Currency", currency);

			politicalStructure = idb.fetchSingle("select politisk_struktur from land where lid = " + id + ";");
			addInfo("Political structure", politicalStructure);

			economy = idb.fetchSingle("select ekonomi from land where lid = " + id + ";");
			addInfo("Economy", economy);

		} catch (Exception e) {
			System.out.println("Error loading land data: " + e.getMessage());
		}

		setInfo();
	}
}
