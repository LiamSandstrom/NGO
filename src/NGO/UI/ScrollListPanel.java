package NGO.UI;

import java.util.function.Supplier;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ScrollListPanel extends JPanel {

    public ScrollListPanel(List<String> items, Supplier<? extends CardStructure> cardFactory) {
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

        for (String item : items) {
            gbc.gridy = yIndex++;

            CardStructure card = cardFactory.get(); // Ny instans varje gång
            card.initCard(item); // Fyll med data
            panel.add(card, gbc);
        }

        add(scroll);
    }
}
