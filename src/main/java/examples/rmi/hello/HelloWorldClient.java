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

import java.rmi.Naming;

/**
 * Client program for the HelloWorld example.
 * @author <a href="mailto:superbonbon@sbbi.net">SuperBonBon</a>
 * @version 1.0
 */

public class HelloWorldClient {

	private static final String LOCAL_HOST = "127.0.0.1";

	public static void main ( String[] args ) {
		String hostName = LOCAL_HOST;

		String say = "Hello, Mars!";
		
		try {
			HelloWorldInterface hello = (HelloWorldInterface)Naming.lookup( "rmi://" + hostName + "/"+HelloWorldServer.LABEL);
			System.out.println( hello.say( say ) );
		} catch ( Exception e ) {
			System.out.println( "HelloWorldClient exception: " + e );
		}

		System.out.println( "Press enter to exit" );
		try {
			System.in.read();
		} catch ( java.io.IOException ex ) {
		}
	}  
}
