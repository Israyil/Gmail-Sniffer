package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

import database.MongoManager;
import model.Ogrenci;
import model.OgrenciList;
import util.Encryption;
import util.MailCheck;

@WebServlet(name = "LoginServlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private PrintWriter out = null;
	private static MongoClient mongoClient;
	private static DB database;
	private static DBCollection dbcol;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=ISO-8859-1");
		resp.setCharacterEncoding("ISO-8859-1");

		MongoManager.connect();
		try {
			if (req.getParameter("username").toString().equals("johnlennondabizden@gmail.com")
					&& req.getParameter("password").toString().equals(Encryption
							.decrypt(MongoManager.adminPasswordControl(req.getParameter("username").toString())))) {
				//username ve password kontolü yapýlýr. Password kontrolü veritabýnda encrypte edilmiþ þifreyle karþýlatýrýlýr.
				MongoManager.readAllStudents();
				//Tüm kayýtlar çaðrýlýr.
				MailCheck m = new MailCheck();
				m.start();
				//kullanýcý doðru girdiði için bilgileri threadimiz baþlatýlýr yani mail çekme iþlemi baþlatýlýr.
				resp.sendRedirect(req.getContextPath() + "/index.jsp");
				//index.jsp sayfasýna yönlendirilir.
			} else {
				resp.sendRedirect(req.getContextPath() + "/login.jsp");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
