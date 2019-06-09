package com.bank.clientsim;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.bank.controller.*;

public class BankApplication {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("\nPress 1 for Employee login and Press 2 for Customer login:");
		int choice = scan.nextInt();
		
		if(choice==1) {
		LoginController C = new LoginController();
		boolean validation = false;
		validation = C.validateAccount();
		
		if (validation) {

			System.out.println("\nAuthentication Successful..");

			// run loop until choice 5 is pressed
			int ch;

			do {
				System.out.println("\n\t\t~~~~  Employee Menu  ~~~~\n \t\t1.Create Customer\n \t\t2.Remove Customer \n\t\t3.Display Employee Details\n "
								+ "\t\t4.Display Customer Details\n\t\t5.Exit");
				System.out.println("\nEnter Ur Choice :");
				
				try{
					ch = scan.nextInt();
				}
				 catch ( InputMismatchException ex )
			    { 
			      System.out.println("You entered bad data." );
			      System.out.println("Run the program again." );
			      break;
			    } 
					
				
				switch (ch) {
				case 1:
					C.createCustomer();
					break;
				case 2:
					C.removeCustomer();
					break;
				case 3:
					C.printEmployeeList();
					break;
				case 4:
					C.printCustomerList();
					break;
				case 5:
					System.out.println("Good Bye..");
					break;
				default:
					System.out.println("please enter a number between 1 and 5..");
					break;	
				}// end of switch;

			} while (ch != 5);
		} // end of if
		else {
			System.out.println(
					"Sorry !! You are Not Authenticated to access Employee Menu.Please verify your credentials");
		}
		}
		else if(choice==2) {
		int ch1;

	do {
		LoginController C = new LoginController();
		System.out.println("\n\t\t~~~~ Customer Menu  ~~~~\n \t\t1.Create Account\n \t\t2.Deposit Amount\n\t\t3.Withdraw amount\n "
						+ "\t\t4.View Balance\n\t\t5.View Transaction Details\n\t\t6.Exit");
		System.out.println("\nEnter Ur Choice :");
		
		try{
			ch1 = scan.nextInt();
		}
		 catch ( InputMismatchException ex )
	    { 
	      System.out.println("You entered bad data." );
	      System.out.println("Run the program again." );
	      break;
	    } 
			
		switch (ch1) {
		
		case 1:
			C.createCustomer();
			break;
		case 2:		
			C.depositAmount();		
			break;
		case 3:
			C.withdrawAmount();
			break;
		case 4:
			C.viewBalance();
			break;			
		case 5:
			C.viewTransactionDetails();
			break;
		case 6:
			System.out.println("Good Bye..");
			break;
		default:
			System.out.println("Please Enter a number between 1 and 6..");
			break;	
		}// end of switch;

	} while (ch1 != 6);
} // end of if
		
	}
	}// end of main


