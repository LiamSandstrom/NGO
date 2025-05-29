/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author rostykmalanchuk
 */
public class PartnerProjektUI extends SettingsPanelFramework{
    private InfDB idb;
    private User user;
    private String id;
    private ArrayList<String> name;
    private String deleteQuery;
    private String insertQuery;
    
    
    
    public PartnerProjektUI(User user, String id) {
        super(user, id);
        this.user = user;
        this.idb = user.getDb();
        this.id = id;
        
        getPartnerInfo(id);
    }

    public void getPartnerInfo(String id) {
        try {
            //Väljer ut alla partner namn som är kopplade till ett projekt, när man klickar på någon av projekt skickas projekt id med, därför kan vi anväda det i en sql fråga
            name = idb.fetchColumn("select namn from projekt_partner join partner on partner_pid = partner.pid join projekt on projekt_partner.pid = projekt.pid where projekt.pid = '" + id + "'");
            for (String proj : name) {
                String partID = idb.fetchSingle("select pid from partner where namn = '" + proj + "'");
                deleteQuery = "delete from projekt_partner where pid = '" + id + "' and partner_pid = '";
                //anropar metoden addInfo och skickar med två st strängar ett som partner id andra partner namnet till infoMap
                addInfo(partID, proj);
            }
            //anropas setEditInfo2 metoden, den gjorde så att man kan se content alltså partner namnet och knappen till höger delete, och vi skickan med en sqlfråga
            setEditInfo2(deleteQuery);
           
            ArrayList<String> allPartner = idb.fetchColumn("select namn from partner");
            //skapar en JComboBox, tilldelar den en metod som tar emot lista, och skapar comboboxen och även positionering
            JComboBox combo = getAllPartners(allPartner);
            //skapar en knapp samtidigt tilldelar metoden som skapar en knapp med färdig design
            JButton addbtn = addPartButton();
            //händelse
            addbtn.addActionListener(e -> {
                try {
                    //sparar vald partner namn till en sträng
                    String selectedPartner = (String) combo.getSelectedItem();
                    //hämtar partner id med det valda namnet
                    String choice = idb.fetchSingle("select pid from partner where namn = '" + selectedPartner + "'");
                    //skapar en sql fråga
                    insertQuery = "insert into projekt_partner (pid, partner_pid) values (" + id + ", " + choice + ")";
                    //lägger till ett samband i tabellen
                    idb.insert(insertQuery);
                    JOptionPane.showMessageDialog(null, "Partner added!");
                } catch (InfException error) {
                    JOptionPane.showMessageDialog(null, "Partner already exist!");
                }
            });
        } catch (InfException e) {
            System.out.println(e);
        }

    }
}
