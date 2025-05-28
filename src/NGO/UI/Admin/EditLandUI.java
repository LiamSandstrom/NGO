package NGO.UI.Admin;

import NGO.UI.Cards.LandCard;
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

public class EditLandUI extends ContentPanelStructure {

	private InfDB idb;

	public EditLandUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		idb = user.getDb();
                JButton addBtn = new JButton("Add Country");
		addBtn.setPreferredSize(new Dimension(200, 43));
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 105);
		add(addBtn, gbc);

		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridy = 1;

		addBtn.addActionListener(e -> {
			SettingsJFrameHandler.addPanel(new AddLand(user, "none"));
		});

		try {
			ArrayList<String> avdelningar = idb.fetchColumn("select lid from land;");

			ScrollListPanel cardList = new ScrollListPanel(avdelningar
			, () -> new LandCard(20, user));
			add(cardList, gbc);

		} catch (Exception e) {
		}
	}

}
