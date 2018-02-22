package fr.epita.iam.launcher;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.AuthenticationException;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.exceptions.IdentityDeletionException;
import fr.epita.iam.exceptions.IdentityListException;
import fr.epita.iam.exceptions.IdentityUpdationException;
import fr.epita.iam.services.IdentityJDBCDAO;

public class Main {
    
	/**
	 * @author Omkar Yadav and Sohel Khan Pathan 
	 * 
	 * This is the program is in Launcher Package, which drives the main functionality of the project
	 * Firstly, program will ask user to enter User name and password.
	 * Authentication will take place with the help of database table "Users"
	 * Once user is authenticated, a menu with possible option will be displayed.
	 * Options are to Create, Update, Delete an Identity which is core the functionality of the project. 
	 * And remaining options to list the identities and exit the program.
	 * 
	 * @param args   No Arguments to main method
	 * @throws ClassNotFoundException  List of Exceptions.
	 * @throws SQLException  List of Exceptions.
	 * @throws AuthenticationException  List of Exceptions.
	 */
	public static void main(String[] args) throws  ClassNotFoundException, SQLException, AuthenticationException{
		
		String username;    //User name for Authentication
        String password;    //Password for Authentication
        String uid;         //Identity's Unique ID 
        String email;       //Identity's Email address
        String name;        //Identity's Name
        Boolean authStatus; //Authorization status
        
        // Scanner to take input from user
        Scanner scan = new Scanner(System.in);
        
        //For User Authentication 
        IdentityJDBCDAO identityDAO = new IdentityJDBCDAO();
        System.out.println("Welcome to IAM System!");
        System.out.println("Please enter the Username and Password for authentication ");
        System.out.println("Username: ");
        username = scan.nextLine();
        
        System.out.println("Password: ");
        password = scan.nextLine();
        
        /**
         * Authenticate user method "authentication" 
         */
        authStatus = identityDAO.authentication(username, password); 
        
        if(!authStatus){
        	/* User is not authorized to access the program, 
        	 * as user credentials are not maintained in table "Users".
            */          
        	System.out.println("Invalid credentials; You are not authorized to execute this program.");
           	scan.close();
           	return;
        	}	
        else{
        	System.out.println( username + " authorized Successfully.");
        }
        
        
        boolean iteration = true;
        String choice;
        
        //Display the Menu on the console
        while(iteration==true){
        	System.out.println("");
        	System.out.println("IAM system valid operations:");
        	System.out.println("");
          	System.out.println("1. Create an Identity");
        	System.out.println("2. Update an Identity");
        	System.out.println("3. Delete an Identity");
        	System.out.println("4. List all the Identities");
        	System.out.println("5. Terminate the program.");
        	System.out.println("");
        	System.out.println("Enter operation to be performed (1,2,3,4,5)");
        	choice = scan.nextLine();
        	
        	//To perform operations based on the choice
        	switch(choice){
        	
        	//1. Create an Identity
        	case "1" : 
        		System.out.println("To Create an Identity please enter UID, Display Name and Email ID");
        		
        		System.out.println("UID:");
        		uid = scan.nextLine(); //Read Identity UID entered by user
        		
        		System.out.println("Display Name:");
        		name = scan.nextLine(); //Read Identity Name entered by user
        		
        		System.out.println("Email ID:");
        		email = scan.nextLine(); //Read Identity Email Address entered by users
        		
        		Identity identity_c = new Identity(name,email,uid); // Create identity object with Name email and UID
        		
        		try{
        			// Create Identity by calling identity_create of class IdentityJDBCDAO
        			identityDAO.identity_create(identity_c);
        		}
        		catch(IdentityCreationException e){ 
        			/*Raise the custom exception, 
        			 * created for Identity creation operation
        			*/        			
        			System.out.println(e.getMessage());
        		}
        		
        		break;
        		
        	//2. Update an Identity
        	case "2" :
        		
                System.out.println("To Update an Identity, please enter UID and new Display Name, Email ID for identity");
        		
        		System.out.println("Enter UID ");
        		uid = scan.nextLine(); //Read Identity UID entered by user
        		
        		System.out.println("Enter new Display Name to be updated:");
        		name = scan.nextLine(); //Read Identity Name entered by user
        		
        		System.out.println("Enter new Email ID to be updated:");
        		email = scan.nextLine(); //Read Identity Email Address entered by user
        		
        		/*
        		 *   Update identity object with Name email and UID
        		 */
        		Identity identity_u = new Identity(name,email,uid); 
        		
        		try{
        			/*
        			 *  Update Identity by calling identity_update of class IdentityJDBCDAO
        			 */
        			identityDAO.identity_update(identity_u);
        		}
        		catch(IdentityUpdationException e){ 
        			/* Raise the custom exception, 
        			 * created to update an Identity
        			*/        			
        			System.out.println(e.getMessage());
        		}
        		
        		break;
        		
        	//3. Delete an Identity
        	case "3" :
        		
        		System.out.println("To Delete an Identity please enter UID");
        		
        		System.out.println("UID:");
        		uid = scan.nextLine(); //Read Identity UID
        		
        		Identity identity_d = new Identity(null,null,uid); // Delete identity object with UID
        		
        		try{
        			// Delete an Identity by calling identity_delete method of class IdentityJDBCDAO
        			identityDAO.identity_delete(identity_d);
        		}
        		catch(IdentityDeletionException e){ 
        			/* Raise the custom exception, 
        			 * created for Identity deletion operation
        			*/        			
        			System.out.println(e.getMessage());
        		}
        		
        		break;
        		
        	//4. List all identities
        	case "4" :
        		
        		System.out.println("To List the identities, please enter UID, Display Name and Email ID");
        		
        		System.out.println("UID:");
        		uid = scan.nextLine(); //Read Identity UID
        		
        		System.out.println("Display Name:");
        		name = scan.nextLine(); //Read Identity Name
        		
        		System.out.println("Email ID:");
        		email = scan.nextLine(); //Read Identity Email Address
        		
        		Identity identity_l = new Identity(name,email,uid); // List the identities object with Name email and UID
        		
        		try{
        			/**
        			 *  List the Identities by calling identity_list of class IdentityJDBCDAO        			
        			 */
        			List<Identity> identities = identityDAO.identity_list(identity_l); 
        			
        			/**
        			 * List the all the identities in the list with toString()
        			 */        			
        			System.out.println(identities);
        			

        		}
        		catch(IdentityListException e){ 
        			/* Raise the custom exception, 
        			 * created for Identity listing operation
        			*/        			
        			System.out.println(e.getMessage());
        		}
        		
        		break;
            
        	//5. Exit the program
        	case "5" :
        		System.out.println("Thank you for using IAM program. Program will now terminate.");
        		iteration = false;
        		break;
        	
        	//Invalid choice not in 1,2,3,4 or 5.	
            default:
            	System.out.println("Invalid Choice:" +choice+ ". Kindly enter valid operation number(1,2,3,4,5)");
            	break;
        		
        	}
        }
        
        
        scan.close();
	}

}
