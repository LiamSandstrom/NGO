package NGO.UI;

import NGO.User;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ContentPanelStructure extends JPanel {

	public ContentPanelStructure(User user, UIStructure parentPanel){
	
		setPreferredSize(new Dimension(800, 700));
	} 
        
        public void eDate(){
            JOptionPane.showMessageDialog(null, "Wrong format, format must be: ''YYYY-MM-DD''.\nOr there are no active project for those dates.\nOr there was an error with the database");
        }
	
}
