/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Cards;
import NGO.UI.CardStructure;
import NGO.UI.DepartmentSettingsUI;
import NGO.UI.SettingsJFrameHandler;
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
public class AvdelningCard extends CardStructure {
	
	private User user;

	public AvdelningCard(int radius, User user) {
		super(radius, user);
		this.user = user;
	}

	@Override
	public void initCard(String id) {
		try {
			String name = user.getDb().fetchSingle("select namn from avdelning where avdid = " + id);
			JLabel nameLabel = new JLabel(name);
			nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
			add(nameLabel, BorderLayout.CENTER);
                        
                        JButton editBtn = new JButton("Edit");
			editBtn.setPreferredSize(new Dimension(100, 33));
			editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
			editBtn.setBackground(new Color(63, 81, 181));
			add(editBtn, BorderLayout.EAST);

			editBtn.addActionListener(e -> {
				SettingsJFrameHandler.addPanel(new DepartmentSettingsUI(user, id));
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
