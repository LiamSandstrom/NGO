/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author david
 */
public class Validate {

	private User user;
	private static InfDB idb;

	public Validate() {
	}//Överlagring av konstruktor vid behov av validering utan relation till databas

	public Validate(User user) {
		this.user = user;
		this.idb = user.getDb();
	}

	public static boolean epost(String epost) {
		boolean isCorrect = false;
		if (epost.matches("^$")) {// om den är tom
			JOptionPane.showMessageDialog(null, "Email cant be empty");
		} else if (epost.matches("^\\s*$") || epost.matches(".*\\s.*")) { // // epost innehåller mellanslag i början eller i mitten av stängen
			JOptionPane.showMessageDialog(null, "Email cannot contain space");
		} else if (!epost.matches(".*@example.com")) {// ej innehåller "@example.com"
			JOptionPane.showMessageDialog(null, "Email must contain ''@example.com''");
		} else { // annars rätt
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean adress(String adress) {
		boolean isCorrect = false;
		if (adress.matches("^\\d{3} [A-Za-zåäöÅÄÖ ]+, [A-Za-zåäöÅÄÖ ]+$")) {
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "The correct format for an adress is \n ''123 Street name, City name''");
		}
		return isCorrect;
	}

	/* public static boolean adress(String adress) {

        try {
            boolean isCorrect = false;
            if (adress.matches(".*,.*")) {//Om adressen inte innehåller ","
                if (zip(adress.substring(0, adress.indexOf(" ")))) {
                    if (street(adress.substring(adress.indexOf(" "), adress.indexOf(",")))) {
                        if (city(adress.substring(adress.indexOf(",", (adress.length()-1))))) {
                            isCorrect = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "City must only contain character(can contain space), ex ''A city name''");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Street must only contain character(can contain space), ex ''example street''");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Zip code must be 3 numbers, ex ''345''");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Adress is missing '',''");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Adress cant be empty, format should be ''XXX Street name, City name''");
        }
        return isCorrect;
    }*/
	public static boolean city(String city) {
		boolean isCorrect = false;
		if (city.matches("^ [A-Za-z]+$")) { //city ska innehålla bara bokstäver och börja på mellanslag ,får innehålla mellanslag
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean street(String street) {
		boolean isCorrect = false;
		if (street.matches("^ [A-Za-z ]+$")) { //Street ska innehålla bara bokstäver och börja på mellanslag, får innehålla mellanslag
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean zip(String zip) {
		boolean isCorrect = false;
		if (zip.matches("^\\d{3}")) { // om zip är 3 siffror
			isCorrect = true;
		}
		return isCorrect;
	}

	public static boolean telefon(String telefon) {
		boolean isCorrect = false;
		if (telefon.matches("^\\d{3}-\\d{3}-\\d{4}$")) { // formatet måste matcha exakt
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Phone number should be ''XXX-XXX-XXXX''");
		}
		return isCorrect;
	}

	public static boolean pass(String pass) {
		boolean isCorrect = false;
		if (pass.matches(".{8,}")) { // minst 8 tecken
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Password must be at least 8 character, ex ''abcdefgh''");
		}
		return isCorrect;
	}

	public static boolean firstName(String name) {
		boolean isCorrect = false;
		if (name.matches("^[A-ZÅÄÖ][a-zåäö]{1,30}$")) { // mellan 2-30 tecken, ej siffror
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "First name must be 2-30 character and not contain numbers,\n first character should be capital, ex ''Name''");
		}
		return isCorrect;
	}

	public static boolean lastName(String name) {
		boolean isCorrect = false;
		if (name.matches("^([A-ZÅÄÖ][a-zåäö]{1,20})( [A-ZÅÄÖ][a-zåäö]{1,20})*$")) { //tecken, ej siffror, tillåter mellannamn och efternamn i vilket varje får vara 2-20 tecken och måste börja på stor bokstav och ha ett mellanslag emellan, exkluderat mellanslag i antal
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Last name must be 4-40 character and not contain numbers,\n can contain one space Middle Name and Last Name \nmust start with a capital letter,\n ex ''Lastname'' or ''Middlename Lastname''");
		}
		return isCorrect;
	}

	public static boolean date(String date) {
		boolean isCorrect = false;
		if (date.matches("^\\d{4}-\\d{2}-\\d{2}")) {
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Date format must be ''YYYY-MM-DD''");
		}
		return isCorrect;
	}

	public static boolean projName(String name) {
		boolean isCorrect = false;
		if (name.matches("^.{2,}")) { //Minst 2 tecken
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Project name cant be empty and must contain 2 character, ''Project name''");
		}
		return isCorrect;
	}

	public static boolean description(String desc) {
		boolean isCorrect = false;
		if (desc.matches("^.{2,200}$")) { //Mellan 2-200 tecken
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Project description cant be empty and mustonly\ncontain 2-200 character, ''This is a description''");
		}
		return isCorrect;
	}

	public static boolean cost(String cost) {
		boolean isCorrect = false;
		if (cost.matches("^(0|[1-9]\\d*)([.]\\d{1,2})?$")) { //får ej börja på 0, kan vara 0, endast siffror, valfri summa, med 1-2 decimaler
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "cost cannot start with zero but can be zero, must contain 1-2 decimals, ex ''2000.00''");
		}
		return isCorrect;
	}

	public static boolean status(String status) {
		boolean isCorrect = false;
		if (status.matches("^(Pågående|Avslutat|Planerat)")) { //Status får endast vara en av tre strängar exakt
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Status must exactly match one of the words, ''Pågående'' or ''Avslutat'' or ''Planerat''");
		}
		return isCorrect;
	}

	public static boolean prio(String prio) {
		boolean isCorrect = false;
		if (prio.matches("^(Hög|Medel|Låg)")) { //Prio får endast vara en av tre strängar exakt
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Priority must exactly match one of the words, ''Hög'' or ''Medel'' or ''Låg''");
		}
		return isCorrect;
	}

	public static boolean id(String id) {
		boolean isCorrect = false;
		if (id.matches("^\\d+")) { //Id måste vara endast siffror, kan vara 0, men ej tom
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Id must be only numbers and cannot be empty, ex ''123''");
		}
		return isCorrect;
	}

	public boolean isInMyAuthority(String pid) {
		boolean inMyAuthority = false;
		try {
			String query = "select ans_proj.pid from projekt join ans_proj on projekt.pid = ans_proj.pid join anstalld on ans_proj.aid = anstalld.aid where projektchef = '" + user.getId() + "';";
			ArrayList<HashMap<String, String>> projsAndWorkers = idb.fetchRows(query);
			for (HashMap<String, String> aWorkerOnProj : projsAndWorkers) {
				for (String idColumn : aWorkerOnProj.keySet()) {
					String pidInDb = aWorkerOnProj.get(idColumn);
					if (pidInDb.equals(pid)) {
						inMyAuthority = true;
					}
				}
			}
			if (!inMyAuthority) {//För att skriva ut felmeddelande
				JOptionPane.showMessageDialog(null, "You do not have authority over this project");
			}
		} catch (InfException e) {
			JOptionPane.showMessageDialog(null, e);
		}

		return inMyAuthority;
	}

	public static boolean projectName(String namn) {
		boolean isCorrect = false;
		if (namn.length() > 1 && namn.length() <= 25) {
			isCorrect = true;
		} else {
			JOptionPane.showMessageDialog(null, "Project name must be 2-25 character, ex ''Project A''");
		}
		return isCorrect;
	}

	public static boolean idExists(String id, String idName, String table) {
		try {
			String val = idb.fetchSingle("select " + idName + " from " + table + " where " + idName + " = '" + id + "';");
			if (val != null && val.equals(id)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		JOptionPane.showMessageDialog(null, "Enter a valid " + table + "!");
		return false;
	}

	public static boolean idNotExists(String id, String idName, String table) {
		try {
			String val = idb.fetchSingle("select " + idName + " from " + table + " where " + idName + " = '" + id + "';");
			if (val != null && val.equals(id)) {
				JOptionPane.showMessageDialog(null, idName + " " + id + " already exists!");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	public static boolean branch(String name) {

		if (name.matches("^[A-ZÅÄÖ][a-zA-ZåäöÅÄÖ ]{1,30}$")) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Sector must be 2-30 characters! ex 'Ren energi'");
			return false;
		}

	}

	public static boolean validateString(String name) {

		if (name.matches("^[A-ZÅÄÖ][a-zA-ZåäöÅÄÖ ]{1,30}$")) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "String: " + name + " must be 2-30 characters! ex 'Big company'");
			return false;
		}
	}

	public static boolean validateEpost(String epost) {
		if (epost.matches("^(?=[^\\s]{3,25}$)[^\\s@]+@([^\\s@]+\\.)+[^\\s@]+$")) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Epost must be 3-25 characters! ex 'test@example.com'");
			return false;
		}
	}

	public static boolean validateTelefon(String nummer) {
		if (nummer.matches("^(\\+\\d{7,15}|\\d{3}-\\d{3}-\\d{4})$")) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Wrong Phone number format! ex +1234567890 or 123-456-7890");
			return false;
		}
	}

	public static boolean validateAdress(String adress) {
		if (adress.matches("^[a-zA-Z0-9åäöÅÄÖ ,./-]{5,50}$")) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Wrong adress format! 5-50 characters!");
			return false;
		}
	}
        
        public static boolean goalName(String name){
            boolean fitsFormat = false;
            if(name.length() > 2 && name.length() <= 40){
                fitsFormat = true;
            }
            return fitsFormat;
        }
        
        public static boolean partnerName(String name){
            boolean formatCorrect = false;
            if(name.matches("^[A-Za-z0-9åäöÅÄÖ ]{3,35}$")){ //Tillåter flera 3-35 bokstäver och siffror, flera, siffror, mellanslag och stora bokstäver tillåtna
                formatCorrect = true;
            }else{
                JOptionPane.showMessageDialog(null, "Wrong format!\nFormat must be 3-35 characters, only letters(lower and uppercase), numbers and spaces allowed.\n Ex: ''MyPartner Name1''");
            }
            return formatCorrect;
        }
}
