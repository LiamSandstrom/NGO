package NGO.UI.Handlaggare;

import NGO.UI.Cards.GlobalCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
import NGO.UI.UIStructure;
import NGO.User;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import oru.inf.InfDB;
import oru.inf.InfException;

public class GlobalGoals extends ContentPanelStructure {

    private InfDB idb;
    private User user;
    private ScrollListPanel cardList;
     GridBagConstraints gbc;
    

    public GlobalGoals(User user, UIStructure parentPanel) {
        super(user, parentPanel);
        this.user = user;
        cardList = null;
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        idb = user.getDb();

        JButton filterButton = new JButton("Filter by ▼");
        JPopupMenu filterMenu = new JPopupMenu();
        JMenuItem filterID = new JMenuItem("ID");
        JMenuItem filterName = new JMenuItem("Name");
        JMenuItem filterPrioritet = new JMenuItem("Priority");

        filterID.addActionListener(e -> {
            createList("select * from hallbarhetsmal order by hid");
        });
        filterName.addActionListener(e -> {
            createList("select * from hallbarhetsmal order by namn");
        });
        filterPrioritet.addActionListener(e -> {
            createList("select * from hallbarhetsmal order by field(prioritet, 'Hög', 'Medel', 'Låg')");
        });
        filterButton.addActionListener(e -> {
            filterMenu.show(filterButton, 0, filterButton.getHeight());
        });

        add(filterButton);
        gbc.gridy = 1;
        filterMenu.add(filterID);
        filterMenu.add(filterName);
        filterMenu.add(filterPrioritet);
        createList("select * from hallbarhetsmal;");

        

    }

    

    public void createList(String query) {
        if(cardList!=null){
            remove(cardList);
            cardList = null;
        }
        try {
            
            ArrayList<String> goals = idb.fetchColumn(query);

             cardList = new ScrollListPanel(
                    goals,
                    () -> new GlobalCard(20, user));
            add(cardList,gbc);
            revalidate();
            repaint();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
