/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;

import NGO.UI.CardStructure;
import NGO.UI.ProjektPartnerUI;
import NGO.UI.SettingsJFrameHandler;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class ProjektPartnerCard extends CardStructure {

    private User user;
    private InfDB idb;

    public ProjektPartnerCard(int radie, User user) {
        super(radie, user);
        this.user = user;
        this.idb = user.getDb();
    }

    @Override
    public void initCard(String id) {
        try {
            //Projekt som jag är tilldelad
            String projNamn = idb.fetchSingle("select projekt.projektnamn from projekt"
                    + " join ans_proj on projekt.pid = ans_proj.pid join anstalld on ans_proj.aid = anstalld.aid "
                    + "where projekt.pid = '" + id + "';");
            JLabel cardRbr = new JLabel(projNamn);
            cardRbr.setFont(new Font("Arial", Font.PLAIN, 20));
            add(cardRbr, BorderLayout.CENTER);

            JButton infoBtn = new JButton("Info");
            infoBtn.setPreferredSize(new Dimension(100, 33));
            infoBtn.setFont(new Font("Arial", Font.PLAIN, 16));
            infoBtn.setBackground(new Color(63, 81, 181));
            add(infoBtn, BorderLayout.EAST);

            infoBtn.addActionListener(e -> {
                SettingsJFrameHandler.addPanel(new ProjektPartnerUI(user, id));
            });

        } catch (InfException e) {
            e.printError();
        }
    }
}
