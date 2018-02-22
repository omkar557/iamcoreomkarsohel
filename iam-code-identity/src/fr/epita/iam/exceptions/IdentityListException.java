package fr.epita.iam.exceptions;

import fr.epita.iam.datamodel.Identity;

public class IdentityListException extends Exception{
	
	Identity faultyIdentity;

	
	public IdentityListException(Identity identity, Exception originalCause) {
		faultyIdentity = identity;
		initCause(originalCause);

	}
	
	public String getMessage() {
		return "problem occured while listing the identity in the system " + faultyIdentity.toString();
	}

}
