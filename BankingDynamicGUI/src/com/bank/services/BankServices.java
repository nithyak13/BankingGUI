package com.bank.services;

import com.bank.beans.Account;
import com.bank.beans.User;
import com.bank.beans.Transaction;

public interface BankServices {

	boolean validateAccount(String userId, String userName, String password);

	void populateCustomerList(String userId, User user,Account acc);
	
	void depositAmount(String userId, String userName, Transaction trans);
	
	void withdrawAmount(String userId, String userName, Transaction trans);

	public void removeCustomer(String userId, String UserName);
		
	void printEmployeeList();

	void printCustomerList();

	void viewBalance(String userId, String accNumber);

	void viewTransactionDetails(String userId, String accNumber);

	
}
