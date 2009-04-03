package com.ivan.game.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JPanel;

public class GameCanvas extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2635338105799380475L;

	public GameCanvas() {
		controlhandler = new KeyHandler();
		setFocusable(true);
	}

	public void equipListener(boolean b) {
		if (b) {
			if (this.getKeyListeners().length == 0)
				addKeyListener(controlhandler);
		} else {
			if (this.getKeyListeners().length == 1)
				removeKeyListener(controlhandler);
		}
	}

	public char getInput() {
		return input;
	}

	private KeyHandler controlhandler;

	private char input = ' ';

	private Thread thread = Thread.currentThread();

	/*
	 * the control listener.
	 */
	private class KeyHandler implements KeyListener {
		private char prekey = ' ';

		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyChar() == input) {
				input = ' ';
				prekey = ' ';
			}
		}

		public void keyTyped(KeyEvent e) {

			if (e.getKeyChar() != 'j') {
				input = e.getKeyChar();
			} else {
				if (prekey != 'j') {
					prekey = 'j';
					input = 'j';
				} else {
					input = ' ';
				}
			}
		}
	}
}
