package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MongoManager;

@WebServlet(name="DeleteServlet",urlPatterns={"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		MongoManager.deleteStudent(request.getParameter("number").toString());//bu numaraya sahip kullanýcýyý MongoManager sýnýfýný kullanarak veritabanýndan siler.
		MongoManager.readAllStudents();//Sonra tüm kayýtlar veritabýndan çekilir.
		response.sendRedirect(request.getContextPath()+"/index.jsp");//Tekrar index.jsp sayfasýna yönlendirilir.
	}

}
