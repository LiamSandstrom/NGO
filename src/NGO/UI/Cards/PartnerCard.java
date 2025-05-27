package NGO.UI.Cards;
import NGO.UI.Admin.EditPartnerUI;
import NGO.UI.Admin.PartnerSettingsUI;
import NGO.UI.CardStructure;
import NGO.UI.SettingsJFrameHandler;
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

public class PartnerCard extends CardStructure {
	
	private User user;

	public PartnerCard(int radius, User user) {
		super(radius, user);
		this.user = user;
	}

	@Override
	public void initCard(String id) {
		try {
			String name = user.getDb().fetchSingle("select namn from partner where pid = " + id);
			JLabel nameLabel = new JLabel(name);
			nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			add(nameLabel, BorderLayout.CENTER);

			JButton editBtn = new JButton("Edit");
			editBtn.setPreferredSize(new Dimension(100, 33));
			editBtn.setFont(new Font("Arial", Font.PLAIN, 16));
			editBtn.setBackground(new Color(63, 81, 181));
			add(editBtn, BorderLayout.EAST);

			editBtn.addActionListener(e -> {
				SettingsJFrameHandler.addPanel(new PartnerSettingsUI(user, id));
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}