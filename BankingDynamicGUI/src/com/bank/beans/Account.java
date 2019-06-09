package com.bank.beans;

public class Account {
	
	private String userId;
	private String accNumber;
	private String accType;
	private int balance;
	public String getAccNumber() {
		return accNumber;
	}
	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String toString() {
		return "UserId:" +userId+"  "+ "Account type:" +accType + "  " + "Account Number:" +accNumber + "  " + "Balance:" +balance;
	}
}
