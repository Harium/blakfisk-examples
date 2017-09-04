package examples.room;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.room.view.RoomClientApplication;

public class RoomClientExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public RoomClientExample() {
        super(800, 480);
    }

    public static void main(String[] args) {
        RoomClientExample client = new RoomClientExample();
        client.init();
    }

    @Override
    public Application startApplication() {
        return new RoomClientApplication(w, h);
    }

}
