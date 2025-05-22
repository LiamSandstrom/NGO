package NGO.UI.Cards;
import NGO.UI.CardStructure;
import NGO.UI.SettingsPanelStructure;
import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import oru.inf.InfDB;

public class ProjektCard extends CardStructure {
	
	private User user;

	public ProjektCard(int radius, User user) {
		super(radius, user);
		this.user = user;
	}

	@Override
	public void initCard(String id) {
		try {
			String name = user.getDb().fetchSingle("select projektnamn from projekt where pid = " + id);
			JLabel nameLabel = new JLabel(name);
			nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			add(nameLabel, BorderLayout.CENTER);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}