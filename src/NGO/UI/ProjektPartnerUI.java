/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class ProjektPartnerUI extends SettingsPanelFramework{
    private User user;
    private InfDB idb;
    private String id;
    public ProjektPartnerUI(User user, String id) {
        super(user, id);
        this.user = user;
        this.idb = user.getDb();
        this.id = id;
        //infoDisplay();
        showPartnerInfo(id);
        
    }

    private void showPartnerInfo(String projID) {// Med all info om partner
        try {

            /*
            String queryPartnerNamn = "select distinct partner.namn from projekt "
                    + "join ans_proj on projekt.pid = ans_proj.pid "
                    + "join anstalld on ans_proj.aid = anstalld.aid "
                    + "join projekt_partner on projekt.pid = projekt_partner.pid "
                    + "join partner on projekt_partner.partner_pid = partner.pid "
                    + "where anstalld.avdelning = '" + user.getAvdelning() + "';";*/
            String pids = "select projekt.pid from projekt "
                    + "join ans_proj on projekt.pid = ans_proj.pid "
                    + "join anstalld on ans_proj.aid = anstalld.aid "
                    + "where anstalld.aid = '" + user.getId() +"'";
            ArrayList<String> projIds = idb.fetchColumn(pids);
            
            

            String queryPartnerInfo = "";
            
            
            ArrayList<HashMap<String, String>> partnersInfo = idb.fetchRows("select partner.pid, partner.namn, partner.kontaktperson, partner.kontaktepost, partner.telefon, partner.adress, partner.branch, partner.stad from partner "
                    + "join projekt_partner on partner_pid = partner.pid join projekt on projekt_partner.pid = projekt.pid where projekt.pid = '" + projID +"'");
            for(HashMap<String, String> partInfo: partnersInfo){
            for (String apartnersInfo : partInfo.keySet()) {
                addInfo("Partner","" );
                addInfo(apartnersInfo, partInfo.get(apartnersInfo));
                
            }
            setInfo();
            }
        } catch (InfException e) {
            e.printError();
        }

    }
    
    private void infoDisplay(){
        try{
            /*String queryProjNamn = "select distinct projekt.projektnamn from projekt "
                    + "join ans_proj on projekt.pid = ans_proj.pid "
                    + "join anstalld on ans_proj.aid = anstalld.aid "
                    + "join projekt_partner on projekt.pid = projekt_partner.pid "
                    + "join partner on projekt_partner.partner_pid = partner.pid "
                    + "where anstalld.avdelning = '" + user.getAvdelning()+ "' and anstalld = '" + user.getId() +"';";
            //String projNamn = idb.fetchSingle(queryProjNamn);*/
            
            String queryPartnerNamn = "select distinct partner.namn from projekt "
                    + "join ans_proj on projekt.pid = ans_proj.pid "
                    + "join anstalld on ans_proj.aid = anstalld.aid "
                    + "join projekt_partner on projekt.pid = projekt_partner.pid "
                    + "join partner on projekt_partner.partner_pid = partner.pid "
                    + "where anstalld.avdelning = '" + user.getAvdelning() + "';";
            String partnerNamn = idb.fetchSingle(queryPartnerNamn);

            addInfo("Partner", partnerNamn);
            setInfo();
        }catch(InfException e){
            e.printError();
        }
    }
}
