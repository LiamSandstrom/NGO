/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

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
public class EditAvdelningUI extends ContentPanelStructure {

	private InfDB idb;

	public EditAvdelningUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		idb = user.getDb();

		try {
			ArrayList<String> avdelningar = idb.fetchColumn("select avdid from avdelning;");

			Card<String> card = id -> {

				RoundedPanel panel = new RoundedPanel(20);
				panel.setLayout(new BorderLayout());
				panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
				panel.setPreferredSize(new Dimension(600, 70));
				panel.setBackground(new Color(40, 40, 40));

				try {
					String name = idb.fetchSingle("select namn from avdelning where avdid = " + id + ";");

					JLabel nameLabel = new JLabel(name);
					nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
					panel.add(nameLabel, BorderLayout.CENTER);

					JButton editBtn = new JButton("Edit");
					editBtn.setPreferredSize(new Dimension(100, 33));
					editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
					editBtn.setBackground(new Color(63, 81, 181));
					panel.add(editBtn, BorderLayout.EAST);

				} catch (InfException ex) {
					System.out.println(ex);
				}

				return panel;
			};

			JLabel header = new JLabel("Departments");
			header.setFont(new Font("Arial", Font.PLAIN, 30));
			add(header, gbc);
			gbc.insets = new Insets(10, 0, 10, 0);
			gbc.gridy = 1;
			ScrollListPanel<String> cardList = new ScrollListPanel(avdelningar, card);
			add(cardList, gbc);

		} catch (Exception e) {
		}
	}

}
