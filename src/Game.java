
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
	int x = 475;
	int y = 475;
	int dx = 2;
	int dy = 2;
	int size;
	Food food = new Food(1000, 1000);
	Enemy[] enemy = new Enemy[7];
	int score = 0;
	BufferedImage backgroundImage = null;
	BufferedImage characterImage = null;
	BufferedImage foodImage = null;
	BufferedImage enemyImage = null;
	BufferedImage heartImage = null;
	BufferedImage bulletImage = null;
	boolean gameOver = false;
	boolean playAgain = false;
	boolean draw = true;
	boolean replay = false;
	int lives = 3;
	boolean god = false;
	long time;// 1000 millisec in sec
	long animateTime;
	int delta = 1;
	int counter = 0;
	int facing = 1;
	long deadTime;
	boolean leftDown = false, rightDown = false, downDown = false, upDown = false;
	boolean drawToScreen = true;

	public void animate() {
		if (counter + delta == 4) {
			delta = -1;
		} else if (counter + delta == -1) {
			delta = 1;
		}
		counter += delta;
	}

	private void checkEaten() {
		if (hit(x, y, food.x, food.y)) {
			food.spawn(x, y, size);
			score += 10;
			// size += 10;
		}
	}

	private void checkDead() {
		if (lives == 0 && System.currentTimeMillis() - deadTime > 800) {
			gameOver = true;
		}
		if (System.currentTimeMillis() - time > 1000) {
			god = false;
		}
		if (god) {
			return;
		}
		for (int i = 0; i < enemy.length; i++) {
			if (Enemy.hit(x, y, enemy[i].x, enemy[i].y, size)) {
				lives--;
				god = true;
				time = System.currentTimeMillis();
				if (lives == 0) {
					deadTime = System.currentTimeMillis();
					drawToScreen = false;
					return;
				}
				return;
			}
		}

	}

	public void moveFood() {
		if (System.currentTimeMillis() - animateTime > 90) {
			food.animate();
			animate();
			animateTime = System.currentTimeMillis();
		}
	}

	private boolean hit(int x2, int y2, int x3, int y3) {
		if ((y2 + size > y3) && (y2 < y3 + 50) && (x2 + size > x3) && (x2 < x3 + 50)) {
			return true;
		}
		return false;
	}

	private void move() {
		if (leftDown) {
			x -= dx;
		}
		if (rightDown) {
			x += dx;
		}
		if (upDown) {
			y -= dy;
		}
		if (downDown) {
			y += dy;
		}
		if (x + size + dx >= this.getWidth()) {
			x = getWidth() - size - 1;
		}
		if (x + dx <= 0) {
			x = 0;
		}
		if (y + size + dy >= this.getHeight()) {
			y = getHeight() - size - 1;
		}
		if (y + dy <= 0) {
			y = 0;
		}
	}

	private void moveEnemy() {
		for (int i = 0; i < enemy.length; i++) {
			if (enemy[i].x > 1080 || enemy[i].y > 1080 || enemy[i].alive == false || enemy[i].x < -80
					|| enemy[i].y < -80) {
				enemy[i].alive = false;
				enemy[i].spawn(x, y, size, food);
				continue;
			}
			if (enemy[i].direction == 'x') {
				enemy[i].x += enemy[i].delta;
			} else {
				enemy[i].y += enemy[i].delta;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		Font font = g2d.getFont();
		font = font.deriveFont(40.0f);
		g2d.setFont(font);
		for (int i = 0; i < 1000; i += 200) {
			for (int j = 0; j < 1000; j += 200) {
				g2d.drawImage(backgroundImage, i, j, null);
			}
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillOval(food.x + 8 - food.counter, food.y + 50, 36 + (food.counter * 2), 20);
		for (int i = 0; i < enemy.length; i++) {
			if (enemyImage != null && enemy[i].alive) {
				g2d.fillOval(enemy[i].x, enemy[i].y + 78, 80, 20);
			}
		}
		if (god && draw) {
			if (lives == 0) {
				g2d.fillOval(x + 10 - counter, y + 105, 77 + (counter * 2), 20);
				if (facing == -1) {
					g2d.drawImage(characterImage, x + 100, y + counter, facing * 100, 100, null);
				} else {
					g2d.drawImage(characterImage, x, y + counter, null);
				}
			}
			draw = false;
		} else {
			draw = true;
			g2d.fillOval(x + 10 - counter, y + 105, 77 + (counter * 2), 20);
			if (facing == -1) {
				g2d.drawImage(characterImage, x + 100, y + counter, facing * 100, 100, null);
			} else {
				g2d.drawImage(characterImage, x, y + counter, null);
			}

		}
		// g2d.drawImage(bullet, 100, 100, null);
		g2d.drawImage(foodImage, food.x, food.y + food.counter, null);

		for (int i = 0; i < enemy.length; i++) {
			if (enemyImage != null && enemy[i].alive) {
				// g2d.fillOval(enemy[i].x, enemy[i].y+ 80, 80, 20);
				g2d.drawImage(enemyImage, enemy[i].x, enemy[i].y, null);
			}
		}

		// g2d.drawImage(andrew, x, y, size, size, null);
		switch (lives) {
		case 3:
			g2d.drawImage(heartImage, 780, 20, null);
		case 2:
			g2d.drawImage(heartImage, 840, 20, null);
			if (god && draw) {
				g2d.drawImage(heartImage, 780, 20, null);
			}
		case 1:
			g2d.drawImage(heartImage, 900, 20, null);
			if (god && draw) {
				g2d.drawImage(heartImage, 840, 20, null);
			}
		case 0:
			if (god && draw) {
				g2d.drawImage(heartImage, 900, 20, null);
			}
		}
		g2d.drawString("Score: " + score, 10, 50);
	}

	public static void main(String[] args) throws InterruptedException {

		JFrame frame = new JFrame("Nom Nom Ramen");
		Game game = new Game();
		Controller c = new Controller(game);
		frame.add(game);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(c);
		BufferedImage ramen = null;
		try {
			game.backgroundImage = ImageIO.read(Game.class.getResource("grass_top.png"));
			game.characterImage = ImageIO.read(Game.class.getResource("rsz_unicorn.png"));
			game.foodImage = ImageIO.read(Game.class.getResource("rsz_ramen.png"));
			ramen = ImageIO.read(Game.class.getResource("ramen.png"));
			game.enemyImage = ImageIO.read(Game.class.getResource("rsz_rock.png"));
			game.heartImage = ImageIO.read(Game.class.getResource("rsz_heart.png"));
			game.bulletImage = ImageIO.read(Game.class.getResource("rsz_cone.png"));
		} catch (IOException e) {
		}
		game.size = game.characterImage.getWidth();
		game.food.spawn(game.x, game.y, game.size);
		for (int i = 0; i < game.enemy.length; i++) {
			game.enemy[i] = new Enemy(1000, 1000); 
			game.enemy[i].spawn(game.x, game.y, game.size, game.food);
		}
		frame.setIconImage(ramen);

		Thread t = new Thread(game);
		t.start();
	}

	@Override
	public void run() {
		animateTime = System.currentTimeMillis();
		while (!gameOver || playAgain) {
			if(playAgain){
				
			}
			if (drawToScreen) {
				this.move();
				this.moveFood();
				this.moveEnemy();
			}
			this.checkEaten();
			this.checkDead();
			this.repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.drawEnd(this.getGraphics());
	}

	private void drawEnd(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		Font font = g2d.getFont();
		font = font.deriveFont(40.0f);
		g2d.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		// Determine the X coordinate for the text
		int x = (1000 - metrics.stringWidth("Game Over")) / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as
		// in java 2d 0 is top of the screen)
		int y = ((1000 - metrics.getHeight()) / 2) + metrics.getAscent();
		// Set the font
		// Draw the String
		g.drawString("Game Over", x, y - 15);
		font = g2d.getFont();
		font = font.deriveFont(30.0f);
		g2d.setFont(font);
		metrics = g.getFontMetrics();
		int x2 = (1000 - metrics.stringWidth("Score: " + this.score)) / 2;
		g.drawString("Score: " + this.score, x2, y + 15);
		font = g2d.getFont();
		font = font.deriveFont(20.0f);
		g2d.setFont(font);
		metrics = g.getFontMetrics();
		int x3 = (1000 - metrics.stringWidth("(Press Enter to Play Again!)")) / 2;
		g2d.drawString("(Press Enter to Play Again!)", x3, y + 300);
		while (System.currentTimeMillis() - time < 5000) {
		}
	}

}