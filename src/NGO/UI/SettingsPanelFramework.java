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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import oru.inf.InfDB;

public class SettingsPanelFramework extends SettingsPanelStructure {

	private InfDB idb;
	private GridBagConstraints gbc;
	private RoundedPanel contentPanel;
	private LinkedHashMap<String, String> infoMap;
	private static ArrayList<JTextField> textList;

	public SettingsPanelFramework(User user, String id) {
		super(user, id);
		idb = user.getDb();
		textList = new ArrayList<>();
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.gridx = 0;

		contentPanel = new RoundedPanel(20);
		contentPanel.setLayout(new GridBagLayout());
		//contentPanel.setPreferredSize(new Dimension(550, 1000));
		contentPanel.setBackground(new Color(40, 40, 40));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setPreferredSize(new Dimension(550, 450)); // synlig storlek
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setBorder(null);

		GridBagConstraints tempGbc = new GridBagConstraints();
		tempGbc.gridwidth = 3;
		tempGbc.gridx = 0;
		add(scrollPane, tempGbc);

		infoMap = new LinkedHashMap<String, String>();
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

	public void setEditInfo() {

		for (String key : infoMap.keySet()) {

			RoundedPanel cPanel = new RoundedPanel(10);
			cPanel.setLayout(new BorderLayout());
			cPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
			cPanel.setPreferredSize(new Dimension(500, 70));

			JLabel keyLabel = new JLabel(key + ":");
			keyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			keyLabel.setHorizontalAlignment(JLabel.RIGHT);

			JTextField valueLabel = new JTextField(infoMap.get(key));
			valueLabel.setPreferredSize(new Dimension(250, 60));
			valueLabel.setHorizontalAlignment(JTextField.RIGHT);
			valueLabel.setFont(new Font("Arial", Font.PLAIN, 20));

			textList.add(valueLabel);

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

	public JButton addSaveButton() {
		JButton btn = new JButton("Save");
		GridBagConstraints knappGbc = new GridBagConstraints();
		btn.setPreferredSize(new Dimension(100,60));
		btn.setFont(new Font("Arial", Font.PLAIN, 20));
		knappGbc.gridx = 1;
		knappGbc.gridy = 1; 
		knappGbc.gridwidth = 2;    
		knappGbc.anchor = GridBagConstraints.CENTER;
		knappGbc.insets = new Insets(20, 0, 10, 0);
		knappGbc.fill = GridBagConstraints.HORIZONTAL;
		add(btn,knappGbc);
		return btn;
	}

	public JButton addDeleteButton() {
		JButton btn = new JButton("Delete");
		btn.setBackground(Color.red);
		GridBagConstraints knappGbc = new GridBagConstraints();
		btn.setPreferredSize(new Dimension(100,60));
		btn.setFont(new Font("Arial", Font.PLAIN, 20));
		knappGbc.gridx = 0;
		knappGbc.gridy = 1; 
		knappGbc.gridwidth = 1;    
		knappGbc.anchor = GridBagConstraints.CENTER;
		knappGbc.insets = new Insets(20, 0, 10, 0);
		knappGbc.fill = GridBagConstraints.HORIZONTAL;
		add(btn,knappGbc);
		return btn;
	}

	public ArrayList<String> getTextInTextfields(){
		ArrayList<String> output = new ArrayList<>();

		for(JTextField field : textList){
			output.add(field.getText());
		}
		return output;
	}
}
