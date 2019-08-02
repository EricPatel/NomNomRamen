import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener{
	public Game g;
	
	public Controller(Game g){
		this.g = g;
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			g.rightDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			g.leftDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			g.downDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			g.upDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(g.gameOver){
				g.replay = true;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			g.leftDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			g.rightDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP){
			g.downDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			g.upDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(g.gameOver){
				g.replay = true;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(g.gameOver){
				g.replay = true;
			}
		} 
		
	}
}
