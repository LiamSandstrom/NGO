package NGO.UI.Cards;
import NGO.UI.CardStructure;
import NGO.UI.Handlaggare.GlobalGoalsInfoPanel;
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

/**
 *
 * @author liam
 */
public class GlobalCard extends CardStructure {
	
	private User user;

	public GlobalCard(int radius, User user) {
		super(radius, user);
		this.user = user;
	}

	@Override
	public void initCard(String id) {
		try {
			String name = user.getDb().fetchSingle("select namn from hallbarhetsmal where hid = " + id + ";");
			JLabel nameLabel = new JLabel(name);
			nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			add(nameLabel, BorderLayout.CENTER);
                        
                        JButton infoBtn = new JButton("Info");
			infoBtn.setPreferredSize(new Dimension(100, 33));
			infoBtn.setFont(new Font("Arial", Font.PLAIN, 16));
			infoBtn.setBackground(new Color(63, 81, 181));
			add(infoBtn, BorderLayout.EAST);

			infoBtn.addActionListener(e -> {
				SettingsJFrameHandler.addPanel(new GlobalGoalsInfoPanel(user, id));
			});


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
