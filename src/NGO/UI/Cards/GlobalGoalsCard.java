/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;

import NGO.UI.CardStructure;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author liam
 */
public class GlobalGoalsCard extends CardStructure {
	User user;

	public GlobalGoalsCard(int radius, User user) {
		super(radius, user);
		this.user = user;
	}

	@Override
	public void initCard(String id) {
		try {
			GridBagConstraints gbc = new GridBagConstraints();
			JPanel panel = new JPanel(new GridBagLayout());
			panel.setBackground(null);
			panel.setPreferredSize(new Dimension( 200,400));
			String name = user.getDb().fetchSingle("select namn from hallbarhetsmal where hid = " + id + ";");
			JLabel nameLabel = new JLabel(name);
			nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			panel.add(nameLabel, gbc);
			gbc.gridy = 1;
			String goalNumber = user.getDb().fetchSingle("select malnummer from hallbarhetsmal where hid = " + id + ";"); 
			panel.add(new JLabel(goalNumber),gbc);
			add(panel);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
