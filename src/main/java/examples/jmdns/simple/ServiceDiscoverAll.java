// Licensed under Apache License version 2.0
// Original license LGPL

//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package examples.jmdns.simple;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceTypeListener;

/**
 * Sample Code for Service Type Discovery using JmDNS and a ServiceTypeListener.
 * <p>
 * Run the main method of this class. It lists all service types known on the local network on System.out.
 *
 * @author Werner Randelshofer
 */
public class ServiceDiscoverAll {

    static class ServiceListener implements ServiceTypeListener {

        @Override
        public void serviceTypeAdded(ServiceEvent event) {
        	//Info is null here
        	System.out.println("Service type added: " + event.getType());
        }

        @Override
        public void subTypeForServiceTypeAdded(ServiceEvent event) {
            System.out.println("SubType for service type added: " + event.getType());
        }
    }

    public static void main(String[] args) throws UnknownHostException {
    	
    	InetAddress address = InetAddress.getLocalHost();
    	
        try {
            JmDNS jmdns = JmDNS.create(address);
            jmdns.addServiceTypeListener(new ServiceListener());

            System.out.println("Press q and Enter, to quit");
            int b;
            while ((b = System.in.read()) != -1 && (char) b != 'q') {
                /* Stub */
            }
            jmdns.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
