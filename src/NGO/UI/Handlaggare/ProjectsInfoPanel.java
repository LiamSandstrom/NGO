/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Handlaggare;

import NGO.UI.SettingsPanelFramework;
import NGO.User;
import oru.inf.InfDB;

/**
 *
 * @author Pontus
 */
public class ProjectsInfoPanel extends SettingsPanelFramework {

    String pid;
    String name;
    String description;
    String startdate;
    String enddate;
    String cost;
    String status;
    String priority;
    String manager;
    String country;
    InfDB idb;

    public ProjectsInfoPanel(User user, String id) {
        super(user, id);
        idb = user.getDb();
        getInfo(id);
        addInfo("Name", name);
        addInfo("Descripton", description);
        addInfo("Start date", startdate);
        addInfo("End date", enddate);
        addInfo("Cost", cost);
        addInfo("Status", status);
        addInfo("Priority", priority);
        addInfo("Manager", manager);
        addInfo("Country", country);

        setInfo();
    }

    private void getInfo(String id) {
        try {
            pid = idb.fetchSingle("select pid from projekt where pid = " + id + ";");
            name = idb.fetchSingle("select projektnamn from projekt where pid = " + id + ";");
            description = idb.fetchSingle("select beskrivning from projekt where pid = " + id + ";");
            startdate = idb.fetchSingle("select startdatum from projekt where pid = " + id + ";");
            enddate = idb.fetchSingle("select slutdatum from projekt where pid = " + id + ";");
            cost = idb.fetchSingle("select kostnad from projekt where pid = " + id + ";");
            status = idb.fetchSingle("select status from projekt where pid = " + id + ";");
            priority = idb.fetchSingle("select prioritet from projekt where pid = " + id + ";");
            manager = idb.fetchSingle("select projektchef from projekt where pid = " + id + ";");
            country = idb.fetchSingle("select land from projekt where pid = " + id + ";");
        } catch (Exception e) {
        }

    }
}
