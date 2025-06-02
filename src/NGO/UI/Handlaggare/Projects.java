package NGO.UI.Handlaggare;

import NGO.UI.Cards.ProjectHandlaggareCard;
import NGO.UI.ContentPanelStructure;
import NGO.UI.ScrollListPanel;
import NGO.UI.UIStructure;
import NGO.User;
import NGO.Validate;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import oru.inf.InfDB;
import oru.inf.InfException;

public class Projects extends ContentPanelStructure {

	private String id;
	private InfDB idb;
	private User user;
	private ScrollListPanel cardList;
	private GridBagConstraints gbc;
	private JComboBox<String> combo, addOrRemove;
	private JTextField searchFieldFrom, searchFieldTo, alterPerson, alterToField;
	private Validate val;
	private String[] alternatives = {"Unspecified", "Pågående", "Planerat", "Avslutat"};
	private String[] alts = {"add", "remove"};
	private ArrayList<String> chefId;
	private boolean kontroll;
	private JLabel feedbackLabel;

	public Projects(User user, UIStructure parentPanel) {
		super(user, parentPanel);
		this.user = user;
		this.id = user.getId();
		this.idb = user.getDb();
		this.val = new Validate(user);
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.weightx = 0;

		try {
			chefId = idb.fetchColumn("select projektchef from projekt");
			kontroll = chefId.contains(id);
		} catch (InfException e) {
			JOptionPane.showMessageDialog(null, "Kunde inte hämta projektchefer: " + e.getMessage());
		}

		addSearchComponents();
		if (kontroll) {
			addProjectManagerControls();
		}

		createList(getQuery(false));

	}

