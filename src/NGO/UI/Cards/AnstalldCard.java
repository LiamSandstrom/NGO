/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;

import NGO.UI.AnstalldSettingsUI;
import NGO.UI.CardStructure;
import NGO.UI.SettingsJFrameHandler;
import NGO.UI.SettingsPanelFramework;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import oru.inf.InfDB;

/**
 *
 * @author liam
 */
public class AnstalldCard extends CardStructure {

	private User user;

	public AnstalldCard(int radius, User user) {
		super(radius, user );
		this.user = user;
	}

	@Override
	public void initCard(String id) {
		try {
			String namn = user.getDb().fetchSingle("select fornamn from anstalld where aid = " + id);
			String efternamn = user.getDb().fetchSingle("select efternamn from anstalld where aid = " + id);
			String fullName = namn + " " + efternamn;
			JLabel nameLabel = new JLabel(fullName);
			nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			add(nameLabel, BorderLayout.CENTER);

			JButton editBtn = new JButton("Edit");
			editBtn.setPreferredSize(new Dimension(100, 33));
			editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
			editBtn.setBackground(new Color(63, 81, 181));
			add(editBtn, BorderLayout.EAST);

			editBtn.addActionListener(e -> {
				SettingsJFrameHandler.addPanel(new AnstalldSettingsUI(user, id));
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
