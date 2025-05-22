/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;

import NGO.UI.CardStructure;
import NGO.UI.Handlaggare.SearchHandlaggareInfoPanel;
import NGO.UI.SettingsJFrameHandler;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Pontus
 */
public class SearchHandlaggareCard extends CardStructure {
    private User user;

	public SearchHandlaggareCard(int radius, User user) {
		super(radius, user);
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
                        
                        JButton infoBtn = new JButton("Info");
			infoBtn.setPreferredSize(new Dimension(100, 33));
			infoBtn.setFont(new Font("Arial", Font.PLAIN, 16));
			infoBtn.setBackground(new Color(63, 81, 181));
			add(infoBtn, BorderLayout.EAST);

			infoBtn.addActionListener(e -> {
				SettingsJFrameHandler.addPanel(new SearchHandlaggareInfoPanel(user, id));
			});


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
