package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.AuthenticationException;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.IdentityListException;
import fr.epita.iam.exceptions.IdentityUpdationException;
import fr.epita.iam.services.Configuration;

/**
 * 
 * @author Omkar Yadav and Sohel Khan Pathan 
 * 
 * This is the program in Services package, intended to handle actual functionality of 
 *
 */
public class IdentityJDBCDAO implements IdentityDAO{
	
   //1. Setting connection String
//   private final String connectionString = "jdbc:derby://localhost:1527/iam-b;create=true";	
   private final String connectionString = Configuration.getInstance().getProperty("db.host");   
						
   //2. Assigning User name
   private final String userName = "root";

   //3. Assigning Password
   private final String password = "root";
			
   //Select Query for Authentication
   private final String selectQueryForAuth = "SELECT USERNAME, PASSWORD FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
			
   //Insert Query for Creating an Identity
   private final String inserQueryForCreation = "INSERT INTO IDENTITIES (UID,DISPLAYNAME,EMAIL) VALUES (?,?,?)";
	
   //Update Query for Updating an Identity
   private final String updateQueryForUpdation = "UPDATE IDENTITIES SET DISPLAYNAME=?,EMAIL=? WHERE UID=?";
		
   //Delete Query for Deleting an Identity
   private final String deleteQueryForDeletion = "DELETE FROM IDENTITIES WHERE UID = ?";
	
   //Select Query for Listing the Identities
   private final String selectQueryForListing = "SELECT UID, DISPLAYNAME, EMAIL FROM IDENTITIES WHERE DISPLAYNAME = ? OR EMAIL = ? OR UID = ? ";
			
   Connection connection = null;
   
