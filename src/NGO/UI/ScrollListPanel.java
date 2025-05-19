/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import java.util.List;
import javax.swing.JPanel;
import NGO.UI.Card;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JScrollPane;

/**
 *
 * @author liam
 */
public class ScrollListPanel<T> extends JPanel {

	public ScrollListPanel(List<T> items, Card<T> cards) {
		setLayout(new GridBagLayout());
		JPanel panel = new JPanel(new GridBagLayout());

		JScrollPane scroll = new JScrollPane(panel);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		scroll.setPreferredSize(new Dimension(700, 600));
		scroll.setBorder(null);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.gridx = 0;

		int yIndex = 0;

		for(T item : items){
			gbc.gridy = yIndex;
			yIndex++;
			JPanel card = cards.createCard(item);
			panel.add(card, gbc);
		}

		add(scroll);
	}

}
