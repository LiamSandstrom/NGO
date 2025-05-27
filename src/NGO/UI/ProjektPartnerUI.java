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
    public ProjektPartnerUI(User user, String id) {
        super(user, id);
        this.user = user;
        this.idb = user.getDb();
        //infoDisplay();
        showPartnerInfo();
    }

    private void showPartnerInfo() {// Med all info om partner
        try {

            String queryPartnerNamn = "select distinct partner.namn from projekt "
                    + "join ans_proj on projekt.pid = ans_proj.pid "
                    + "join anstalld on ans_proj.aid = anstalld.aid "
                    + "join projekt_partner on projekt.pid = projekt_partner.pid "
                    + "join partner on projekt_partner.partner_pid = partner.pid "
                    + "where anstalld.avdelning = '" + user.getAvdelning() + "';";
            String partnerNamn = idb.fetchSingle(queryPartnerNamn);

            String queryPartnerInfo = "";
            HashMap<String, String> aPartnersInfo = idb.fetchRow("select * from partner where namn = '" + partnerNamn +"'");
            
            for(String attribute : aPartnersInfo.keySet()){
                addInfo(attribute, aPartnersInfo.get(attribute));
            }
            
            setInfo();
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
