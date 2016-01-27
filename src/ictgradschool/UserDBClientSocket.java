package ictgradschool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
// https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockClient.java
public class UserDBClientSocket {
	
	
	public static void usage() {
		System.out.println("Run as: java UserDBClientSocket <hostname> <portnum>");
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args == null || args.length != 2) {
			UserDBClientSocket.usage();
			System.exit(-1);
		}
		
		String hostname = args[0];
		int port = -1;
		try {
			port = Integer.parseInt(args[1]);
		} catch(Exception e) {
			System.err.println("port number must be a numeric value");
			UserDBClientSocket.usage();
			System.exit(-1);
		}
		
		
		try (
			Socket serverSocket = new Socket(hostname, port);
			PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true); //autoflush output to server
			BufferedReader serverStreamReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));	
		){
			
			System.out.print("Enter username (EXIT to quit)> ");
			// read from stdin and write to serversocket
			BufferedReader userStreamReader = new BufferedReader(new InputStreamReader(System.in));
			String userInputLine = null;
			
			while((userInputLine = userStreamReader.readLine()) != null) {	
				out.println(userInputLine);
				if(userInputLine.equals("EXIT")) break;
			
				String serverLine = null;
				System.out.println("Response from Server output:");
				while((serverLine = serverStreamReader.readLine()) != null) {					
					
					if(serverLine.equals("DONE")) break;
					
					System.out.println(serverLine);
				}
				
				System.out.print("Enter username (EXIT to quit)> ");
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		 
	}

}
