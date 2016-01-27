package ictgradschool;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
//import java.lang.AutoCloseable; //java.io.Closeable; // see UserDao interface

// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
// https://docs.oracle.com/javase/7/docs/api/java/lang/AutoCloseable.html
// https://docs.oracle.com/javase/7/docs/api/java/lang/AutoCloseable.html

public class UserDAOImpl implements UserDAO {

	private String usersTable = "RegisteredUsers";
	private String connectionURL = "jdbc:derby://localhost:1527/Lab19Ex06_UserDB;create=true";
	private Connection connection;
	
	
	public UserDAOImpl() throws ClassNotFoundException, SQLException {
		// 2. Register the jdbc driver
		Class.forName("org.apache.derby.jdbc.ClientDriver");
		// 3. Open a connection
		connection = DriverManager.getConnection(connectionURL);		
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
	public void addUser(User a) throws Exception {
		

	}

	@Override
	public void deleteUser(User a) throws Exception {
		

	}

	@Override
	public void updateUser(User a) throws Exception {
		

	}

	/*@Override
	public User getUser(int id) throws Exception {
		String sqlStmt = "SELECT * FROM "+usersTable+" WHERE userid = " + id;
		return null;
	}*/

	public User getUser(String username) throws Exception {
		String sqlStmt = "SELECT * FROM "+usersTable+" WHERE username = '" + username + "'";
		List<User> users = doQuery(sqlStmt);
		
		if(users == null) 
			return null;
		else
			return users.get(0);
	}

	// PRIVATE METHODS
	private List<User> doQuery(String sqlStmt) throws Exception {
		ArrayList<User> resultList = new ArrayList<User>();
		
		Statement statement = null;
		ResultSet rs = null;		
		
		try {
			// 4. Execute a query
			statement = connection.createStatement(); // forward-only, read-only result set, 
									// http://www.tutorialspoint.com/jdbc/jdbc-result-sets.htm
			
			rs = statement.executeQuery(sqlStmt);			
			
			// 5. Extract data from result set						
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
					
					//resultList.add(getNextRow(rs));
					
					
				} while(rs.next());
			}
			
		} catch(SQLException se) {
			
			se.printStackTrace();
			throw(se); // rethrow
			
		} finally {
			
			try {
				if(rs != null) rs.close();
			} catch(Exception e) {				
				e.printStackTrace(); // no rethrow, into web server log				
			}
			
			try {
				if(statement != null) statement.close();
			} catch(Exception e) {				
				e.printStackTrace(); // no rethrow, into web server log				
			}	
		}
		
		return resultList;
	}	
	
}
