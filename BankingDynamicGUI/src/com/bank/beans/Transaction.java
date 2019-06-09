package com.bank.beans;
import java.util.Date;

public class Transaction {
	
	private String userId;
	private String transactionType;
	private Date transactionTime;
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String toString() {
		return "Transaction Type:" +transactionType + "   " +"Amount:"+amount+"  "+"Transaction Time:" + transactionTime ;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
