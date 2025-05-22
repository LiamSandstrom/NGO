package NGO.UI.Handlaggare;

import NGO.UI.SettingsPanelStructure;
import NGO.User;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import oru.inf.InfDB;
import oru.inf.InfException;

public class GlobalGoalsInfoPanel extends SettingsPanelStructure {

    private InfDB idb;
    private User user;

    public GlobalGoalsInfoPanel(User user, String id) {
        super(user, id);
        setLayout(new GridBagLayout());
        idb = user.getDb();
        add(new JLabel(id));
        setBackground(Color.red);
        //Här skrivs allt som lägg till för varje fönster
        JTextArea content = new JTextArea();
        add(content);
        loadGoals("Select * from hallbarhetsmal", content);
        revalidate();
        repaint();
    }

    private void loadGoals(String mysqlQuestion, JTextArea goals) {
        try {
            ArrayList<HashMap<String, String>> allGoals = idb.fetchRows(mysqlQuestion);
            StringBuilder results = new StringBuilder();
            HashMap<String, String> rad = allGoals.get(0);
            results.append("Global Goals ID: ").append(rad.get("hid"))
                    .append("\nName: ").append(rad.get("namn"))
                    .append("\nGoal number: ").append(rad.get("malnummer"))
                    .append("\nDescription: ").append(rad.get("beskrivning"))
                    .append("\nPriority: ").append(rad.get("prioritet"))
                    .append("\n\n");

            goals.setText(results.toString());
        } catch (InfException ex) {
            ex.printStackTrace();
        }
    }
}
