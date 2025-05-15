package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ContentPanelStructure extends JPanel {

	public ContentPanelStructure(User user, UIStructure parentPanel){
	
		setPreferredSize(new Dimension(800, 700));
		setBackground(Color.blue);
		JButton btn = new JButton("AHAHAH");
		add(btn);
		btn.addActionListener(e -> {
			parentPanel.changeContentPanel(new JPanel());
		});
		
	} 
	
}
