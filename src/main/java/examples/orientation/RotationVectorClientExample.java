package examples.orientation;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.orientation.view.OrientationClientApplication;

public class RotationVectorClientExample extends Etyl {
	
	private static final long serialVersionUID = 1L;

	public RotationVectorClientExample() {
		super(400, 400);
	}

	public static void main(String[] args) {
		RotationVectorClientExample client = new RotationVectorClientExample();
		client.setTitle("Rotation Client");
		client.init();
	}

	@Override
	public Application startApplication() {
		// TODO Auto-generated method stub
		return new OrientationClientApplication(w, h);
	}

}
