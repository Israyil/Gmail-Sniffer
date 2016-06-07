package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import database.MongoManager;
import model.Ogrenci;

public class MailCheck extends Thread {

	private String username = "johnlennondabizden@gmail.com";
	private String password;
	private static int group_no = 1;

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getInstance(props, null);

		try {
			

			javax.mail.Store store = session.getStore();
			((Service) store).connect("imap.gmail.com", username, getPassword());
			while (true) {
				MongoManager.connect();
				Thread.sleep(10000);//Uygulamayý test etmek amacýyla 10 saniye bekletilmiþtir thread.
				try {
					java.util.Date lastTwoHoursDate = new java.util.Date();
					lastTwoHoursDate.setHours(lastTwoHoursDate.getHours() - 2);
					Folder inbox = store.getFolder("INBOX");
					inbox.open(Folder.READ_WRITE);
					List<Message> unReadMessages = new ArrayList<Message>();

					for (int i = inbox.getMessageCount(); i >= 0; i--) {
						try {

							if (inbox.getMessage(i).getSentDate().after(lastTwoHoursDate)
									&& !inbox.getMessage(i).getFlags().contains(Flags.Flag.SEEN)
									&& isProjectMail(inbox.getMessage(i))) {//burda alýnan mesajýn son 2 saat içinde geldiði,mesajýn okunmamýþ olmasý gerektiði ve proje maili olup olmadýðýnýn kontrolü yapýlmaktadýr.

								unReadMessages.add(inbox.getMessage(i));//Ýlgili mail listeye alýnýr 
								inbox.getMessage(i).setFlag(Flags.Flag.SEEN, true);//Alýnan mail görüldü yapýlýrki tekrar listeye alýnmasý diye.
							} else
								break;
						} catch (Exception e) {
							continue;
						}
					}
					for (Message m : unReadMessages) {
						try {

							List<Ogrenci> list = extractStudents(m);
							//mesajlardaki öðrenciler parse edilerek düzgün formattda öðrenci listesine alýnýr. 
							for (Ogrenci i : list) {
								MongoManager.insertStudent(i.getGroup_no(), i.getNumber(), i.getNameSurname(),
										i.getNote());
									//Parse edilen öðrenciler veritabýna kaydedilir.
							}
							Address[] addresses = m.getFrom();
							//Gelen mailler kimden gelmiþ onlar alýnýr.
							for (Address a : addresses) {
								sendAckKnowledgeMessage(extractSender(a.toString()));//Kimden geldiði alýnan kiþilere mesajýnýz bize ulaþmýþtýr diye geri mail atýlýr.
							}

						} catch (Exception e) {
							continue;
						}
					}

				} catch (NoSuchProviderException e) {

					e.printStackTrace();
				} catch (MessagingException e) {

					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isProjectMail(Message msg) throws IOException, MessagingException {
		//Bu metod bize proje ile ilgili mail olup olamadýðý döndürür.
		Multipart mp = (Multipart) msg.getContent();
		BodyPart bp = mp.getBodyPart(0);
		String content = bp.getContent().toString();//Content olarak bize tüm metin gelir.
		String[] array = content.split("\r\n");
		if ((bp.getContent().toString().contains("Syt") || bp.getContent().toString().contains("SYT"))
				&& (bp.getContent().toString().contains("2.proje") || bp.getContent().toString().contains("2. proje")))
			return true;
		else
			return false;
	}

	private String extractSender(String str) {
		return str.substring(str.indexOf("<") + 1, str.indexOf(">"));
		//Bu metod bize mailin kimden geldiðini döndürür.
	}

	private void sendAckKnowledgeMessage(String receiver) {
		//Bu metod bize atan kiþilere geri mail atmaya yarar.
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				try {
					return new javax.mail.PasswordAuthentication(username, getPassword());
				} catch (Exception e) {
					e.printStackTrace();
					return null;

				}
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject("Syt Proje");
			message.setText("Information has reached us \nThank you..");
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Ogrenci> extractStudents(Message msg) throws MessagingException, IOException {
		List<Ogrenci> list = new ArrayList<Ogrenci>();
		Ogrenci ogrenci;
		Address[] in = msg.getFrom();
		Multipart mp = (Multipart) msg.getContent();
		BodyPart bp = mp.getBodyPart(0);

		String content = bp.getContent().toString();
		String[] array = content.split("\r\n");
		for (int i = 2; i < array.length; i++) {
			String number = array[i].split("-")[0].trim();
			String nameSurname = array[i].split("-")[1].trim();
			ogrenci = new Ogrenci(String.valueOf(group_no), number, nameSurname, "0");
			list.add(ogrenci);
		}
		group_no++;
		//Bu metod bize gelen maili parse ederek özðrencileri düzgün formata sokarak bize bir öðrenci listesi döndürür.
		return list;

	}

	public String getPassword() throws Exception {
		//Veritabanýnda bulunan þifre decrypt edilerek döndürülür.
		MongoManager.connect();
		return Encryption.decrypt(MongoManager.adminPasswordControl(username));

	}

}