package ictgradschool;

import java.io.*;
import java.net.*;

// Run as: 
// C:\Users\<User>\workspace\lab19Sockets\bin>java ictgradschool.UserDBClientSocket localhost 4444

public class UserDBServerSocket {

	// https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
	// https://docs.oracle.com/javase/tutorial/networking/sockets/examples/KnockKnockServer.java
	// http://tutorials.jenkov.com/java-multithreaded-servers/singlethreaded-server.html
	// http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
	// http://trac.greenstone.org/changeset/29017
	
	
	private int port = -1;	
	private UserDAO userDAO = null;
	
	
	public UserDBServerSocket(int portNum, UserDAO userDataAccessObject) {
		port = portNum;
		this.userDAO = userDataAccessObject;
		
		System.out.println("ServerSocket about to start listening at port " + port + "...");
		
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		// Because the AutoCloseable instances like socket etc are declared in a try-with-resource statement, 
		// they will be closed regardless of whether the try statement completes normally or abruptly  
		// (for instance, as a result of the method BufferedReader.readLine throwing an IOException).
		try (
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); //autoflush output
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));			
		) {
			String username = null;
			
			while((username = reader.readLine()) != null) {
				
				if(username.equals("EXIT")) {					
					System.out.println("Socket connection ended.");
					break;
				} 
				
				System.out.println("Received search request for username: " + username);
				
				User user = userDAO.getUser(username);
				
				
				if(user == null) {
					out.println("Username |" + username + "| not found.");
				} else {	
					out.println("************************");
					out.println("Found user:\n" + user);					
					out.println("************************");
				}
				
				out.println("DONE");
			}
			
		} catch(IOException ioe) {
			System.err.println("Exception reading from client socket.");
			ioe.printStackTrace();
		} catch(Exception e) {
			System.err.println("Exception accessing the User Data.");
			e.printStackTrace();
		}
	} 

	
	public static void usage() {
		System.out.println("Run as: java UserDBServerSocket <portnum>");
		
	}
	
	public static void main(String[] args) {		
		
		if(args.length != 1) {
			UserDBServerSocket.usage();
			System.exit(-1);
		}
		int port = -1;
		
		try {
			port = Integer.parseInt(args[0]);
		} catch(NumberFormatException nfe) {
			System.err.println("port number must be a numeric value");
			UserDBServerSocket.usage();
			System.exit(-1);
		}
		
		
		try ( UserDAO userDAO = new UserDAOImpl(); ) { // a try-with-resources stmt
			
			if(port > 0) {				
				
				UserDBServerSocket userDBServerSocket = new UserDBServerSocket(port, userDAO);			
			}
			
			// userDAO.close() will be called now, even if an exception occurs
			// See https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		} catch(Exception e) {
			System.err.println("Error in main: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
