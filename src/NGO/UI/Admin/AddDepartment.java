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
 * @author Pontus
 */
public class AddDepartment extends SettingsPanelFramework {

    InfDB idb;

    public AddDepartment(User user, String id) {
        super(user, id);
        idb = user.getDb();

        Validate val = new Validate(user);

        addInfo("ID", "");
        addInfo("Name", "");
        addInfo("Description", "");
        addInfo("Address", "");
        addInfo("Epost", "");
        addInfo("Phone number", "");
        addInfo("City", "");
        addInfo("Manager", "");

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
            if (val.idNotExists(newID, "avdid", "avdelning") && val.partnerName(newName) && val.description(newDescription) && val.validateAdress(newAddress) && val.validateEpost(newEpost) && val.validateTelefon(newPhone) && val.id(newCity) && val.id(newManager)){
			
            try {

                idb.insert("INSERT INTO avdelning (avdid, namn, beskrivning, adress, epost, telefon, stad, chef) "
                        + "VALUES (" + newID + ", '"
                        + newName + "', '"
                        + newDescription + "', '"
                        + newAddress + "', '"
                        + newEpost + "', '"
                        + newPhone + "', "
                        + newCity + ", "
                        + newManager + ")");

                System.out.println("SAVED");
                JOptionPane.showMessageDialog(null, "Saved!");
            } catch (Exception ex) {
                System.out.println(ex);
            }
            }
        });
    }

}
