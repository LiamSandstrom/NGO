package NGO.UI;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Settings2JFrameHandler {

	public static void createFrame(JPanel panel) {
		JFrame frame2 = new JFrame();
		frame2.setSize(new Dimension(700, 600));
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame2.setResizable(false);
		frame2.setVisible(true);
		frame2.add(panel);

	}
}
