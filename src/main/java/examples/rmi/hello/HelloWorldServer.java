/*
 *  This software copyright by various authors including the RPTools.net
 *  development team, and licensed under the LGPL Version 3 or, at your
 *  option, any later version.
 *
 *  Portions of this software were originally covered under the Apache
 *  Software License, Version 1.1 or Version 2.0.
 *
 *  See the file LICENSE elsewhere in this distribution for license details.
 */

package examples.rmi.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server program for the HelloWorld example.
 * @author <a href="mailto:superbonbon@sbbi.net">SuperBonBon</a>
 * @version 1.0
 */

public class HelloWorldServer {

	public static final String LABEL = "HelloWorld";
	
	public static void main ( String[] args ) {
		try {
			
			int port = 1099;

			exportServer(LABEL, port, new HelloWorld());

			System.out.println ( "Object bound HelloWorld server is ready." );
		} catch ( Exception e ) {
			System.out.println( "Hello Server failed: " + e );
		}
	}

	private static HelloWorld exportServer(String label, int port, HelloWorld hello) throws Exception{
		Registry r = LocateRegistry.createRegistry(port);
		r.bind(label, hello);

		return hello;
	}


}
