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
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import oru.inf.InfDB;

public class SettingsPanelFramework extends SettingsPanelStructure {

	private InfDB idb;
	private GridBagConstraints gbc;
	private RoundedPanel contentPanel;
	private LinkedHashMap<String, String> infoMap;
	private  ArrayList<JTextField> textList;

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

	public void setInfo2() {

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

	public void setInfo() {

		for (String key : infoMap.keySet()) {

			RoundedPanel cPanel = new RoundedPanel(10);
			cPanel.setLayout(new BorderLayout());
			cPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

			JLabel keyLabel = new JLabel(key + ":");
			keyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			keyLabel.setHorizontalAlignment(JLabel.RIGHT);

			//TextArea för att supporta dynamic size
			JTextArea valueLabel = new JTextArea(infoMap.get(key));
			valueLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			valueLabel.setLineWrap(true);
			valueLabel.setWrapStyleWord(true);
			valueLabel.setEditable(false);
			valueLabel.setOpaque(false);
			valueLabel.setHighlighter(null);
			valueLabel.setCaretColor(new Color(0, 0, 0, 0));

			//Dynamic size
			valueLabel.setSize(new Dimension(250, Short.MAX_VALUE));
			int preferredHeight = valueLabel.getPreferredSize().height;
			cPanel.setPreferredSize(new Dimension(500, Math.max(70, preferredHeight + 20)));

			//Behöver vara i panel för centrering
			JPanel valueWrapper = new JPanel(new GridBagLayout());
			valueWrapper.setOpaque(false);
			GridBagConstraints valueGbc = new GridBagConstraints();
			valueGbc.anchor = GridBagConstraints.CENTER;
			valueWrapper.add(valueLabel, valueGbc);

			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(10, 0, 0, 0);
			cPanel.add(keyLabel, BorderLayout.WEST);

			gbc.gridx = 0;
			gbc.weightx = 6;
			gbc.anchor = GridBagConstraints.EAST;
			cPanel.add(valueWrapper, BorderLayout.EAST);

			contentPanel.add(cPanel, gbc);
			gbc.gridy = gbc.gridy + 1;

			revalidate();
			repaint();
		}
	}

	public void addLinkEditInfo(String titel, HashMap<String, String> alternativ, Function<String, SettingsPanelStructure> panelCreator, String förvaltNamn) {

		RoundedPanel cPanel = new RoundedPanel(10);
		cPanel.setLayout(new BorderLayout());
		cPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

		JComboBox<String> comboBox = new JComboBox<>(alternativ.values().toArray(new String[0]));
		comboBox.setPreferredSize(new Dimension(250, 50));
		comboBox.setFont(new Font("Arial", Font.PLAIN, 14));

		int preferredHeight = comboBox.getPreferredSize().height;
		cPanel.setPreferredSize(new Dimension(500, Math.max(70, preferredHeight + 20)));

		JButton btn = new JButton(titel + ":");
		btn.setFont(new Font("Arial", Font.PLAIN, 20));
		btn.setHorizontalAlignment(JLabel.RIGHT);

		cPanel.add(btn, BorderLayout.WEST);
		cPanel.add(comboBox, BorderLayout.EAST);

		contentPanel.add(cPanel, gbc);
		gbc.gridy++;

		revalidate();
		repaint();

		String valtId = "";
		String comboNamn = (String) comboBox.getSelectedItem();
		for (String i : alternativ.keySet()) {
			if (alternativ.get(i).equals(comboNamn)) {
				valtId = i;
				textList.add(new JTextField(valtId));
				break;
			}
		}

		comboBox.setSelectedItem(förvaltNamn);
		int listIndex = textList.size();

		btn.addActionListener(e -> {
			String comboId = "";
			String valtNamn = (String) comboBox.getSelectedItem();

			// Sök i alternativ (där key = id, value = namn)
			for (String id : alternativ.keySet()) {
				if (alternativ.get(id).equals(valtNamn)) {
					comboId = id;
					Settings2JFrameHandler.createFrame(panelCreator.apply(id));
					break;
				}
			}


		});

		comboBox.addActionListener(e -> {
			String valtNamn = (String) comboBox.getSelectedItem();
			for (String id : alternativ.keySet()) {
				if (alternativ.get(id).equals(valtNamn)) {
					textList.get(listIndex - 1).setText(id);
					break;
				}
			}
		});

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
			valueLabel.setHorizontalAlignment(JTextField.LEFT);
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
		btn.setPreferredSize(new Dimension(100, 60));
		btn.setFont(new Font("Arial", Font.PLAIN, 20));
		knappGbc.gridx = 2;
		knappGbc.gridy = 1;
		knappGbc.gridwidth = 1;
		knappGbc.anchor = GridBagConstraints.CENTER;
		knappGbc.fill = GridBagConstraints.HORIZONTAL;
		knappGbc.insets = new Insets(20, 0, 10, 0);
		add(btn, knappGbc);
		return btn;
	}

	public JButton addSaveButtonAnstalld() {
		JButton btn = new JButton("Save");
		GridBagConstraints knappGbc = new GridBagConstraints();
		btn.setPreferredSize(new Dimension(100, 60));
		btn.setFont(new Font("Arial", Font.PLAIN, 20));
		knappGbc.gridx = 1;
		knappGbc.gridy = 1;
		knappGbc.gridwidth = 1;
		knappGbc.anchor = GridBagConstraints.CENTER;
		knappGbc.fill = GridBagConstraints.HORIZONTAL;
		knappGbc.insets = new Insets(20, 0, 10, 0);
		add(btn, knappGbc);
		return btn;
	}

	public JButton addDeleteButton() {
		JButton btn = new JButton("Delete");
		btn.setBackground(Color.red);
		GridBagConstraints knappGbc = new GridBagConstraints();
		btn.setPreferredSize(new Dimension(100, 60));
		btn.setFont(new Font("Arial", Font.PLAIN, 20));
		knappGbc.gridx = 0;
		knappGbc.gridy = 1;
		knappGbc.gridwidth = 1;
		knappGbc.anchor = GridBagConstraints.CENTER;
		knappGbc.insets = new Insets(20, 0, 10, 0);
		add(btn, knappGbc);
		return btn;
	}
        
        public JTextField textField(){
            JTextField field = new JTextField();
            field.setPreferredSize(new Dimension(250, 60));
            field.setHorizontalAlignment(JTextField.LEFT);
            field.setFont(new Font("Arial", Font.PLAIN, 20));
            GridBagConstraints knappGbc = new GridBagConstraints();
            knappGbc.gridx = 1;
            knappGbc.gridy = 1;
            knappGbc.anchor = GridBagConstraints.EAST;
            //knappGbc.insets = new Insets(20, 0, 10, 0);
            add(field, knappGbc);
            
        }

	public JButton addPasswordButton() {
		JButton btn = new JButton("Random Password");
		GridBagConstraints knappGbc = new GridBagConstraints();
		btn.setPreferredSize(new Dimension(100, 60));
		btn.setFont(new Font("Arial", Font.PLAIN, 20));
		knappGbc.gridx = 2;
		knappGbc.gridy = 1;
		knappGbc.gridwidth = 1;
		knappGbc.anchor = GridBagConstraints.CENTER;
		knappGbc.fill = GridBagConstraints.HORIZONTAL;
		knappGbc.insets = new Insets(20, 0, 10, 0);
		add(btn, knappGbc);
		return btn;
	}

	public ArrayList<String> getTextInTextfields() {
		ArrayList<String> output = new ArrayList<>();

		for (JTextField field : textList) {
			output.add(field.getText());
		}
		return output;
	}

	public JTextField getTextField(int i) {
		return textList.get(i);
	}
}
