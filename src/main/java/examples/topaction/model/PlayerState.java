package examples.topaction.model;

public class PlayerState {
	public int x = 0;
	public int y = 0;
	public double angle = 0;
	public int state = 0;
	public int weapon = 0;
	
	public PlayerState(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return x+" "+y+" "+angle+" "+state;
	}
}
