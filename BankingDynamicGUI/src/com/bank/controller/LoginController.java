package com.bank.controller;

import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;

import com.bank.beans.Account;
import com.bank.beans.Transaction;
import com.bank.beans.User;
import com.bank.services.BankServices;
import com.bank.services.impl.BankServiceImpl;


public class LoginController extends HttpServlet  {

	private String userId;
	private String userName;
	private String password;
	private String userType;
	private String firstName;
	private String lastName;
	private String accType;
	private String accNumber;
	private int balance;

	Scanner scan = new Scanner(System.in);
	BankServices bankServices = null;

	public LoginController() {
		bankServices = new BankServiceImpl();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	           throws ServletException, java.io.IOException {

		try
		{
			 
			User user = new User();
			String userName =request.getParameter("un"); 
			user.setUserName(userName);
			String password=request.getParameter("pw");
			user.setPassword(password);
			
			String userId =request.getParameter("uid");
			
			System.out.println("UserName :"+ userName);
			//System.out.println("password :"+ password);
			
			boolean accountFlag = bankServices.validateAccount(userId, userName, password);
	    
			if (accountFlag)
			{
			 	HttpSession session = request.getSession(true);	    
				session.setAttribute("currentSessionUser",user); 
				response.sendRedirect("UserLogged.jsp"); //logged-in page      		
			}	        
			else 
				response.sendRedirect("InvalidLogin.jsp"); //error page 
			} 
			catch (Throwable theException) 	    
			{
				System.out.println(theException); 
			}
	}
	
	public boolean validateAccount() {
		System.out.println("Enter User Id: ");
		userId =scan.next();
		System.out.print("Enter User Name: ");
		userName = scan.next();
		System.out.print("Enter Password: ");
		password = scan.next();

		boolean accountFlag = bankServices.validateAccount(userId, userName, password);

		return accountFlag;

	}

	public void createCustomer() {
		User user = new User();
		boolean validation = false;
		int flag=0;
		
		System.out.print("Enter User Id: ");
		userId = scan.next();
		System.out.print("Enter User Name: ");
		userName = scan.next();
		System.out.print("Enter Password: ");
		password = scan.next();
		System.out.print("Enter First Name: ");
		firstName = scan.next();
		System.out.print("Enter Last Name: ");
		lastName = scan.next();
		System.out.println("Enter User Type:");
		userType = scan.next();

		Account acc=new Account();
	
		System.out.print("Enter Account Number: ");
		accNumber = scan.next();
		System.out.println("Enter Account Type:");
		accType = scan.next();
		System.out.println("Enter Balance:");
		balance = scan.nextInt();
		
		validation = bankServices.validateAccount(userId, userName, password);
		if (validation) {
			System.out.println("\nCustomer already exists..");
			flag=1;
		}
		
		if(flag==0) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setUserId(userId);
		user.setUserName(userName);
		user.setUserType(userType);
		
		acc.setUserId(userId);
		acc.setAccNumber(accNumber);
		acc.setAccType(accType);
		acc.setBalance(balance);

		bankServices.populateCustomerList(userId, user,acc);
		}
	}
	
	public  void depositAmount()
	{
		Transaction trans = new Transaction();
		trans.setTransactionType("deposit");
		trans.setTransactionTime(new Date());
		
		System.out.print("Enter User Id: ");
		userId = scan.next();
		System.out.print("Enter User Name: ");
		userName = scan.next();
		System.out.print("Enter Amount to be deposited: ");
		int depositAmount = scan.nextInt();
		
		trans.setAmount(depositAmount);
		bankServices.depositAmount(userId,userName,trans);
	}
	public void withdrawAmount() {
		Transaction trans = new Transaction();
		trans.setTransactionType("withdraw");
		trans.setTransactionTime(new Date());
		System.out.print("Enter User Id: ");
		userId = scan.next();
		System.out.print("Enter User Name: ");
		userName = scan.next();
		System.out.print("Enter Amount to be withdrawn: ");
		int withdrawAmount = scan.nextInt();
		
		trans.setAmount(withdrawAmount);
		
		bankServices.withdrawAmount(userId,userName,trans);
	}
	
	public void viewBalance() {
		System.out.print("Enter User Id to be viewed: ");
		userId =scan.next();
		System.out.print("Enter Account Number to be viewed: ");
		accNumber = scan.next();
		
		bankServices.viewBalance(userId,accNumber);
	}
	
	public void removeCustomer() {
		System.out.print("Enter User Id to be removed: ");
		userId =scan.next();
		System.out.print("Enter User Name to be removed: ");
		userName = scan.next();
		bankServices.removeCustomer(userId, userName);
	}

	public void printEmployeeList() {
		bankServices.printEmployeeList();
	}

	public void printCustomerList() {
		bankServices.printCustomerList();
	}

	public void viewTransactionDetails() {
		System.out.print("Enter User Id to be viewed: ");
		userId =scan.next();
		System.out.print("Enter Account Number to be viewed: ");
		accNumber = scan.next();
		bankServices.viewTransactionDetails( userId,accNumber);
		
	}

}