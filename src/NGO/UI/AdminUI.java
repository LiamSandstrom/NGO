package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminUI extends JPanel {

	User user;

	public AdminUI(User user) {
		this.user = user;

		setLayout(new GridBagLayout());
		add(new JLabel("Admin panel"));
		setBackground(Color.red);
		setVisible(true);
	}
}
