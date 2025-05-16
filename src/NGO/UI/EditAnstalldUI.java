/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import oru.inf.InfDB;

/**
 *
 * @author liam
 */
public class EditAnstalldUI extends ContentPanelStructure {

	InfDB idb;

	public EditAnstalldUI(User user, UIStructure parentPanel) {
		super(user, parentPanel);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		idb = user.getDb();

		try {
			ArrayList<String> workers = idb.fetchColumn("select aid from anstalld;");
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			JScrollPane scroll = new JScrollPane(panel);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.getVerticalScrollBar().setUnitIncrement(20);
			scroll.setPreferredSize(new Dimension(500, 600));
			scroll.setBorder(null);

			add(scroll);

			GridBagConstraints workerGbc = new GridBagConstraints();
			int yIndex = 0;
			for (String id : workers) {
				RoundedPanel workerPanel = new RoundedPanel(20);
				workerPanel.setLayout(new BorderLayout());
				workerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
				workerPanel.setPreferredSize(new Dimension(400, 70));
				Color workerPanelColor = new Color(40,40,40);
				workerPanel.setBackground(workerPanelColor);

				String name = idb.fetchSingle("select fornamn from anstalld where aid = " + id + ";");
				String lastName = idb.fetchSingle("select efternamn from anstalld where aid = " + id + ";");
				JLabel label = new JLabel(name + " " + lastName);
				label.setFont(new Font("Arial", Font.PLAIN, 20));

				workerGbc.gridy = yIndex;
				workerGbc.insets = new Insets(10, 0, 10, 0);
				yIndex++;

				workerPanel.add(label, BorderLayout.CENTER);

				JButton editBtn = new JButton("Edit");
				editBtn.setPreferredSize(new Dimension(100, 33));
				editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
				editBtn.setBackground(new Color(63, 81, 181));
				workerPanel.add(editBtn, BorderLayout.EAST);

				panel.add(workerPanel, workerGbc);
				System.out.println(name);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
