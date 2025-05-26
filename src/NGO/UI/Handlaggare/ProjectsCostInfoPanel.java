package NGO.UI.Handlaggare;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import javax.swing.JButton;
import java.util.ArrayList;
import oru.inf.InfDB;

public class ProjectsCostInfoPanel extends SettingsPanelFramework {

    private String pid;
    private String projectname;
    private String description;
    private String startdate;
    private String enddate;
    private String cost;
    private String status;
    private String priority;
    private String projektchefId;
    private InfDB idb;
    private User user;

    public ProjectsCostInfoPanel(User user, String id) {
        super(user, id);
        this.user = user;
        idb = user.getDb();
        getInfo(id);

        try {
            projektchefId = idb.fetchSingle("select projektchef from projekt where pid = " + id + ";");
        } catch (Exception e) {
            projektchefId = "";
        }

        if (user.getId().equals(projektchefId)) {
            addInfo("ID", pid);
            addInfo("Name", projectname);
            addInfo("Description", description);
            addInfo("Start date", startdate);
            addInfo("End date", enddate);
            addInfo("Cost", cost);
            addInfo("Status", status);
            addInfo("Priority", priority);

            setEditInfo();

            JButton saveBtn = addSaveButton();
            saveBtn.addActionListener(e -> {
                ArrayList<String> values = getTextInTextfields();
                String newName = values.get(1);
                String newDescription = values.get(2);
                String newStart = values.get(3);
                String newEnd = values.get(4);
                String newCost = values.get(5);
                String newStatus = values.get(6);
                String newPriority = values.get(7);

                try {
                    idb.update("update projekt set projektnamn = '" + newName
                            + "', beskrivning = '" + newDescription
                            + "', startdatum = '" + newStart
                            + "', slutdatum = '" + newEnd
                            + "', kostnad = '" + newCost
                            + "', status = '" + newStatus
                            + "', prioritet = '" + newPriority
                            + "' WHERE pid = " + pid + ";");

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });

        } else {
            addInfo("ID", pid);
            addInfo("Name", projectname);
            addInfo("Description", description);
            addInfo("Start date", startdate);
            addInfo("End date", enddate);
            addInfo("Cost", cost);
            addInfo("Status", status);
            addInfo("Priority", priority);

            setInfo();
        }
    }

    private void getInfo(String id) {
        try {
            pid = idb.fetchSingle("select pid from projekt where pid = " + id + ";");
            projectname = idb.fetchSingle("select projektnamn from projekt where pid = " + id + ";");
            description = idb.fetchSingle("select beskrivning from projekt where pid = " + id + ";");
            startdate = idb.fetchSingle("select startdatum from projekt where pid = " + id + ";");
            enddate = idb.fetchSingle("select slutdatum from projekt where pid = " + id + ";");
            cost = idb.fetchSingle("select kostnad from projekt where pid = " + id + ";");
            status = idb.fetchSingle("select status from projekt where pid = " + id + ";");
            priority = idb.fetchSingle("select prioritet from projekt where pid = " + id + ";");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}