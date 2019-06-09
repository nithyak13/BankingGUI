package com.bank.services.impl;

import java.io.IOException;
import java.util.HashMap;

import com.bank.beans.Account;
import com.bank.beans.User;
import com.bank.beans.Transaction;
import com.bank.services.BankServices;
import com.bank.dao.BankDataRepo;
import com.bank.dao.impl.BankDataRepoImpl;

public class BankServiceImpl implements BankServices {

	BankDataRepo bankDataRepo = null;
	static HashMap<String, User> userList = new HashMap();
	static HashMap<String, Account> accList = new HashMap();
	static HashMap<String, Transaction> transList = new HashMap();

	public BankServiceImpl() {

		bankDataRepo = new BankDataRepoImpl();
	}

	public boolean validateAccount(String userId, String userName, String password) {

		userList = bankDataRepo.getUserList();
		User user = userList.get(userId);

		if (user != null && userName.equals(user.getUserName())) {
			if (password.equals(user.getPassword())) {
				return true;
			}
		}

		return false;
	}

	public void depositAmount(String userId, String userName, Transaction trans) {

		accList = bankDataRepo.getAccountList();
		Account account = accList.get(userId);

		int amount = trans.getAmount();
		int balance = account.getBalance();
		int totalAmount = balance + amount;
		
		account.setBalance(totalAmount);
		System.out.println(account.getBalance());
		accList.put(userId,account);
		transList.put(userId, trans);
		System.out.println("in serv.impl:"+transList.get(amount));
		bankDataRepo.populateTransactionDetails(userId,account,trans);
		
		System.out.println("Amount deposited successfully");
				}

	public void withdrawAmount(String userId, String userName, Transaction trans) {
		accList = bankDataRepo.getAccountList();
		Account account = accList.get(userId);

		int amount = trans.getAmount();
		int balance = account.getBalance();
		int totalAmount = balance - amount;

		account.setBalance(totalAmount);
		transList.put(userId, trans);

		bankDataRepo.populateTransactionDetails(userId, account, trans);

		System.out.println("Amount withdrawn successfully");
	}

	public void populateCustomerList(String userId, User user, Account acc) {
		boolean populateCheck = bankDataRepo.populateCustomerList(userId, user, acc);
		if (populateCheck) {
			System.out.println("Customer Information has been stored successfully.");
		} else {
			System.out.println("Issue in Customer Information Storage. Please check your data");
		}

	}

	public void removeCustomer(String userId, String UserName) {
		boolean removeCheck = bankDataRepo.removeCustomer(userId, UserName);
		if (removeCheck) {
			System.out.println("Customer Information has been removed successfully..");
		} else {
			System.out.println("Issue in Customer Information Removal. Check the UserId..");
		}
	}

	public void viewBalance(String userId, String accNumber) {
		accList = bankDataRepo.getAccountList();
		if (accList != null) {
				Account acc = accList.get(userId);
				System.out.println(acc.toString());
		}
		else {
			System.out.println("Error in Printing..Plese check ur Data");
		}

	}
	
	public void viewTransactionDetails(String userId, String accNumber) {
		transList = bankDataRepo.getTransList();
		accList = bankDataRepo.getAccountList();
		
		System.out.println(" \n\tTransaction Details .. \n");
		if (accList != null) {
			Account acc = accList.get(userId);
			System.out.println(acc.toString());
		}
		if (transList != null) {
			Transaction trans =transList.get(userId);
			System.out.println(trans.toString());
		}
	}

	public void printEmployeeList() {
		bankDataRepo.printEmployeeList();

	}

	public void printCustomerList() {
		bankDataRepo.printCustomerList();

	}

	

}