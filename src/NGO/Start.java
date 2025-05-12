package NGO;


import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;


/**
 *
 * @author liam
 */

public class Start {
	
	private static InfDB idb;
	private static MainWindow mainWindow;
	
	public static void main(String[] args) {
		try {
			idb = new InfDB("SDGSweden", "3306", "dbAdmin2024", "dbAdmin2024PW");
			mainWindow = new MainWindow(idb);

		} catch (InfException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Failed to connect to database");
		}
		
	}
}
