/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import javax.swing.JPanel;

/**
 *
 * @author liam
 */
public class AnstalldSettingsUI extends SettingsPanelFramework {
	
	public AnstalldSettingsUI(User user, String id) {
		super(user, id);

		for(int i = 0; i < 10; i++){
			addInfo("" + i, "info: " + 1);
		}
		setEditInfo();
		addSaveButton();
	}
	
		//titel
		String namn;
		String adress;
		String epost;
		String telefonnummer;
		String avdelningNamn;

		/**
		 * try { String fornamn = idb.fetchSingle("select fornamn from
		 * anstalld where aid = " + id + ";"); String efternamn =
		 * idb.fetchSingle("select efternamn from anstalld where aid = "
		 * + id + ";"); namn = fornamn + " " + efternamn;
		 *
		 * adress = idb.fetchSingle("select adress from anstalld where
		 * aid = " + id + ";");
		 *
		 * epost = idb.fetchSingle("select epost from anstalld where aid
		 * = " + id + ";");
		 *
		 * telefonnummer = idb.fetchSingle("select telefon from anstalld
		 * where aid = " + id + ";");
		 *
		 * avdelningNamn = idb.fetchSingle("select avdelning from
		 * anstalld where aid = " + id + ";");
		 *
		 * infoMap.put("Namn", namn); infoMap.put("Adress", adress);
		 * infoMap.put("Epost", epost); infoMap.put("Nmr",
		 * telefonnummer); infoMap.put("Avdelning", avdelningNamn);
		 * setInfo();
		 *
		 * } catch (Exception e) { }
		 *
		 */
}
