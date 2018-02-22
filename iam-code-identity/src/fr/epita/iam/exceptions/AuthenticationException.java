package fr.epita.iam.exceptions;

public class AuthenticationException extends Exception{
	
	String username;
	
	public AuthenticationException(String uname,Exception originalCause) {
		username = uname;
		initCause(originalCause);

	}
	
	public String getMessage() {
		return "An error occured while authenticating username: " + username ;
	}

}
