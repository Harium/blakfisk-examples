package examples.action;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.action.view.ActionClientApplication;

public class ActionClientExample extends Etyl {
	
	private static final long serialVersionUID = 1L;

	public ActionClientExample() {
		super(400, 400);
	}

	public static void main(String[] args) {
		ActionClientExample client = new ActionClientExample();
		client.init();
	}

	@Override
	public Application startApplication() {
		// TODO Auto-generated method stub
		return new ActionClientApplication(w, h);
	}

}
