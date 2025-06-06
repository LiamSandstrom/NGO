package NGO.UI;

import NGO.UI.Admin.EditPartnerUI;
import NGO.UI.Admin.EditAnstalldUI;
import NGO.UI.Admin.EditLandUI;
import NGO.UI.Admin.EditProjectUI;
import NGO.UI.Admin.EditAvdelningUI;
import NGO.Admin;
import NGO.UI.Admin.EditGlobalGoalsUI;
import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminUI extends UIStructure {

	private Admin admin;

	public AdminUI(Admin admin) {
            super();
		this.admin = admin;
		changeContentPanel(new WelcomePanel(admin, this));

		EditAnstalldUI anstalldUI = new EditAnstalldUI(admin, this);
		addButton("Employees", anstalldUI);

		EditProjectUI projectUI = new EditProjectUI(admin, this);
		addButton("Projects", projectUI);

		EditPartnerUI partnerUI = new EditPartnerUI(admin, this);
		addButton("Partners", partnerUI);

		EditLandUI landUI = new EditLandUI(admin, this);
		addButton("Countries", landUI);

		EditAvdelningUI avdelningUI = new EditAvdelningUI(admin, this);
		addButton("Departments", avdelningUI);
		
                EditGlobalGoalsUI målUI = new EditGlobalGoalsUI(admin, this);
                addButton("Global Goals", målUI);
                
		bottomMargin();
	}
}
