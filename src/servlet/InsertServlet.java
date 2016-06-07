package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import database.MongoManager;
import model.Ogrenci;
import model.OgrenciList;

@WebServlet(name="InsertServlet",urlPatterns={"/InsertServlet"})
public class InsertServlet extends HttpServlet {
	
	private static MongoClient mongoClient;
	private static DB database;
	private static DBCollection dbcol;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		MongoManager.insertStudent(request.getParameter("group_no").toString(),request.getParameter("number").toString(),request.getParameter("nameSurname").toString(),request.getParameter("note").toString());
		//veritaban�na bir kay�t eklenir.
		MongoManager.readAllStudents();
		//T�m kay�tlar �a��r�l�r
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		//index.jsp sayfas�na y�nlendirilir.
		
	}

}
