package NGO;

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
import javax.swing.JPanel;
import javax.swing.JTextField;
import oru.inf.InfDB;

public class MainWindow extends JFrame implements LoginListener {

	private JPanel currentPanel;
	InfDB idb;
	String userId;

	public MainWindow(InfDB idb) {
		this.idb = idb;

		//Window
		setSize(600, 400);
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
		userId = id;
		remove(currentPanel);
		repaint();

		System.out.println("SUCESS");

		getProjects();
		revalidate();
		repaint();
	}

	private void getProjects() {
		try {
			ArrayList<String> projects = idb.fetchColumn("select pid from ans_proj where aid = " + userId);
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridy = 0;
			panel.add(new JLabel("Project id"));
			int i = 1;

			for (String project : projects) {
				gbc.gridy = i;
				panel.add(new JLabel(project), gbc);
			}
			add(panel);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void removeWindow(JPanel panel) {
		remove(panel);
	}
}
