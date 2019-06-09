package com.bank.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
//import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import com.bank.beans.Account;
import com.bank.beans.User;
import com.bank.beans.Transaction;
import com.bank.dao.BankDataRepo;

public class BankDataRepoImpl implements BankDataRepo {

	static HashMap<String, User> userList = null;
	static HashMap<String, Account> accList = new HashMap();
	static HashMap<String, Transaction> transList = new HashMap();
	String dataSource = "";
	Connection con = null;

	public BankDataRepoImpl() {
		userList = new HashMap();

		try {
			FileReader fr = new FileReader("C:/Users/sathi/workspace/BankingDynamicGUI/src/properties.txt");
			//C:\Users\sathi\workspace\BankingDynamicGUI\src\properties.txt
			Properties p = new Properties();
			p.load(fr);
			dataSource = p.getProperty("datasource");

			if (dataSource.equals("Hashmap")) {
				populateList();
			} 
			else if (dataSource.equals("File")) {
				populateList();
			} 
			else if (dataSource.equals("database"))  {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "admin");
				populateList();
			}
		} catch (Exception ee) {
			System.out.println("Exception occurred while processing the file : " + ee.getMessage());
		}

	}

	private void populateList() throws IOException {

		if (dataSource.equals("Hashmap")) {

			User employeeUser = new User();
			employeeUser.setUserId("1234");
			employeeUser.setUserName("Nithya");
			employeeUser.setPassword("welcome123");
			employeeUser.setUserType("EmployeeManager");
			employeeUser.setFirstName("Nithya");
			employeeUser.setLastName("Kalyani");

			userList.put("1234", employeeUser);
			employeeUser = new User();
			employeeUser.setUserId("5678");
			employeeUser.setUserName("Saran");
			employeeUser.setPassword("welcome567");
			employeeUser.setUserType("EmployeeTeller");
			employeeUser.setFirstName("Saran");
			employeeUser.setLastName("kumar");
			
			userList.put("12345", employeeUser);
		} 
		else if (dataSource.equals("File")) {
			String filePath = "user.txt";
		    String line;
		    HashMap<String, User> userList = new HashMap();
		    BufferedReader reader = new BufferedReader(new FileReader(filePath));
		    while ((line = reader.readLine()) != null)
		    {	
		    	User user = new User();
		        String[] parts = line.split(":", 2);
		        if (parts.length >= 2)
		        {
		            String key = parts[0];
		            String value = parts[1];
		            if (key.equals("UserId"))
			        {
			        	user.setUserId(value);
			        	System.out.println("id:"+user.getUserId());
			        }
			        else if (key.equals("UserName"))
			        {
			        	user.setUserName(value);
			        	System.out.println("name:"+user.getUserName());
			        }
			        else if (key.equals("Password"))
			        {
			        	user.setPassword(value);
			        	System.out.println("pwd:"+user.getPassword());
			        }
			        else if (key.equals("UserType"))
			        {
			        	user.setUserType(value);
			        	System.out.println("type:"+user.getUserType());
			        }
			        else if (key.equals("FirstName"))
			        {
			        	user.setFirstName(value);
			        	System.out.println("fn:"+user.getFirstName());
			        }
			        else if (key.equals("LastName"))
			        {
			        	user.setLastName(value);
			        	System.out.println("ln:"+user.getLastName());
			        }
		        }
				   else {
				        System.out.println("ignoring line: " + line);
				    }
		        userList.put(user.getUserId(), user);	
		        
				}
		   
		    //System.out.println(userList);
		    for (User key:userList.values()) {
		        System.out.println( key);
		      }
			
		}	
		else if (dataSource.equals("database")) {
			try {
				// filling User List

				String queryString = "select userId,userName,password,userType,firstName,LastName from BANK_USERS";
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(queryString);
				while (rs.next()) {
					User user = new User();
					String userId = rs.getString("userid");
					String userName = rs.getString("userName");
					String password = rs.getString("password");
					String userType = rs.getString("userType");
					String firstName = rs.getString("firstName");
					String lastName = rs.getString("lastName");
					user.setUserId(userId);
					user.setUserName(userName);
					user.setPassword(password);
					user.setUserType(userType);
					user.setFirstName(firstName);
					user.setLastName(lastName);

					userList.put(userId, user);
				}

				// filling account list

				String accountString = "select userId,accnumber,acctype,balance from Account";
				ResultSet rs1 = st.executeQuery(accountString);
				while (rs1.next()) {
					Account acc = new Account();
					String userId = rs1.getString("userid");
					String accnumber = rs1.getString("accnumber");
					String acctype = rs1.getString("acctype");
					int balance = rs1.getInt("balance");
					acc.setUserId(userId);
					acc.setAccNumber(accnumber);
					acc.setAccType(acctype);
					acc.setBalance(balance);

					accList.put(userId, acc);
				}
				// filling trans list

				String transString = "select userId,transactiontype,transactiontime,amount from Trans";
				ResultSet rs2 = st.executeQuery(transString);
				while (rs2.next()) {
					Transaction trans = new Transaction();
					String userId = rs2.getString("userid");
					String transactionType = rs2.getString("transactiontype");
					java.util.Date transactionTime = new java.util.Date(rs2.getString("transactiontime"));
					int amount = rs2.getInt("amount");

					trans.setUserId(userId);
					trans.setTransactionTime(transactionTime);
					trans.setTransactionType(transactionType);
					trans.setAmount(amount);

					transList.put(userId, trans);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}

	}

	// Method to write the data into the file called user.txt
	private synchronized void writeIntoFile(String str) {
		char buf[] = new char[str.length()];
		str.getChars(0, str.length(), buf, 0);
		File file = new File("user.txt");
		FileWriter fw = null;

		try {
			fw = new FileWriter(file, true);
			String newLine = System.getProperty("line.separator");
			fw.write(newLine);
			for (int i = 0; i < buf.length; i++) {
				fw.write(buf[i]);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public synchronized void writeIntoDatabase(String queryString) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(queryString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public HashMap<String, User> getUserList() {
		return userList;
	}

	public HashMap<String, Account> getAccountList() {
		return accList;
	}

	public HashMap<String, Transaction> getTransList() {
		return transList;
	}

	public boolean populateTransactionDetails(String userId, Account acc, Transaction trans) {
	
		if (accList != null)
			accList.put(userId, acc);

		if (transList != null) {
			transList.put(userId, trans);
		}
		if (dataSource.equals("database")) {
			try {
				String insertString1 = "update account set balance=" + acc.getBalance() + "where userId=" + userId;
				writeIntoDatabase(insertString1);

				String insertString2 = "INSERT INTO trans(UserId,transactiontype,transactiontime,amount) values ('";
				String endString2 = ")";
				String queryString2 = insertString2 + userId + "','" + trans.getTransactionType() + "','"
						+ trans.getTransactionTime() + "'," + trans.getAmount() + endString2;
				writeIntoDatabase(queryString2);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean populateCustomerList(String userId, User user, Account acc) {
		if (userList != null) {
			userList.put(userId, user);
		
			if (accList != null)
				accList.put(userId, acc);

			// if data source is the file , then write into the file
			if (dataSource.equals("File")) {
				writeIntoFile(user.getUserId() + ":" + user.getUserName() + ":" + user.getPassword() + ":"
						+ user.getUserType() + ":" + user.getFirstName() + ":" + user.getLastName() + "\n");
			}
			if (dataSource.equals("database")) {
				String insertString = "INSERT INTO Bank_users(UserID,username,password,usertype,FirstName,LastName) values ('";
				String endString = "')";
				String queryString = insertString + user.getUserId() + "','" + user.getUserName() + "','"
						+ user.getPassword() + "','" + user.getUserType() + "','" + user.getFirstName() + "','"
						+ user.getLastName() + endString;
				// System.out.println(queryString);
				writeIntoDatabase(queryString);

				String insertString1 = "INSERT INTO Account(UserId,accNumber,acctype,balance) values ('";
				String endString1 = ")";
				String queryString1 = insertString1 + acc.getUserId() + "','" + acc.getAccNumber() + "','"
						+ acc.getAccType() + "'," + acc.getBalance() + endString1;
				writeIntoDatabase(queryString1);

			}
			return true;
		}
		return false;
	}

	public boolean removeCustomer(String userId, String UserName) {
		if (userList != null) {
			userList.remove(userId);
			if (accList != null)
				accList.remove(userId);
				if (transList != null)
				transList.remove(userId);
				String s1 = "delete from bank_users where userid=" + userId;
				String s2 = "delete from Account where userid=" + userId;
				String s3 = "delete from Trans where userid=" + userId;
				try {
				// System.out.println("connected to "+con.getSchema());
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(s1);
					ResultSet rs1 = st.executeQuery(s2);
					ResultSet rs2 = st.executeQuery(s3);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return true;
		}
		return false;
	}

	public void printEmployeeList() {
		System.out.println("\n\tEmployee details..\n");
		for (String i : userList.keySet()) {
			User user = userList.get(i);
			String userType = user.getUserType();
			if (userType.equals("EmployeeManager") || userType.equals("EmployeeTeller")) {
				System.out.println(user.toString());
			}
		}
	}

	public void printCustomerList() {
		userList = getUserList();
		accList = getAccountList();

		System.out.println(" \n\tCustomer Details .. \n");
		for (String i : userList.keySet()) {
			User user = userList.get(i);
			Account acc = accList.get(user.getUserId());
			String userType = user.getUserType();
			if ((!userType.equals("EmployeeManager")) && !userType.equals("EmployeeTeller")) {
				System.out.println(user.toString());
				System.out.println("Account Details for the user id : " + user.getUserId() + "" + acc.toString());
			}
		}

		return;
	}

}