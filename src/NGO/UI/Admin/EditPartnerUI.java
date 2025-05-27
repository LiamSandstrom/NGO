/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Admin;

import NGO.UI.Cards.PartnerCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
import NGO.UI.SettingsJFrameHandler;
import NGO.UI.UIStructure;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author liam
 */
public class EditPartnerUI extends ContentPanelStructure {

	private InfDB idb;

	public EditPartnerUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		idb = user.getDb();

		JButton addBtn = new JButton("Add Partner");
		addBtn.setPreferredSize(new Dimension(200, 43));
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 105);
		add(addBtn, gbc);

		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 1;

		addBtn.addActionListener(e -> {
			SettingsJFrameHandler.addPanel(new AddPartner(user, "none"));
		});

		try {
			ArrayList<String> avdelningar = idb.fetchColumn("select pid from partner;");

			ScrollListPanel cardList = new ScrollListPanel(avdelningar,
			() -> new PartnerCard(20, user));
			add(cardList, gbc);

		} catch (Exception e) {
		}
	}

}
