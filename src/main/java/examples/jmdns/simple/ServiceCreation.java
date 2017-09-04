package examples.jmdns.simple;

import java.io.IOException;
import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class ServiceCreation {

	public static final String SERVICE_TYPE = "_pcamera._tcp.local.";
	
	public static void main(String[] args) throws IOException {
		
		InetAddress address = InetAddress.getLocalHost();
		System.out.println(address.getHostAddress());
		
		ServiceInfo service = ServiceInfo.create(SERVICE_TYPE, "service_name", 12345, "path=index.html"); 
		
		JmDNS jmdns = JmDNS.create(address);
		//JmDNS jmdns = JmDNS.create();
	    jmdns.registerService(service);
	    
	    int b;
	    
	    System.out.println("Service created...");
	    System.out.println("Press q and closing JmDNS...");
	    
	    //Keep service alive
	    while ((b = System.in.read()) != -1 && (char) b != 'q') {
            /* Stub */
        }
        System.out.println("Closing JmDNS...");
        jmdns.unregisterService(service);
        jmdns.unregisterAllServices();
        jmdns.close();
	}

}
