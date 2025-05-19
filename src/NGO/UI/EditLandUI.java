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
public class EditLandUI extends ContentPanelStructure {

	private InfDB idb;

	public EditLandUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		idb = user.getDb();

		try {
			ArrayList<String> avdelningar = idb.fetchColumn("select lid from land;");

			ScrollListPanel cardList = new ScrollListPanel(avdelningar
			, () -> new LandCard(20, user));
			add(cardList);

		} catch (Exception e) {
		}
	}

}
