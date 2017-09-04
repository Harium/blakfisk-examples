package examples.topaction;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.action.view.ActionClientApplication;

public class TopActionClientExample extends Etyl {
	
	private static final long serialVersionUID = 1L;

	public TopActionClientExample() {
		super(400, 400);
	}

	public static void main(String[] args) {
		TopActionClientExample client = new TopActionClientExample();
		client.init();
	}

	@Override
	public Application startApplication() {
		return new ActionClientApplication(w, h);
	}

}
