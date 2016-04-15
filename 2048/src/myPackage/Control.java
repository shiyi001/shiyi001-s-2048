package myPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Control implements KeyListener{
	Model model;
	
	public Control(Model model){
		this.model = model;
	}
	
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		switch(keyCode){
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			model.upward();
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			model.downward();
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:	
			model.leftward();
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			model.rightward();
			break;
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_R:
			model.reset();
			break;
		default:
			break;
		}
	}
	
	public void keyReleased(KeyEvent e){
		
	}
	
	public void keyTyped(KeyEvent e){
		
	}
}
