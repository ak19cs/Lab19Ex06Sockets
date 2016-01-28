package ictgradschool;

public class User {
	
	// username is the primary key
	private String firstname; 
	private String lastname; 
	private String username;
	private String email;	
	
	
	public User() {}
	
	public User(String fname, String lname, String uname, String mail) {
		firstname = fname;
		lastname = lname;
		username = uname;
		email = mail;		
	}	
	
	
	public String getFirstname() { return firstname; }
	public String getLastname() { return lastname; }
	public String getUsername() { return username; }
	public String getEmail() { return email; }
	
	
	public void setFirstname(String fname) { firstname = fname; }
	public void setLastname(String lname) { lastname = lname; }
	public void setUsername(String uname) { username = uname; }
	public void setEmail(String emailAddress) { email = emailAddress; }
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("username:\t").append(username);
		result.append("\nfirstname:\t").append(firstname);
		result.append("\nlastname:\t").append(lastname);
		result.append("\nemail:\t").append(email);
		return result.toString();
		
		/*return "username:\t" + username
				+ "\nfirstname:\t" + firstname 
				+ "\nlastname:\t" + lastname 
				+ "\nemail:\t" + email;*/		
	}
	
	
	public String toHTMLString(String tc, String te) { // tc = tablecell, te = end tablecell		
		StringBuffer html = new StringBuffer().append("<tr>");
		html.append(tc).append(firstname).append(te);
		html.append(tc).append(lastname).append(te);
		html.append(tc).append(username).append(te);
		html.append(tc).append(email).append(te);
		html.append("</tr>");
		
		return html.toString();
		
		/*return "<tr>"
			+ tc + firstname + te
			+ tc + lastname + te
			+ tc + username + te
			+ tc + email + te 
			+ "</tr>";*/
	}
	
}
