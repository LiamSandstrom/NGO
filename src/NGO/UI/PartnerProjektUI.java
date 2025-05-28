/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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
    private JComboBox<String> comboBox;
    private JButton btnAdd;
    private ArrayList<String> pid;
    private String deleteQuery;
    private String insertQuery;
    
    
    
    public PartnerProjektUI(User user, String id) {
        super(user, id);
        this.user = user;
        this.idb = user.getDb();
        this.id = id;
        
        getPartnerInfo(id);
        
        
        System.out.println(id);
        
    
    }

    public void getPartnerInfo(String id) {
        try {
            
            pid = idb.fetchColumn("select namn from projekt_partner join partner on partner_pid = partner.pid join projekt on projekt_partner.pid = projekt.pid where projekt.pid = '" + id + "'");
            for (String proj : pid) {
                String partID = idb.fetchSingle("select pid from partner where namn = '" + proj + "'");
                
                deleteQuery = "delete from projekt_partner where pid = '" + id + "' and partner_pid = '";
                addInfo(partID, proj);
            }
            setEditInfo2(deleteQuery);
            
            ArrayList<String> allPartner = idb.fetchColumn("select namn from partner");

            JComboBox combo = getAllPartners(allPartner);
            JButton addbtn = addPartButton();
            addbtn.addActionListener(e -> {
                try {
                    String selectedPartner = (String) combo.getSelectedItem();
                    String choice = idb.fetchSingle("select pid from partner where namn = '" + selectedPartner + "'");
                    String checkQuery = "select * from projekt_partner where pid = " + id + " and partner_pid = " + choice;
                    insertQuery = "insert into projekt_partner (pid, partner_pid) values (" + id + ", " + choice + ")";
                    idb.insert(insertQuery);
                } catch (InfException error) {
                    JOptionPane.showMessageDialog(null, "Partner already exist!");
                }
            });
        } catch (InfException e) {
            System.out.println(e);
        }

    }
}
