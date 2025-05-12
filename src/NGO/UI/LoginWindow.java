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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

		//Login Content
		JPanel content = new JPanel();
		content.setBackground(new Color(175 , 185, 245));
		content.setLayout(new GridBagLayout());
		content.setPreferredSize(new Dimension(450, 150));
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		content.add(new JLabel("Email adress"), gbc);

		//Login Elements
		JTextField email = new JTextField();
		email.setPreferredSize(new Dimension(350, 40));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		content.add(email, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		content.add(new JLabel("Password"), gbc);
		
		JTextField password = new JTextField();
		password.setPreferredSize(new Dimension(350, 40));
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		content.add(password, gbc);

		JButton btn = new JButton("Login");
		btn.setPreferredSize(new Dimension(200, 35));
		gbc.gridx = 0;
		gbc.gridy = 4;
		
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
