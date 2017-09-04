package examples.stream;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.stream.application.StreamServerApplication;

public class StreamServerExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public StreamServerExample() {
        super(1280, 480);
    }

    public static void main(String[] args) {
        StreamServerExample server = new StreamServerExample();
        server.init();
    }

    @Override
    public Application startApplication() {
        return new StreamServerApplication(w, h);
    }

}
