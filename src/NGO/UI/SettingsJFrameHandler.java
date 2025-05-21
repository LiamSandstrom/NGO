/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author liam
 */
public class SettingsJFrameHandler {

	private static JFrame frame = null;

	public SettingsJFrameHandler() {

	}

	public static void addPanel(JPanel panel) {
		if (frame == null) {
			createFrame();
		}
		System.out.println(frame);
		frame.add(panel);
	}

	public static void createFrame() {
		frame = new JFrame();
		frame.setSize(new Dimension(700, 600));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				frame = null; 
			}
		});

	}

	public static void removeFrame() {
		frame.dispose();
		frame = null;
	}
}
