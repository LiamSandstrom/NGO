/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO;

import java.util.ArrayList;
import java.util.Random;
import oru.inf.InfDB;

/**
 *
 * @author liam
 */
public class Admin extends User {

	public Admin(InfDB idb, String id) {
		super(idb, id);

	}

	public String generateRandomPassword(int length) {
		String alfabet[] = {
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"å", "ä", "ö", "1", "2", "3", "4", "5", "6", "7", "8", "9"
		};
		Random random = new Random();

		String password = "";
		for(int i = 0; i < length; i++){
			int randomNumber = random.nextInt(alfabet.length);
			password = password + alfabet[randomNumber];
		}
		return password;
	}
}
