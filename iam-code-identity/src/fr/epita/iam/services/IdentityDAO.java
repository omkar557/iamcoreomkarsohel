package fr.epita.iam.services;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.IdentityListException;
import fr.epita.iam.exceptions.IdentityUpdationException;

public interface IdentityDAO {
	
	public void identity_create(Identity identity) throws IdentityCreationException;

	public void identity_update(Identity identity) throws IdentityUpdationException;

	public void identity_delete(Identity identity) throws IdentityDeletionException;

	public List<Identity> identity_list(Identity criteria) throws IdentityListException;


}
