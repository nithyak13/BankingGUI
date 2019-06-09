package com.java.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
public class BankLoginServlet extends HttpServlet {


public void doGet(HttpServletRequest request, HttpServletResponse response) 
			           throws ServletException, java.io.IOException {

try
{	    

     UserBean user = new UserBean();
     String userName =request.getParameter("un"); 
     user.setUserName(userName);
     String password=request.getParameter("pw");
     user.setPassword(password);

     System.out.println("UserName :"+ userName);
     System.out.println("password :"+ password);
	   		    
     if (userName.equals("Nithya") && password.equals("welcome") )
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
	}