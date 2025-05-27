/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.Validate;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class ResetPassword extends JFrame{
    
    private int inputWidth;
    private int inputHeight;
    private int textSize;
    private JPanel pan;
    private JTextField epostField;
    private JTextField newPass;
    private JTextField passConfirm;
    private Validate val;
    private InfDB idb;
    
    public ResetPassword(InfDB idb, int inputWidth, int inputHeight, int textSize){
        val = new Validate(); 
        this.idb = idb;
        this.inputWidth = inputWidth;
        this.inputHeight = inputHeight;
        this.textSize = textSize;
        setTitle("Reset Password");
        
//Hela gridden
        pan = new JPanel();
        pan.setBackground(new Color(40, 40, 40));
        pan.setLayout(new GridBagLayout());
        pan.setPreferredSize(new Dimension(800, 700));
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(pan);
        
        
//Email
        //Label
        JLabel epostLabel = new JLabel("Email adress");
        epostLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
        gbc.gridx = 0;
        gbc.gridy = 1;
        pan.add(epostLabel, gbc);
        //Fält
        epostField = new JTextField();
        epostField.setPreferredSize(new Dimension(inputWidth, inputHeight));
        gbc.gridx = 0;
        gbc.gridy = 2;
        pan.add(epostField, gbc);
        
//Password
        //Label
        JLabel passLabel = new JLabel("New Password");
        passLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
        gbc.gridx = 0;
        gbc.gridy = 3;
        pan.add(passLabel, gbc);
        //Fält
        newPass = new JTextField();
        newPass.setPreferredSize(new Dimension(inputWidth, inputHeight));
        gbc.gridx = 0;
        gbc.gridy = 4;
        pan.add(newPass, gbc);
        
//Confirm Password
        //Label
        JLabel confirmLabel = new JLabel("Confirm Password");
        epostLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
        gbc.gridx = 0;
        gbc.gridy = 5;
        pan.add(confirmLabel, gbc);
        //Fält
        passConfirm = new JTextField();
        passConfirm.setPreferredSize(new Dimension(inputWidth, inputHeight));
        gbc.gridx = 0;
        gbc.gridy = 6;
        pan.add(passConfirm, gbc);
        
//Knapp
        JButton resetbtn = new JButton("Reset"); 
        resetbtn.setPreferredSize(new Dimension(inputWidth, 40));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 0, 0, 0);
        pan.add(resetbtn, gbc);
        
        resetbtn.addActionListener(e -> {
            executeReset();
        });
    }
    
    private void executeReset(){
        String email = epostField.getText();
        String newPassword = newPass.getText();
        String confPass = passConfirm.getText();
        String accId = "";
        boolean aUserFound = false;
        if(val.epost(email) && val.pass(newPassword) && val.pass(confPass)){//Validera korrekt inmatning
            if(newPassword.equals(confPass)){//skrivit nytt lösen rätt 2 gånger
                try{
                    ArrayList<HashMap<String, String>> employeeAccounts = idb.fetchRows("Select aid, epost, losenord from anstalld");
                    for(HashMap<String, String> account: employeeAccounts){
                        for(String accDataKey: account.keySet()){
                            if(email.equals(account.get("epost"))){
                                aUserFound = true;
                                accId = account.get("aid");
                            }
                        }
                    }
                    if(aUserFound){
                        idb.update("update anstalld set losenord = '" + newPassword + "' where aid = '" + accId + "'");
                        JOptionPane.showMessageDialog(null, "Password was successfully reset!");
                    }
                }catch(InfException e){
                    e.printError();
                }
            }else{
                JOptionPane.showMessageDialog(null, "New password and confirmation password does not match");
            }
        }
    }
}
