package NGO;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;

public class User {

	public enum Role {
		HANDLAGGARE,
		ADMIN
	}

	private Role role;
	private InfDB idb;
	private String id;

	public User(InfDB idb, String id) {
		this.idb = idb;
		this.id = id;
		fetchRole();

	}
        
        public String getName() throws InfException{
            String namn = "";
            try{
                String firstName = idb.fetchSingle("SELECT fornamn FROM anstalld where aid = " + id + ";");
                String lastName = idb.fetchSingle("SELECT efternamn FROM anstalld where aid = " + id + ";");
                namn = firstName + " " + lastName;
            }catch(InfException e){
                JOptionPane.showMessageDialog(null, "Could not fetch namn where id is " + id );
            }
            return namn;
        }
        
	public Role getRole() {
		return role;
	}

	public String getId() {
		return id;
	}

	private void fetchRole() {
		try {
			String result = idb.fetchSingle("select aid from admin where aid = " + id + ";");
			if (result != null) {
				role = Role.ADMIN;
				System.out.println(role);
				return;
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			String result = idb.fetchSingle("select aid from handlaggare where aid = " + id + ";");
			if (result != null) {
				role = Role.HANDLAGGARE;
				System.out.println(role);
				return;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("NO ROLE ERROR");
	}

	public List<String> getProjects() {
		try {
			ArrayList<String> result = idb.fetchColumn("select pid from ans_proj where aid = " + id + ";");
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("NO PROJECTS");
		return new ArrayList<String>();
	}

	public String getAvdelning() {
		try {
			String result = idb.fetchSingle("select avdelning from anstalld where aid = " + id + ";");
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("USER HAS NO AVDELNING");
		return "";
	}
        
        public InfDB getDb(){
            return idb;
        }
}
