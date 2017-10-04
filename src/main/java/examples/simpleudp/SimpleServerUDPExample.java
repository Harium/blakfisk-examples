package examples.simpleudp;

import examples.simpleudp.server.HandShakeUDPServer;

public class SimpleServerUDPExample {

    public static void main(String[] args) {
        HandShakeUDPServer server = new HandShakeUDPServer(SimpleClientUDPExample.PORT);
        server.start();

        //Disable Firewalls
        //sudo service iptables stop
        //sudo service firewalld stop
    }

}
