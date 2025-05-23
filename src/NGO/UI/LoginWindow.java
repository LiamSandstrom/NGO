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
import NGO.UI.Handlaggare.ResetPassword;

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
		RoundedPanel content = new RoundedPanel(20);
		content.setBackground(new Color(40,40,40));
		content.setLayout(new GridBagLayout());
		//450, 230 correct size when no admin and hand button
		content.setPreferredSize(new Dimension(450, 380));
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
                
                JButton resetBtn = new JButton("Reset Password");
                btn.setFont(new Font("Arial", Font.PLAIN, textSize));
                resetBtn.setPreferredSize(new Dimension(inputWidth, 40));
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.insets = new Insets(10, 0, 0, 0);
                content.add(resetBtn, gbc);
                
                resetBtn.addActionListener(e ->{
                    
                });
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

		JButton btnAdmin = new JButton("Login Admin");
		btnAdmin.setFont(new Font("Arial", Font.PLAIN, textSize));
		btnAdmin.setPreferredSize(new Dimension(inputWidth, 40));
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(10,0,0,0);

		content.add(btnAdmin ,gbc);

		btnAdmin.addActionListener(e -> {
			loginListener.onLoginSucess("1");
		});
		
		JButton btnHand = new JButton("Login Handläggare");
		btnHand.setFont(new Font("Arial", Font.PLAIN, textSize));
		btnHand.setPreferredSize(new Dimension(inputWidth, 40));
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(10,0,0,0);

		content.add(btnHand ,gbc);

		btnHand.addActionListener(e -> {
			loginListener.onLoginSucess("10");
		});

		add(content, gbc);
	}
}
