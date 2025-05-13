package NGO;

import java.util.ArrayList;
import java.util.List;
import oru.inf.InfDB;

public class User {

	enum Role {
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
}
