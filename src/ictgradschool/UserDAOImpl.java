package ictgradschool;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
//import java.lang.AutoCloseable; //java.io.Closeable; // see UserDAO interface

// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
// https://docs.oracle.com/javase/7/docs/api/java/lang/AutoCloseable.html
// https://docs.oracle.com/javase/7/docs/api/java/lang/AutoCloseable.html

public class UserDAOImpl implements UserDAO {

	private String usersTable = "RegisteredUsers";
	private String connectionURL = "jdbc:derby://localhost:1527/Lab19Ex06_UserDB;create=true";
	private Connection connection;
	
	
	public UserDAOImpl() throws ClassNotFoundException, SQLException {		
		Class.forName("org.apache.derby.jdbc.ClientDriver"); // Register the jdbc driver
		connection = DriverManager.getConnection(connectionURL); // Open a connection		
	}
	
	@Override
	public void close() throws SQLException { // defined by AutoCloseable
		// AutoCloseable.close() ideally idempotent: calling close 2nd time should not side-effect
		if(connection != null) { 
			connection.close();				
			connection = null;
		}
	}

	@Override
	public List<User> getAllUsers() throws Exception {
		String sqlStmt = "SELECT * FROM "+usersTable;		
		return doQuery(sqlStmt);
	}

	@Override
	public void addUser(User user) throws Exception {
		String sqlStmt = "INSERT INTO "+usersTable+" (username, firstname, lastname, email) VALUES ("
				+ user.getUsername() + "," 
				+ user.getFirstname() + "," 
				+ user.getLastname() + "," 
				+ user.getEmail()
				+ ")";
		doUpdate(sqlStmt);

	}

	@Override
	public void deleteUser(User user) throws Exception {
		String sqlStmt = "DELETE FROM "+usersTable+" WHERE username="+user.getUsername();
		doUpdate(sqlStmt);
	}

	@Override
	public void updateUser(User user) throws Exception {
		String sqlStmt = "UPDATE "+usersTable+" SET"
				+ " firstname=" + user.getFirstname()
				+ " lastname=" + user.getLastname()
				+ " email=" + user.getEmail()
				+ " WHERE username=" + user.getUsername();
		doUpdate(sqlStmt);
	}

	public User getUser(String username) throws Exception {
		String sqlStmt = "SELECT * FROM "+usersTable+" WHERE username = '" + username + "'";
		List<User> users = doQuery(sqlStmt);
		
		if(users == null) 
			return null;
		else
			return users.get(0); // username is a primary key, so there should be no more than 1 result
	}

	// PRIVATE METHODS
	private void doUpdate(String sqlStmt) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(sqlStmt); 	
			// executeUpdate is for a DDL SQL stmt, or DML statement of type INSERT, UPDATE or DELETE
		statement.close();
	}
	
	private List<User> doQuery(String sqlStmt) throws Exception {
		ArrayList<User> resultList = new ArrayList<User>();
		
		// Try-with-resources. Both Statement and ResultSet are AutoCloseable		
		try (
			Statement statement = connection.createStatement(); // forward-only, read-only result set, 
									// http://www.tutorialspoint.com/jdbc/jdbc-result-sets.htm
			ResultSet rs = statement.executeQuery(sqlStmt);		
		) {								
			if(!rs.next()) { // positioned to first record, and then found there were none 
					// http://stackoverflow.com/questions/8292256/get-number-of-rows-returned-by-resultset-in-java
				return null;
			} else {				
				
				// get the rows
				do {					
					User user = new User(
						rs.getString("firstname"), 
						rs.getString("lastname"), 
						rs.getString("username"), 
						rs.getString("email")
					);
					
					resultList.add(user);					
					
				} while(rs.next());
			}
			
		} catch(SQLException se) {
			
			se.printStackTrace();
			throw(se); // rethrow
			
		} 
		
		return resultList;
	}
	
}
