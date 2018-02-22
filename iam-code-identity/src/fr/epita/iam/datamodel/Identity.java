package fr.epita.iam.datamodel;

/**
 * This class is inside Data Model Package.
 * This class is responsible for creating Identity with required parameters,
 * like UID, Email ID and Display name
 * 
 * @author Omkar Yadav And Sohel Khan Pathan
 *
 */
public class Identity {
	
	private String displayName; //Display name for the Identity
	private String uid;         //Unique ID for Identity
	private String email;       //Email address for Identity
	
	/**
	 *
	 */
	public Identity() {
	}

	/**
	 * @param displayName :: Display Name
	 * @param email :: Email ID
	 * @param uid :: UID
	 */
	public Identity(String displayName, String email, String uid) {
		this.displayName = displayName;
		this.email = email;
		this.uid = uid;
	}
	
//	Getters and Setters for Display Name
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
//	Getters and Setters for Unique ID
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
//	Getters and Setters for Email Address
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Identity [displayName=" + displayName + ", uid=" + uid + ", email=" + email + "]";
	}
	

}
