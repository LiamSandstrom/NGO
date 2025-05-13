/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.listeners.LoginListener;
import com.mysql.cj.util.StringUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author liam
 */
public class LoginWindow extends JPanel {

	private LoginListener loginListener;

	public LoginWindow(LoginListener listener, InfDB idb) {

		loginListener = listener;

		//Main container
		setLayout(new GridBagLayout());
		int textSize  = 16;
		int inputWidth = 350;
		int inputHeight = 40;

		//Login Content
		JPanel content = new JPanel();
		content.setBackground(new Color(40,40,40));
		content.setLayout(new GridBagLayout());
		content.setPreferredSize(new Dimension(450, 230));
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel emailLabel = new JLabel("Email adress");
		emailLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
		content.add(emailLabel, gbc);

		//Login Elements
		JTextField email = new JTextField();
		email.setPreferredSize(new Dimension(inputWidth, inputHeight));
		email.setMinimumSize(new Dimension(inputWidth, inputHeight));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		content.add(email, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(20,0,0,0);
		gbc.anchor = GridBagConstraints.WEST;
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, textSize));
		content.add(passwordLabel, gbc);

		JTextField password = new JTextField();
		password.setPreferredSize(new Dimension(inputWidth, inputHeight));
		password.setMinimumSize(new Dimension(inputWidth, inputHeight));
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0,0,0,0);
		gbc.anchor = GridBagConstraints.CENTER;
		content.add(password, gbc);

		JButton btn = new JButton("Login");
		btn.setFont(new Font("Arial", Font.PLAIN, textSize));
		btn.setPreferredSize(new Dimension(inputWidth, 40));
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(10,0,0,0);

		content.add(btn, gbc);

		//Login button event
		btn.addActionListener(e -> {
			if (email.getText().isEmpty()) {
				System.out.println("Input a email plz");
				return;
			}
			if (password.getText().isEmpty()) {
				System.out.println("Input a password plz");
				return;
			}
			try {
				String emailInput = email.getText();
				String emailID = idb.fetchSingle("select aid from anstalld where epost = '" + emailInput + "';");

				if (emailID == null) {
					System.out.println("Invalid epost");
					return;
				}

				String passwordInput = password.getText();
				String passwordResult = idb.fetchSingle("select losenord from anstalld where epost = '" + emailInput + "';");

				if (passwordResult == null || !passwordResult.equals(passwordInput)) {
					System.out.println("wrong");
					return;
				}
				loginListener.onLoginSucess(emailID);

			} catch (InfException ex) {
				System.out.println(ex);
			}
		});

		add(content, gbc);
	}
}
