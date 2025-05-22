package NGO.UI;

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
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import oru.inf.InfDB;

public class SettingsPanelFramework extends SettingsPanelStructure {

	InfDB idb;
	GridBagConstraints gbc;
	RoundedPanel contentPanel;
	HashMap<String, String> infoMap;

	public SettingsPanelFramework(User user, String id) {
		super(user, id);
		idb = user.getDb();
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridy = 0;

		contentPanel = new RoundedPanel(20);
		contentPanel.setLayout(new GridBagLayout());
		//contentPanel.setPreferredSize(new Dimension(550, 1000));
		contentPanel.setBackground(new Color(40, 40, 40));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setPreferredSize(new Dimension(550, 450)); // synlig storlek
        	scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setBorder(null);
		add(scrollPane);

		infoMap = new HashMap<String, String>();
	}

	public void setInfo() {

		for (String key : infoMap.keySet()) {

			RoundedPanel cPanel = new RoundedPanel(10);
			cPanel.setLayout(new BorderLayout());
			cPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
			cPanel.setPreferredSize(new Dimension(500, 70));

			JLabel keyLabel = new JLabel(key + ":");
			keyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			keyLabel.setHorizontalAlignment(JLabel.RIGHT);

			JLabel valueLabel = new JLabel(infoMap.get(key));
			valueLabel.setFont(new Font("Arial", Font.PLAIN, 20));

			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(10, 0, 0, 0);
			cPanel.add(keyLabel, BorderLayout.WEST);

			gbc.gridx = 1;
			gbc.anchor = GridBagConstraints.EAST;
			cPanel.add(valueLabel, BorderLayout.EAST);
			contentPanel.add(cPanel, gbc);
			gbc.gridy = gbc.gridy + 1;

			revalidate();
			repaint();
		}
	}

	public void addInfo(String titel, String info) {
		infoMap.put(titel, info);
	}
}
