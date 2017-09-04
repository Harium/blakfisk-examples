package examples.orientation.model;

public class PlayerOrientation {
	public double yaw;
	public double pitch;
	public double roll;
	public int x = 0;
	public int y = 0;
	
	public boolean isLongClick;
	
	public PlayerOrientation() {
		super();
	}
	
	public PlayerOrientation(double yaw, double pitch, double roll) {
		super();
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}
	
}