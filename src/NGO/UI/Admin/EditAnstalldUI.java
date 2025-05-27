/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Admin;

import NGO.UI.Cards.AnstalldCard;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class EditAnstalldUI extends ContentPanelStructure {

	private InfDB idb;

	public EditAnstalldUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JButton addBtn = new JButton("Add Employee");
		addBtn.setPreferredSize(new Dimension(200, 43));
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 105);
		add(addBtn, gbc);

		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 1;

		addBtn.addActionListener(e -> {
			SettingsJFrameHandler.addPanel(new AddAnstalld(user, "none"));
		});

		idb = user.getDb();

		try {
			ArrayList<String> workers = idb.fetchColumn("select aid from anstalld;");

			ScrollListPanel cardList = new ScrollListPanel(
				workers,
				() -> new AnstalldCard(20, user));
			add(cardList, gbc);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
