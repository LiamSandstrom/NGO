package NGO.UI.Admin;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import NGO.Validate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import oru.inf.InfDB;

public class LandSettingsUI extends SettingsPanelFramework {

    private InfDB idb;

    public LandSettingsUI(User user, String id) {
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

        setEditInfo();
        JButton saveBtnRef = addSaveButton();

        saveBtnRef.addActionListener(e -> {
            ArrayList<String> listRef = getTextInTextfields();
            String newName = listRef.get(0);
            String newLanguage = listRef.get(1);
            String newCurrency = listRef.get(2);
            String newPoliticalStructure = listRef.get(3);
            String newEconomy = listRef.get(4);

            if (val.nameWithNumbers(newName) && val.nameWithNumbers(newLanguage) && val.validateCurrency(newCurrency) && val.nameWithNumbers(newPoliticalStructure) && val.nameWithNumbers(newEconomy)) {
                try {
                    idb.update("update land set namn = '" + newName
                            + "', sprak = '" + newLanguage
                            + "', valuta = '" + newCurrency
                            + "', politisk_struktur = '" + newPoliticalStructure
                            + "', ekonomi = '" + newEconomy
                            + "' where lid = '" + id + "';");

                    System.out.println("Saved");
                    JOptionPane.showMessageDialog(null, "Saved!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error saving changes:\n" + ex.getMessage());
                }
            }
        });
    }
}