   /**
    * This method is the responsible to establish connection with the Derby Database
    * Following are the four vital parameters for the connection:
    * 1) Connection String
    * 2) Driver Name
    * 3) User name
    * 4) Password
    * 
    * @return Returns the established connection
    * @throws ClassNotFoundException Exception List
    * @throws SQLException Exception List
    */
   private Connection getConn() throws ClassNotFoundException,SQLException {
		if (this.connection == null || this.connection.isClosed()){
			try {
					Class.forName("org.apache.derby.jdbc.ClientDriver");
					} catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					}
					this.connection = DriverManager.getConnection(connectionString,userName,password);
				}
				return this.connection;
				
	}
	
    /**
     * This method is the responsible to authenticate the user
     * User is authorized, if user has ID, password entry in table Users
     * User is not authorized, if user has no ID, password entry in table Users
     * 
     * @param username ::  User Name for authentication
     * @param passwd   ::  Password for authentication
     * @return         ::  True(Authorized User)/False(Unauthorized User)
     * @throws AuthenticationException  :: Exception List
     * @throws SQLException  :: Exception List
     * @throws ClassNotFoundException :: Exception List
     */
	public boolean authentication(String username, String passwd) throws AuthenticationException, SQLException, ClassNotFoundException{

		     
		try{
			
		connection = getConn();
		
		//Select Query for authentication..............................................
		final PreparedStatement preparedStatement  = connection.prepareStatement(selectQueryForAuth);		
		
		//Sets the designated parameter to the Prepared Statement Query
		preparedStatement.setString(1, username); // User name
		preparedStatement.setString(2, passwd); // Password
		
		/*
		 *  Build the result set based on the select query
		 */
		final ResultSet resultSet = preparedStatement.executeQuery();
				
		if (resultSet.next()) {		
			/*
			 * User is an authorized user, method will True boolean value
			 */
			connection.close();
			return true;
		}
		else {
			/*
			 * User is not an authorized user, method will False boolean value
			 */
			connection.close();
		    return false;		
			}
		
		}
		
		catch ( ClassNotFoundException | SQLException e) {
			
			//Raise the custom Authentication Exception
			final AuthenticationException businessException = new AuthenticationException(username, e);			
			throw businessException;
		}
		
		finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
			
		}    
    
	/**
     * This method is the responsible to Create an Identity
     * Based on the user's input, a new identity is created with UID, Display Name and Email ID.
     * The new identity is created in database table "Identities"
     * 
     * @param identity ::  Identity with UID, Display Name and Email ID.
     * @throws IdentityCreationException  :: Exception List
     */
    public void identity_create(Identity identity) throws IdentityCreationException{
    	
    	try{
    		connection = getConn();
    		
    		//Insert Query for Creating an Identity..............................................
    		final PreparedStatement preparedStatement  = connection.prepareStatement(inserQueryForCreation);		
    		
    		//Sets the designated parameter to the Prepared Statement Query    		
    		preparedStatement.setString(1, identity.getUid());   //UID
    		preparedStatement.setString(2, identity.getDisplayName()); //Display Name
    		preparedStatement.setString(3, identity.getEmail()); // Email 		
    		int result = preparedStatement.executeUpdate(); // Execute the query 
    		
    		if(result > 0){
    	    // Query Executed successfully
    		System.out.println("Identity "+identity.getUid()+" created successfully!");
    		}
    		else{
        	    // Query was not executed successfully    			
    			System.out.println("Unable to create an Identity "+identity.getUid());
    		}
    		   		
    	}
    	
    	catch ( ClassNotFoundException | SQLException e) {
    		// Raise Custom Identity Creation exception 
			final IdentityCreationException businessException = new IdentityCreationException(identity, e);
			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
    }
    	
}
    
    /**
     * This method is the responsible to Update an Identity
     * Based on the user's input, an identity is updated with only UID
     * New Display Name and Email ID is updated for specific UID. 
     * The new Display Name and Email ID  is updated in database table "Identities"
     * 
     * @param identity ::  Identity with UID, Display Name and Email ID.
     * @throws IdentityUpdationException  :: Exception List
     */
    public void identity_update(Identity identity) throws IdentityUpdationException {
    	
    	try{
    		connection = getConn();
    		
    		//Update Query for Updating an Identity..............................................
    		final PreparedStatement preparedStatement  = connection.prepareStatement(updateQueryForUpdation);	
    		//Sets the designated parameter to the Prepared Statement Query    		
    		preparedStatement.setString(3, identity.getUid()); //UID
    		preparedStatement.setString(1, identity.getDisplayName());//Display Name
    		preparedStatement.setString(2, identity.getEmail()); //Email
    		int result = preparedStatement.executeUpdate();
    		
    		if(result > 0){
    		// ID was successfully updated
    		System.out.println("Identity "+identity.getUid()+" Updated successfully!");
    		}
    		else{
    			//UID does not exist
    			System.out.println("Identity "+identity.getUid()+" does not exist!");
    		}
    		   		
    	}
    	
    	catch (ClassNotFoundException | SQLException e) {
    		// Raise Custom Identity Updating exception
			final IdentityUpdationException businessException = new IdentityUpdationException(identity, e);
			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
    }
    	
    }
    
    /**
     * This method is the responsible to Delete an Identity
     * Based on the user's input, an identity is deleted with only UID
     * An identity will be deleted from database table "Identities"
     * 
     * @param identity ::  Identity with UID, Display Name and Email ID.
     * @throws IdentityDeletionException  :: Exception List
     */
    public void identity_delete(Identity identity) throws IdentityDeletionException {
    	
    	try{
    		connection = getConn();
    		
    		//Delete Query for Deleting an Identity..............................................
    		final PreparedStatement preparedStatement  = connection.prepareStatement(deleteQueryForDeletion);		
    		
    		//Sets the designated parameter to the Prepared Statement Query  
    		preparedStatement.setString(1, identity.getUid());	//UID
    		int result = preparedStatement.executeUpdate();
    		
    		if(result > 0){
    	    // ID was successfully updated
    		System.out.println("Identity "+identity.getUid()+" Deleted successfully!");
    		}
    		else{
    			//UID does not exist
    			System.out.println("Identity "+identity.getUid()+" does not exist!");
    		}
    		   		
    	}
    	
    	catch (ClassNotFoundException | SQLException e) {
    		// Raise Custom Identity Deletion exception
			final IdentityDeletionException businessException = new IdentityDeletionException(identity, e);
			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
    }
    	
    }
    
    
    public List<Identity> identity_list(Identity criteria) throws IdentityListException {
    	
    	final List<Identity> identities = new ArrayList<>();
		Connection connection = null;
		try {
			connection = getConn();
			final PreparedStatement preparedStatement = connection.prepareStatement(selectQueryForListing);
			preparedStatement.setString(3, criteria.getUid());
			preparedStatement.setString(1, criteria.getDisplayName());
			preparedStatement.setString(2, criteria.getEmail());

			final ResultSet resultSet = preparedStatement.executeQuery();
			boolean rsFlag = false;
				
			while (resultSet.next()) {
				rsFlag = true;
				final Identity identity = new Identity();
				identity.setDisplayName(resultSet.getString(2));
				identity.setEmail(resultSet.getString(3));
				identity.setUid(resultSet.getString(1));
				identities.add(identity);
				
			}//While loop

			if (!rsFlag){
				//UID does not exist
				System.out.println("No identity exists for given criteria.");			
			}
		} catch (ClassNotFoundException | SQLException e) {
    		// Raise Custom Identity Listing exception
			final IdentityListException businessException = new IdentityListException(criteria, e);
			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}// Catch block
		}//Finally block

		return identities;
		
    }
}
    

