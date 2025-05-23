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
public class SearchHandlaggareInfoPanel extends SettingsPanelFramework {
    
    String aid;
    String firstname;
    String lastname;
    String address;
    String epost;
    String phone;
    String date;
    String department;
    InfDB idb;
    
    public SearchHandlaggareInfoPanel(User user, String id) {
        super(user, id);
        idb = user.getDb();
        getInfo(id);
        addInfo("ID", aid);
        addInfo("First name", firstname);
        addInfo("Last name", lastname);
        addInfo("Address", address);
        addInfo("Epost", epost);
        addInfo("Phone number", phone);
        addInfo("Employment date", date);
        addInfo("Department", department);
        
        setInfo();
    }
    
    private void getInfo(String id) {
        try {
            aid = idb.fetchSingle("select aid from anstalld where aid = " + id + ";");
            firstname = idb.fetchSingle("select fornamn from anstalld where aid = " + id + ";");
            lastname = idb.fetchSingle("select efternamn from anstalld where aid = " + id + ";");
            address = idb.fetchSingle("select adress from anstalld where aid = " + id + ";");
            epost = idb.fetchSingle("select epost from anstalld where aid = " + id + ";");
            phone = idb.fetchSingle("select telefon from anstalld where aid = " + id + ";");
            date = idb.fetchSingle("select anstallningsdatum from anstalld where aid = " + id + ";");
            department = idb.fetchSingle("select avdelning from anstalld where aid = " + id + ";");
            

        } catch (Exception e) {
        }

    }
    
}
