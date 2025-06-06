/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author liam
 */
public abstract class CardStructure extends RoundedPanel {

	public CardStructure(int radius, User user) {
		super(radius);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 70));
		setBackground(new Color(40, 40, 40));
		setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(70, 70, 70));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(new Color(40, 40, 40));
			}
		});
	}

	public abstract void initCard(String id); // Måste implementeras i varje kortklass

}
