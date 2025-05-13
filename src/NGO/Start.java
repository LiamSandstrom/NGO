package NGO;

import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author liam
 */
public class Start {

	public static void main(String[] args) {
		FlatMacDarkLaf.setup();
		new MainWindow();
	}
}
