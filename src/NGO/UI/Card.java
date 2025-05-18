/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NGO.UI;

import javax.swing.JPanel;

/**
 *
 * @author liam
 */
public interface Card<T> {
	JPanel createCard(T data);
}
