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
import javax.jmdns.ServiceListener;

/**
 * Sample Code for Service Type Discovery using JmDNS and a ServiceTypeListener.
 * <p>
 * Run the main method of this class. It lists all service types known on the local network on System.out.
 *
 * @author Werner Randelshofer
 */
public class ServiceDiscoverSpecific {

    static class SpecificServiceListener implements ServiceListener {

		@Override
		public void serviceAdded(ServiceEvent event) {
			// TODO Auto-generated method stub
			System.out.println(event.getName());
		}

		@Override
		public void serviceRemoved(ServiceEvent event) {
			// TODO Auto-generated method stub
			System.out.println(event.getType());
		}

		@Override
		public void serviceResolved(ServiceEvent event) {
			// TODO Auto-generated method stub
			String host = event.getInfo().getInet4Addresses()[0].toString().substring(1);
			int port = event.getInfo().getPort();
			System.out.println(host+":"+port);
		}
    }

    public static void main(String[] args) throws UnknownHostException {
    	
    	InetAddress address = InetAddress.getLocalHost();
    	
        try {
            JmDNS jmdns = JmDNS.create(address);
            jmdns.addServiceListener(ServiceCreation.SERVICE_TYPE, new SpecificServiceListener());

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
