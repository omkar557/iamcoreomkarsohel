package fr.epita.iam.exceptions;

import fr.epita.iam.datamodel.Identity;

public class IdentityUpdationException extends Exception{
	
	Identity faultyIdentity;

	
	public IdentityUpdationException(Identity identity, Exception originalCause) {
		faultyIdentity = identity;
		initCause(originalCause);

	}
	
	public String getMessage() {
		return "problem occured while updating that identity in the system " + faultyIdentity.toString();
	}

}
