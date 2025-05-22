/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI.Admin;

import NGO.UI.Cards.AvdelningCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
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
public class EditAvdelningUI extends ContentPanelStructure {

	private InfDB idb;

	public EditAvdelningUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		idb = user.getDb();

		try {
			ArrayList<String> workers = idb.fetchColumn("select avdid from avdelning;");

			ScrollListPanel cardList = new ScrollListPanel(
				workers,
				() -> new AvdelningCard(20, user));
			add(cardList);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
