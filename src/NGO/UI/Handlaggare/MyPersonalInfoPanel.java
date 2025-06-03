/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;
import NGO.UI.ContentPanelStructure;
import NGO.UI.SettingsPanelFramework;
import NGO.UI.UIStructure;
import NGO.Validate;
import NGO.User;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class MyPersonalInfoPanel extends SettingsPanelFramework{
    
    private InfDB idb;
    
    public MyPersonalInfoPanel(User user, String id){
        super(user, id);
        Validate val = new Validate(user);
		idb = user.getDb();

		String fornamn = "";
		String efternamn = "";
		String adress = "";
		String epost = "";
		String telefonnummer = "";
		String losenord = "";
		

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

			losenord = idb.fetchSingle("select losenord from anstalld where aid = " + id + ";");
			addInfo("Password", losenord);

			

		} catch (Exception e) {
			System.out.println(e);
		}

		setEditInfo();
		JButton saveBtnRef = addSaveButton();
		saveBtnRef.addActionListener(e -> {

			ArrayList<String> listRef = getTextInTextfields();
			String newFornamn = listRef.get(0);
			String newEfternamn = listRef.get(1);
			String newAdress = listRef.get(2);
			String newEpost = listRef.get(3);
			String newTelefon = listRef.get(4);
			String newLosenord = listRef.get(5);

			if (val.epost(newEpost)
				&& val.firstName(newFornamn)
				&& val.lastName(newEfternamn)
				&& val.adress(newAdress)
				&& val.epost(newEpost)
				&& val.telefon(newTelefon)
				&& val.pass(newLosenord)) {
				try {
					idb.update("update anstalld set fornamn = '"
						+ newFornamn + "', efternamn = '"
						+ newEfternamn + "', adress = '"
						+ newAdress + "', epost = '"
						+ newEpost + "', telefon = '"
						+ newTelefon + "', losenord = '"
						+ newLosenord + "' where aid = '" + id + "'");
                                        JOptionPane.showMessageDialog(null, "Your changes have been saved!");

				System.out.println("SAVED");
				} catch (Exception ex) {
				}
			}
		});
	}
}
