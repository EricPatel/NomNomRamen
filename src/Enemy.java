import java.util.Random;

public class Enemy {
	int w;
	int h;
	int x;
	int y;
	int delta = 0;
	char direction;
	Random r = new Random();
	boolean alive = false;

	public Enemy(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void spawn(int xc, int yc, int size, Food food) {
		if(!alive && r.nextInt(10) == 0){
			if(r.nextInt(2) == 0){
				if(r.nextInt(2) == 0){
					this.y = -80;
					this.x = r.nextInt(h-80);
					direction = 'y';
				}
				else{
					this.x = -80;
					this.y = r.nextInt(h-80);
					direction = 'x';
				}
				this.delta = r.nextInt(2)+1;
				alive = true;
			}
			else{
				if(r.nextInt(2) == 0){
					this.y = h+80;
					this.x = r.nextInt(h-80);
					direction = 'y';
				}
				else{
					this.x = w + 80;
					this.y = r.nextInt(h-80);
					direction = 'x';
				}
				this.delta = -1 * (r.nextInt(2)+1);
				alive = true;
			}
		}
		/*
		this.x = r.nextInt(w - 300) + 100;
		this.y = r.nextInt(h - 300) + 100;
		if (hit(xc, yc, x, y, size) || hit(food.x, food.y, x, y, 50)) {
			spawn(xc, yc, size, food);
		}*/
		return;
	}

	public static boolean hit(int x2, int y2, int x3, int y3, int size) {
		if ((y2 + size - 25 > y3) && (y2 < y3 + 65) && (x2 + size - 25 > x3) && (x2 < x3 + 65)) {
			return true;
		}
		return false;
	}
}
