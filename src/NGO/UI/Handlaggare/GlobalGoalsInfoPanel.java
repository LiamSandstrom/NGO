package NGO.UI.Handlaggare;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import oru.inf.InfDB;


public class GlobalGoalsInfoPanel extends SettingsPanelFramework {

    String hid;
    String name;
    String goalNumber;
    String description;
    String priority;
    InfDB idb;

    public GlobalGoalsInfoPanel(User user, String id) {
        super(user, id);
        idb = user.getDb();
        getInfo(id);
        addInfo("ID", hid);
        addInfo("Name", name);
        addInfo("Goal Number", goalNumber);
        addInfo("Description", description);
        addInfo("Priority", priority);

        setInfo();
    }

    private void getInfo(String id) {
        try {
            hid = idb.fetchSingle("select hid from hallbarhetsmal where hid = " + id + ";");
            name = idb.fetchSingle("select namn from hallbarhetsmal where hid = " + id + ";");
            goalNumber = idb.fetchSingle("select malnummer from hallbarhetsmal where hid = " + id + ";");
            description = idb.fetchSingle("select beskrivning from hallbarhetsmal where hid = " + id + ";");
            priority = idb.fetchSingle("select prioritet from hallbarhetsmal where hid = " + id + ";");

        } catch (Exception e) {
        }

    }
}
