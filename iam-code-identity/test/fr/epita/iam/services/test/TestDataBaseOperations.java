package fr.epita.iam.services.test;

import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.IdentityListException;
import fr.epita.iam.exceptions.IdentityUpdationException;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * 
 * @author Omkar Yadav and Sohel Khan Pathan
 *
 */
public class TestDataBaseOperations {

	public static void main(String[] args) throws IdentityCreationException, IdentityDeletionException, 
	        IdentityUpdationException, IdentityListException{
		
		IdentityJDBCDAO idJDBC = new IdentityJDBCDAO();
		Identity identity = new Identity("Sohel", "SohelPathan@gmail.com","333");	
		
		System.out.println("Identity Creation Test:");
		idJDBC.identity_create(identity);
		System.out.println("Identity has been created successfully!");
		List<Identity> identities_c = idJDBC.identity_list(identity);
		System.out.println(identities_c);
		
		System.out.println("Identity Updation Test:");
		identity.setDisplayName("Omkar_Yadav");
		idJDBC.identity_update(identity);
		System.out.println("Identity has been updated successfully!");
		List<Identity> identities_u = idJDBC.identity_list(identity);
		System.out.println(identities_u);
		
		System.out.println("Identity Deletion Test:");
		identity.setDisplayName(null);
		identity.setEmail(null);
		identity.setUid("333");
		idJDBC.identity_delete(identity);
		System.out.println("Identity has been deleted successfully!");
		List<Identity> identities_d = idJDBC.identity_list(identity);
		System.out.println(identities_d);	
		
		
		
	}

}
