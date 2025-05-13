package NGO;

import NGO.UI.AdminUI;
import NGO.UI.GenWin;
import NGO.UI.LoginWindow;
import NGO.listeners.LoginListener;
import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;
//HEJPONTUS

public class MainWindow extends JFrame implements LoginListener {

	private JPanel currentPanel;
	InfDB idb;
	User user;

	public MainWindow() {
		try {
			idb = new InfDB("SDGSweden", "3306", "dbAdmin2024", "dbAdmin2024PW");

		} catch (InfException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Failed to connect to database");
			return;
		}

		//Window
		setSize(1000, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("MainFrame");

		LoginWindow loginWindow = new LoginWindow(this, idb);
		currentPanel = loginWindow;
		add(loginWindow);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void onLoginSucess(String id) {
		user = new User(idb, id);
		remove(currentPanel);
		repaint();

		System.out.println("SUCESS");
		System.out.println("Projects(id): " + user.getProjects());
		System.out.println("Avdelning: " + user.getAvdelning());

		switch (user.getRole()) {
			case User.Role.ADMIN:
				user = new Admin(idb, id);
				//currentPanel = new AdminUI((Admin) user);
                                currentPanel = new GenWin(user);
                                currentPanel.setVisible(true);
				add(currentPanel);
				break;

			case User.Role.HANDLAGGARE:
				JPanel temp = new GenWin(user);
				temp.setVisible(true);
				break;
			default:
				System.out.println("NO ROLE");
		}
		revalidate();
		repaint();
	}

	public void removeWindow(JPanel panel) {
		remove(panel);
	}
}
