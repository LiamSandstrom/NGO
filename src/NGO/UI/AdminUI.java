package NGO.UI;

import NGO.Admin;
import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminUI extends JPanel {

	Admin admin;

	public AdminUI(Admin admin) {
		this.admin = admin;

		setLayout(new GridBagLayout());
		add(new JLabel("Admin panel"));
		setVisible(true);
	}
}
