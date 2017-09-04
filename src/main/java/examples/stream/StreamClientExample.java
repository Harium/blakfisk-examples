package examples.stream;

import com.harium.etyl.Etyl;
import com.harium.etyl.commons.context.Application;
import examples.stream.application.StreamClientApplication;

public class StreamClientExample extends Etyl {

    private static final long serialVersionUID = 1L;

    public StreamClientExample() {
        super(640, 480);
    }

    public static void main(String[] args) {
        StreamClientExample client = new StreamClientExample();
        client.init();
    }

    @Override
    public Application startApplication() {
        return new StreamClientApplication(w, h);
    }

}
