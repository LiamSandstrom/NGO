/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
/*public class AddRemovePartner2 extends SettingsPanelFramework {

    private InfDB idb;
    private String id;
    private User user;
    private JComboBox<String> comboBox;
    private JButton btnAdd;
    private JPanel content;

    public AddRemovePartner2(User user, String id) {
        super(user, id);
        this.id = user.getId();
        this.user = user;
        this.idb = user.getDb();
        
        getContentPanel().setLayout(new GridBagLayout());
        loadData();
        //content = getContentPanel();
        //content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        //loadProjects();
    }
    
    /*private void loadProjects(){
        content.removeAll();
        try{
            ArrayList<String> projectIds = idb.fetchColumn("select pid from projekt where projektchef = '"  + id + "'");
            if(projectIds == null || projectIds.isEmpty()){
                content.add(new JLabel("You are not project manager for any projects"));
                return;
            }
            for(String pid : projectIds){
                String projName = idb.fetchSingle("select projektnamn from projekt where pid = '" + pid + "'");
                //addInfo("",projName);
                JPanel projectPanel = new JPanel();
                projectPanel.setLayout(new BoxLayout(projectPanel, BoxLayout.Y_AXIS));
                projectPanel.setBorder(BorderFactory.createTitledBorder(projName));
                projectPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                JButton btnPart = new JButton("partners");
                JPanel partnerPanel = new JPanel();
                partnerPanel.setLayout(new BoxLayout(partnerPanel, BoxLayout.Y_AXIS));
                partnerPanel.setVisible(false);
                
                btnPart.addActionListener(e -> {
                    boolean showing = partnerPanel.isVisible();
                    if(!showing){
                        loadPartnersForProject(pid, partnerPanel);
                    }
                    partnerPanel.setVisible(!showing);
                    btnPart.setText(showing ? "Add partner" : "Close Partners");
                    revalidate();
                    repaint();
                });
                projectPanel.add(btnPart);
                projectPanel.add(partnerPanel);
                content.add(projectPanel);
                
                
                
            }
        }catch(InfException e){
            JOptionPane.showMessageDialog(this, "Error at loading projects");
        }
        revalidate();
        repaint();
    }
    
    private void loadPartnersForProject(String pid, JPanel panel){
        panel.removeAll();
        try{
            ArrayList<HashMap<String, String>> partners = idb.fetchRows("select partner_pid, namn from projekt_partner join partner on partner_pid = partner.pid where projekt_partner.pid = '" + pid + "'");
            if(partners != null && !partners.isEmpty()){
                for(HashMap<String, String> p:partners){
                    String partnerId = p.get("partner_pid");
                    String namn = p.get("namn");
                    
                    JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JLabel label = new JLabel(namn);
                    JButton btnRemove = new JButton("Remove");
                    btnRemove.setBackground(Color.red);
                    btnRemove.setForeground(Color.white);
                    
                    btnRemove.addActionListener(e->{
                        try{
                            idb.delete("delete from projekt_partner where pid = '" + pid + "' and partner_pid = '" + partnerId + "'");
                            JOptionPane.showMessageDialog(this, "Partner deleted");
                            loadPartnersForProject(pid, panel);
                        }catch(InfException ex){
                            JOptionPane.showMessageDialog(this, "Error at deleting");
                        }
                        
                    });
                    row.add(label);
                    row.add(btnRemove);
                    panel.add(row);
                    
                }
            }else{
                panel.add(new JLabel("No partners found"));
            }
            
            ArrayList<String> allPartners = idb.fetchColumn("select namn from partner");
            DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
            for(String p : allPartners) comboModel.addElement(p);
            
            JComboBox<String> comboBox = new JComboBox<>(comboModel);
            JButton btnAdd = new JButton("Add partner");
            
            JPanel addRow =  new JPanel(new FlowLayout(FlowLayout.LEFT));
            addRow.add(comboBox);
            addRow.add(btnAdd);
            
            btnAdd.addActionListener(e->{
                String selected = (String) comboBox.getSelectedItem();
                if(selected != null ){
                    try{
                        String selectedId = idb.fetchSingle("select pid from partner where namn = '" + selected + "'");
                        String exists = idb.fetchSingle("select count(*) from projekt_partner where pid = '" + pid + "' and partner_pid = '" + selectedId + "'");
                        if("0".equals(exists)){
                            idb.insert("insert into projekt_partner (pid, partner_pid) values ('" + pid + "', '" + selectedId + "')");
                            JOptionPane.showMessageDialog(this, "partner added");
                            loadPartnersForProject(pid, panel);
                        
                        
                        }else{
                            JOptionPane.showMessageDialog(this, "you are already partners");
                            
                        }
                    }catch(InfException ex){
                        JOptionPane.showMessageDialog(this, "adding unsucces");
                        
                    }
                }
            });
            panel.add(Box.createVerticalStrut(10));
            panel.add(addRow);
            
            
            
            
        }catch(InfException e){
            panel.add(new JLabel("Error loading partners"));
        }
        
    }
}
    
    


    private void loadData() {
        try {
            getInfoMap().clear();
            getContentPanel().removeAll();

            ArrayList<String> projectIds = idb.fetchColumn("select pid from projekt where projektchef = '" + id + "'");
            int counter = 0;
            for (String pid : projectIds) {

                String projName = idb.fetchSingle("select projektnamn from projekt where pid = '" + pid + "'");
                
                JButton showPartnersBtn = new JButton("Visa partners");
                showPartnersBtn.setPreferredSize(new Dimension(150, 30));
                
                GridBagConstraints gbcBtn = new GridBagConstraints();
                gbcBtn.gridx = 1;
                gbcBtn.gridy = counter;
                gbcBtn.insets = new Insets(5, 5, 5, 5);
                gbcBtn.anchor = GridBagConstraints.WEST;
                
                final String currentPid = pid;
                showPartnersBtn.addActionListener( ev ->{
                    showPartnersForProject(currentPid);
                });
                
                addInfo("Project ", projName);
                getContentPanel().add(showPartnersBtn, gbcBtn);
                counter++;
            }
            setInfo();
            revalidate();
            repaint();
        }catch(InfException e){
            JOptionPane.showMessageDialog(this, "Fel vid laddning av projekt");
        }
    }
        
    private void showPartnersForProject(String pid) {
        try {
            removeComboBox();
            
            ArrayList<HashMap<String, String>> partners = idb.fetchRows("select partner_pid, namn from projekt_partner join partner on partner_pid = partner.pid where projekt_partner.pid = '" + pid + "'");
            
            int row = getContentPanel().getComponentCount();
            
            
            if (partners != null && !partners.isEmpty()) {
                for (HashMap<String, String> p : partners) {
                    String partnerId = p.get("partner_pid");
                    String namn = p.get("namn");

                    JButton btnRemove = new JButton("Remove "+namn);
                    btnRemove.setPreferredSize(new Dimension(150, 30));
                    btnRemove.setBackground(Color.red);

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = row++;
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc.anchor = GridBagConstraints.CENTER;
                    
                    final String currentPid = pid;
                    final String currentPartnerId = partnerId;

                    btnRemove.addActionListener(ev -> {
                        try {
                            String delQuery = "delete from projekt_partner where projekt_partner.pid = '" + pid + "' and partner_pid = '" + partnerId + "'";
                            idb.delete(delQuery);
                            JOptionPane.showMessageDialog(this, "partner deleted");
                            showPartnersForProject(pid);

                        } catch (InfException ex) {
                            JOptionPane.showMessageDialog(this, "fel vid borttagning");
                        }
                    });
                    getContentPanel().add(btnRemove, gbc);
                    

                }

            } 
            
            ArrayList<String> allPartners = idb.fetchColumn("select namn from partner");
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (String p : allPartners) {
                    model.addElement(p);
            }
            comboBox = new JComboBox<>(model);
            comboBox.setPreferredSize(new Dimension(200, 30));

            GridBagConstraints gbcCombo = new GridBagConstraints();
            gbcCombo.gridx = 0;
            gbcCombo.gridy = row++;
            gbcCombo.insets = new Insets(10, 5, 5, 5);
            gbcCombo.anchor = GridBagConstraints.CENTER;
            getContentPanel().add(comboBox, gbcCombo);

            JButton btnAddPartner = new JButton("Add partner");
            btnAddPartner.setPreferredSize(new Dimension(150, 30));
            
            GridBagConstraints gbcAdd = new GridBagConstraints();
            gbcAdd.gridx = 0;
            gbcAdd.gridy = row++;
            gbcAdd.insets = new Insets(5, 5, 20, 5);
            gbcAdd.anchor = GridBagConstraints.CENTER;
            
            btnAddPartner.addActionListener(e ->{
                String selected = (String) comboBox.getSelectedItem();
                if(selected != null){
                    try{
                        String partnerId = idb.fetchSingle("select pid from partner where namn = '" + selected  +"'");
                        String checkQuery = "select * from projekt_partner where pid = '" + pid + "' and partner_pid = '" + partnerId + "'";
                        if(idb.fetchRows(checkQuery).isEmpty()){
                            String insertQuery = "insert into projekt_partner (pid, partner_pid) values ('" + pid + "', '" + partnerId + "')";
                            idb.insert(insertQuery);
                            JOptionPane.showMessageDialog(this, "partner added");
                            showPartnersForProject(pid);
                        }else{
                            JOptionPane.showMessageDialog(this, "Partner is existing");
                        }
                        
                    }catch(InfException ex){
                        JOptionPane.showMessageDialog(this, "fel");
                    }
                }
                
            });
            getContentPanel().add(btnAddPartner, gbcAdd);
            
            revalidate();
            repaint();

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "error");
        }
    }
    
    private void removeComboBox() {
        if (comboBox != null) {
            getContentPanel().remove(comboBox);
            comboBox = null;
            revalidate();
            repaint();

        }
    }

}*/