	private void addSearchComponents() {

		gbc.gridx = 0;
		gbc.gridy = 0;
		JPanel spacer = new JPanel();
		spacer.setPreferredSize(new Dimension(20, 1));
		spacer.setOpaque(false);
		add(spacer, gbc);

		JButton allProjBtn = new JButton("Section projects");
		allProjBtn.addActionListener(e -> createList(getQuery(false)));
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(allProjBtn, gbc);

		combo = new JComboBox<>(alternatives);
		gbc.gridx = 2;
		gbc.gridy = 0;
		add(combo, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		add(new JLabel("Date from:"), gbc);
		searchFieldFrom = new JTextField("Date", 10);
		removeTextOnClick(searchFieldFrom);
		gbc.gridx = 4;
		gbc.gridy = 0;
		add(searchFieldFrom, gbc);

		gbc.gridx = 5;
		gbc.gridy = 0;
		add(new JLabel("To date:"), gbc);

		searchFieldTo = new JTextField("Date", 10);
		removeTextOnClick(searchFieldTo);
		gbc.gridx = 6;
		gbc.gridy = 0;
		add(searchFieldTo, gbc);

		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(e -> createList(getQuery(true)));
		gbc.gridx = 7;
		gbc.gridy = 0;
		add(searchBtn, gbc);

		gbc.gridx = 8;
		gbc.gridy = 0;
		JPanel spacer3 = new JPanel();
		spacer3.setPreferredSize(new Dimension(20, 1));
		spacer3.setOpaque(false);
		add(spacer3, gbc);
	}

	private void addProjectManagerControls() {
		gbc.gridy = 1;

		gbc.gridx = 0;
		JPanel spacer2 = new JPanel();
		spacer2.setPreferredSize(new Dimension(20, 1));
		spacer2.setOpaque(false);
		add(spacer2, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		addOrRemove = new JComboBox<>(alts);
		add(addOrRemove, gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		add(new JLabel("person:"), gbc);

		alterPerson = new JTextField(10);
		gbc.gridx = 4;
		gbc.gridy = 1;
		add(alterPerson, gbc);

		gbc.gridx = 5;
		gbc.gridy = 1;
		add(new JLabel("For project:"), gbc);

		alterToField = new JTextField(10);
		gbc.gridx = 6;
		gbc.gridy = 1;
		add(alterToField, gbc);

		JButton executeBtn = new JButton("Execute");
		executeBtn.addActionListener(e -> chosenAction());
		gbc.gridx = 7;
		gbc.gridy = 1;
		add(executeBtn, gbc);

		feedbackLabel = new JLabel();
		gbc.gridx = 8;
		gbc.gridy = 1;
		add(feedbackLabel, gbc);
	}

	private void removeTextOnClick(JTextField field) {
		field.addMouseListener(new java.awt.event.MouseAdapter() {
			boolean cleared = false;

			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (!cleared) {
					field.setText("");
					cleared = true;
				}
			}
		});
	}

	private String getStatusCondition() {
		String status = (String) combo.getSelectedItem();
		return status.equals("Unspecified") ? "" : " and status = '" + status + "'";
	}

	private String getQuery(boolean filtered) {
		String sql = "";
		try {
			String avdelningId = idb.fetchSingle("select avdelning from anstalld where aid = '" + id + "'");

			if (filtered) {
				String from = searchFieldFrom.getText().trim();
				String to = searchFieldTo.getText().trim();

				if ((from.equals("Date") && to.equals("Date")) || (from.isEmpty() && to.isEmpty())) {
					sql = "select pid from projekt where pid in (select pid from ans_proj join anstalld using (aid) where avdelning = " + avdelningId + ")" + getStatusCondition();
				} else if (val.date(from) && val.date(to)) {
					sql = "select pid from projekt where pid in (select pid from ans_proj join anstalld using (aid) where avdelning = '" + avdelningId + "')"
						+ getStatusCondition() + " and startdatum >= '" + from + "' and slutdatum <= '" + to + "'";
				}
			} else {
				sql = "select pid from projekt where pid in (select pid from ans_proj join anstalld using (aid) where avdelning = " + avdelningId + ")";
			}
		} catch (InfException e) {
			JOptionPane.showMessageDialog(null, "Database error.");
			e.printStackTrace();
		}
		return sql;
	}

	public void createList(String query) {
		if (cardList != null) {
			remove(cardList);
			cardList = null;
		}
		try {
			ArrayList<String> projects = idb.fetchColumn(query);
			System.out.println("Query: " + query);
			System.out.println("Projects: " + projects);
			cardList = new ScrollListPanel(projects, () -> new ProjectHandlaggareCard(20, user));
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 8;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			add(cardList, gbc);
			revalidate();
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private void chosenAction() {
        String aid = alterPerson.getText();
        String projName = alterToField.getText();
        String pid = "";

        if (val.id(aid) && val.projectName(projName)) {
            //Sätta värde på string pid
            try {
                pid = idb.fetchSingle("select pid from projekt where projektnamn = '" + projName + "'");
            } catch (InfException ex) {
                ex.printError();
            }
            if (val.idExistsExcludeMessage(pid, "pid", "projekt")) {
                if (val.isInMyAuthority(pid)) {
                    if (((String) addOrRemove.getSelectedItem()).equals("add")) {
                        addPerson(aid, pid);
                    } else {
                        removePerson(aid, pid);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "There is no projet by that name");
            }
        }
    }

	private void addPerson(String aid, String pid) {
		try {
			idb.insert("insert into ans_proj values('" + pid + "', '" + aid + "')");
			String namn = idb.fetchSingle("select fornamn from anstalld where aid = '" + aid + "';");
			String projnamn = idb.fetchSingle("select projektnamn from projekt where pid = '" + pid + "';");
			//feedbackLabel.setText(namn + " har lagts till i " + projnamn);
			JOptionPane.showMessageDialog(null, namn + " har lagts till i " + projnamn);
		} catch (InfException e) {
			//feedbackLabel.setText("Kunde inte lägga till " + aid + " i projekt " + pid);
			JOptionPane.showMessageDialog(null,"Kunde inte lägga till " + aid + " i projekt " + pid);
		}
	}

	private void removePerson(String aid, String pid) {
		try {
			idb.delete("delete from ans_proj where aid = '" + aid + "' and pid = '" + pid + "';");
			String namn = idb.fetchSingle("select fornamn from anstalld where aid = '" + aid + "';");
			String projnamn = idb.fetchSingle("select projektnamn from projekt where pid = '" + pid + "';");
			//feedbackLabel.setText(namn + " har tagits bort från " + projnamn);
			JOptionPane.showMessageDialog(null, namn + " har tagits brot ifrån " + projnamn);
		} catch (InfException e) {
			//feedbackLabel.setText("Kunde inte ta bort " + aid + " från projekt " + pid);
			JOptionPane.showMessageDialog(null,"Kunde inte ta bort " + aid + " ifrån projekt " + pid);
		}
	}
}
