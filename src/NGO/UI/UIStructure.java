/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author david
 */
public class UIStructure extends JPanel {

	private JPanel contentPanel;
	private JButton currentButton;
	private JPanel toolbarPanel;
	private GridBagConstraints gbc;
	private int index = 0;
	private Color defaultbtnColor;  

	public UIStructure() {
		this.setLayout(new BorderLayout());
		contentPanel = new JPanel();
		toolbarPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(800, 700));
		toolbarPanel.setPreferredSize(new Dimension(200, 700));
		defaultbtnColor = new Color(70, 70, 70);
		toolbarPanel.setLayout(new GridBagLayout());
		add(toolbarPanel, BorderLayout.WEST);
                add(contentPanel, BorderLayout.EAST);
		contentPanel.setBackground(Color.gray);
		toolbarPanel.setBackground(new Color(40,40,40));
		gbc = new GridBagConstraints();
	}

	public void addButton(String btnText, JPanel panelOnClick) {
		JButton b = new JButton(btnText);
		b.setPreferredSize(new Dimension(150, 40));
		gbc.gridy = index;
		gbc.gridx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		b.addActionListener(e -> {
			changeContentPanel(panelOnClick);
			changeOutlineButton(b);
		});
		toolbarPanel.add(b, gbc);
		index++;
		b.setVisible(true);
	}

	public void bottomMargin() {
		gbc.weighty = 1;
		gbc.gridy = index;
		toolbarPanel.add(Box.createVerticalGlue(), gbc);
	}

	public void changeContentPanel(JPanel newPanel) {
		remove(contentPanel);
		contentPanel = newPanel;
		add(contentPanel, BorderLayout.EAST);
		revalidate();
		repaint();
	}

	public void changeOutlineButton(JButton btn){
		System.out.println("in");
		if(currentButton != null){
			currentButton.setBackground(defaultbtnColor);
			System.out.println("change");
		}
		currentButton = btn;
		btn.setBackground(new Color(200,50,0));
	}

	public void addDirectLinkButton(String btnText, JPanel newPanel){
		
		JButton b = new JButton(btnText);
		b.setPreferredSize(new Dimension(150, 40));
		gbc.gridy = index;
		gbc.gridx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		b.addActionListener(e -> {
			SettingsJFrameHandler.addPanel(newPanel);
		});
		toolbarPanel.add(b, gbc);
		index++;
		b.setVisible(true);
	}
}
