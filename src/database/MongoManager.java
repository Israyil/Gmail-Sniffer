package database;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

import model.Ogrenci;
import model.OgrenciList;

public class MongoManager {
	private static MongoClient mongoClient;
	private static DB database;
	private static DBCollection dbcol;

	public static void readAllStudents() throws UnknownHostException {
		OgrenciList.clearList();
		dbcol = database.getCollection("students");//database de students adýnda bir collection varsa onu alýr yoksa yeni yaratýr.

		DBCursor result = dbcol.find(); //seçilen kolondaki kayýtlarý alýr.
		while (result.hasNext()) {//iterative olarak tüm kayýtlar gezilir.
			DBObject obj = result.next();
			OgrenciList.AddOgrenciList(new Ogrenci((String) obj.get("group_no"), (String) obj.get("number"),
					(String) obj.get("nameSurname"), (String) obj.get("note")));

		}

	}

	public static void connect() throws UnknownHostException {
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDB("syt_proje");//database olarak syt_proje seçilmiþtir.
	}

	public static void insertStudent(String group_no, String number, String nameSurname, String note) {
		dbcol = database.getCollection("students");
		BasicDBObject obj1 = new BasicDBObject().append("group_no", group_no).append("number", number)
				.append("nameSurname", nameSurname).append("note", note);
		dbcol.insert(obj1, WriteConcern.JOURNAL_SAFE);//oluþturduðumuz basic objeyi güvenli bir þekilde students collectiona kaydeder.
	}

	public static void deleteStudent(String number) {
		dbcol = database.getCollection("students");
		BasicDBObject document = new BasicDBObject();
		document.put("number", number);
		dbcol.remove(document);//students collection da bu numaraya ait olan kayýtlarý siler.
	}

	public static void updateStudent(String group_no, String number, String nameSurname, String note,
			String oldNumber) {
		dbcol = database.getCollection("students");
		BasicDBObject obj1 = new BasicDBObject().append("group_no", group_no).append("number", number)
				.append("nameSurname", nameSurname).append("note", note);
		BasicDBObject whereQuery = new BasicDBObject();

		whereQuery.put("number", oldNumber);
		DBObject result = dbcol.findOne(whereQuery);//bu numaraya kayýtlý olan ilk kaydý getirir. Zaten öðrenci numarasý unique olacaktýr.

		DBObject updateObject = new BasicDBObject();
		if (!group_no.isEmpty())
			((BasicDBObject) updateObject).append("group_no", group_no);
		if (!number.isEmpty())
			((BasicDBObject) updateObject).append("number", number);
		if (!nameSurname.isEmpty())
			((BasicDBObject) updateObject).append("nameSurname", nameSurname);
		if (!note.isEmpty())
			((BasicDBObject) updateObject).append("note", note);
		dbcol.update(result, new BasicDBObject("$set", updateObject));

	}

	public static String adminPasswordControl(String username) {//bu metod admin ayný database içinde ki admin collectionda kayýtlý olan þifreyi döndürür.
		dbcol = database.getCollection("admin");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("username",username);
		DBObject result = dbcol.findOne(whereQuery);
		return (String) result.get("password");
	}
	

}
