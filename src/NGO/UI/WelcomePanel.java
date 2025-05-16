/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Font;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.Timer;
import oru.inf.InfException;

/**
 *
 * @author liam
 */
public class WelcomePanel extends ContentPanelStructure {

	int animTracker;

	public WelcomePanel(User user, UIStructure parentPanel) {
		super(user, parentPanel);

                try{
                    JLabel txt = new JLabel("Welcome " + user.getName());
		txt.setFont(new Font("Arial", Font.PLAIN, 40));
		add(txt);
		animateText(txt);
                }catch(InfException e){
                    System.out.println("Nah bruh");
                }
	}

	public void animateText(JLabel label) {
		String text = label.getText();

		animTracker = -15;

		Timer timer = new Timer(20, e -> {

			String letters[] = text.split("");
			String result = "";
			for (int i = 0; i < letters.length; i++) {
				if (animTracker < i) {
					result += randomLetter();
				} else {
					result += letters[i];
				}
			}
			animTracker++;
			label.setText(result);
			if (animTracker >= letters.length) {
				((Timer) e.getSource()).stop();
			}
		});
		timer.start();
	}

	private String randomLetter() {

		String[] c = {
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"å", "ä", "ö"
		};

		String [] n ={"1","2","3","4","5","6","7","8","9","0"};

		Random random = new Random();
		return n[random.nextInt(n.length)];
	}
}
