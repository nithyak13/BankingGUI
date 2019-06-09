package com.bank.dao;

import java.util.HashMap;
import com.bank.beans.Account;
import com.bank.beans.Transaction;
import com.bank.beans.User;

public interface BankDataRepo {
	
	public HashMap<String, User> getUserList();
	public HashMap<String, Account> getAccountList();
	public HashMap<String,Transaction> getTransList();

	boolean populateCustomerList(String userId, User user,Account acc);
	
	boolean populateTransactionDetails(String userId, Account acc,Transaction trans);

	public boolean removeCustomer(String userId, String UserName);
	
	void printEmployeeList();

	void printCustomerList();
	
}
