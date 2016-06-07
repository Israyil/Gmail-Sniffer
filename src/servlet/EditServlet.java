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
		//database üzerinde MongoManager sýnýfý kullanýlarak güncelleme yapýlýr.
		MongoManager.readAllStudents();
		//Tüm kayýtlar çaðýrýlýr
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		//güncellediðimiz kaydý görüntülemek için index.jsp sayfasýna yönlendirilir.
	}

}
