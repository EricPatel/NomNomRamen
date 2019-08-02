import java.util.Random;

public class Food {
	int w;
	int h;
	int x;
	int y;
	int counter = 0;
	int delta = 1;
	Random r = new Random();

	public Food(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void animate() {
		if (counter + delta == 4) {
			delta = -1;
		} else if (counter + delta == -1) {
			delta = 1;
		}

		counter += delta;
	}

	public void spawn(int x1, int y1, int size) {
		this.x = r.nextInt(w - 300) + 100;
		this.y = r.nextInt(h - 300) + 100;
		if (hit(x1, y1, x, y, size)) {
			spawn(x1, y1, size);
		}
		return;
	}

	private boolean hit(int x2, int y2, int x3, int y3, int size) {
		if ((y2 + size > y3) && (y2 < y3 + 50) && (x2 + size > x3) && (x2 < x3 + 50)) {
			return true;
		}
		return false;
	}
}
