/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author liam
 */
public class SettingsPanelStructure extends JPanel {

	private User user;
	private String id;
	
	public SettingsPanelStructure(User user, String id){
		this.user = user;
		this.id = id;

		setPreferredSize(new Dimension(700,600));
	}
}
