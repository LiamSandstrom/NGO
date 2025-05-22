/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import javax.swing.JPanel;

/**
 *
 * @author liam
 */
public class AnstalldSettingsUI extends SettingsPanelFramework {
	
	public AnstalldSettingsUI(User user, String id) {
		super(user, id);

		for(int i = 0; i < 10; i++){
			addInfo("" + i, "info: " + 1);
		}

		setInfo();
	}
	
}
