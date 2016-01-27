package ictgradschool;

import java.util.List;

public interface UserDAO extends AutoCloseable {
	
	public List<User> getAllUsers() throws Exception;
	
	public void addUser(User a) throws Exception;
	public void deleteUser(User a) throws Exception;
	public void updateUser(User a) throws Exception;
	
	public User getUser(String username) throws Exception;	
}
