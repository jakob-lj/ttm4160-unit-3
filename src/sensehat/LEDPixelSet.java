package sensehat;

public class LEDPixelSet {
	
		private int x;
		private int y;
		private int red;
		private int green;
		private int blue;

		public LEDPixelSet() {
			x = 0;
			y = 0;
			red = 0;
			green = 0;
			blue = 0;
		}

		public void set(int x, int y, int red, int green, int blue) {
			this.x = x;
			this.y = y;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setRed(int red) {
			this.red = red;
		}

		public void setGreen(int green) {
			this.green = green;
		}

		public void setBlue(int blue) {
			this.blue = blue;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getRed() {
			return red;
		}

		public int getGreen() {
			return green;
		}

		public int getBlue() {
			return blue;
		}
	
}
