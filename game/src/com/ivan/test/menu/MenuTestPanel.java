package com.ivan.test.menu;

import javax.swing.JPanel;
import java.awt.event.*;

public class MenuTestPanel extends JPanel {
	public MenuTestPanel() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				pressedKey = e.getKeyChar();
			}

			public void keyReleased(KeyEvent e) {
				pressedKey = ' ';
			}

			public void keyTyped(KeyEvent e) {

			}
		});
	}

	public char getPressedKey() {
		return pressedKey;
	}
	private char pressedKey = ' ';
}
