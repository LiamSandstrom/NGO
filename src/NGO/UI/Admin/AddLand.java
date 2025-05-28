package NGO.UI.Admin;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import oru.inf.InfDB;

public class AddLand extends SettingsPanelFramework {

    InfDB idb;

    public AddLand(User user, String id) {
        super(user, id);
        idb = user.getDb();
        
        Validate val = new Validate(user);

        addInfo("ID", "");
        addInfo("Name", "");
        addInfo("Language", "");
        addInfo("Currency", "");
        addInfo("Political structure", "");
        addInfo("Economy", "");

        setEditInfo();
        JButton saveBtnRef = addSaveButton();

        saveBtnRef.addActionListener(e -> {
            ArrayList<String> listRef = getTextInTextfields();
            String lid = listRef.get(0);
            String namn = listRef.get(1);
            String sprak = listRef.get(2);
            String valuta = listRef.get(3);
            String politiskStruktur = listRef.get(4);
            String ekonomi = listRef.get(5);
            if (val.idNotExists(lid, "lid", "land") && val.nameWithNumbers(namn) && val.nameWithNumbers(sprak) && val.validateCurrency(valuta) && val.nameWithNumbers(politiskStruktur) && val.nameWithNumbers(ekonomi)) {

            try {
                idb.insert("INSERT INTO land (lid, namn, sprak, valuta, politisk_struktur, ekonomi) "
                        + "VALUES (" + lid + ", '"
                        + namn + "', '"
                        + sprak + "', '"
                        + valuta + "', '"
                        + politiskStruktur + "', '"
                        + ekonomi + "')");

                System.out.println("Country saved!");
                JOptionPane.showMessageDialog(null, "Saved!!");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            }
        });
    }
}