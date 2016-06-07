package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MongoManager;

@WebServlet(name="EditServlet",urlPatterns={"/EditServlet"})
public class EditServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		MongoManager.updateStudent(request.getParameter("group_no").toString(),request.getParameter("number").toString(),request.getParameter("nameSurname").toString(),request.getParameter("note").toString(),request.getParameter("oldNumber").toString());
		//database �zerinde MongoManager s�n�f� kullan�larak g�ncelleme yap�l�r.
		MongoManager.readAllStudents();
		//T�m kay�tlar �a��r�l�r
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		//g�ncelledi�imiz kayd� g�r�nt�lemek i�in index.jsp sayfas�na y�nlendirilir.
	}

}
