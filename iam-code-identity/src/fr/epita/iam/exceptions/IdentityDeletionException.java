package fr.epita.iam.exceptions;

import fr.epita.iam.datamodel.Identity;

public class IdentityDeletionException extends Exception{
	
	Identity faultyIdentity;

	
	public IdentityDeletionException(Identity identity, Exception originalCause) {
		faultyIdentity = identity;
		initCause(originalCause);

	}
	
	public String getMessage() {
		return "problem occured while deleting that identity in the system " + faultyIdentity.toString();
	}

}
