/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Admin;

import NGO.UI.Cards.ProjektCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
import NGO.UI.SettingsJFrameHandler;
import NGO.UI.UIStructure;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author liam
 */
public class EditProjectUI extends ContentPanelStructure {

	private InfDB idb;

	public EditProjectUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		idb = user.getDb();

		JButton addBtn = new JButton("Add Project");
		addBtn.setPreferredSize(new Dimension(200, 43));
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 105);
		add(addBtn, gbc);

		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 1;

		addBtn.addActionListener(e -> {
			SettingsJFrameHandler.addPanel(new addProject(user, "none"));
		});

		try {
			ArrayList<String> projects = idb.fetchColumn("select pid from projekt;");

			ScrollListPanel cardList = new ScrollListPanel(projects,
				() -> new ProjektCard(20, user));
			add(cardList, gbc);

		} catch (Exception e) {

		}

	}
}
