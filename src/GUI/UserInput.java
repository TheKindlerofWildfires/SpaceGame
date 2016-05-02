package GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserInput implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			System.out.println('a');
			break;
		case KeyEvent.VK_Z:
			System.out.println('z');
			break;
		case KeyEvent.VK_X:
			System.out.println('x');
			break;
		case KeyEvent.VK_D:
			System.out.println('d');
			break;
		case KeyEvent.VK_E:
			System.out.println('e');
			break;
		case KeyEvent.VK_W:
			System.out.println('w');
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
