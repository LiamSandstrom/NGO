package NGO.UI;

import NGO.Admin;
import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminUI extends UIStructure {

	Admin admin;

	public AdminUI(Admin admin) {
            super();
		this.admin = admin;
		changeContentPanel(new WelcomePanel(admin, this));
	}
}
