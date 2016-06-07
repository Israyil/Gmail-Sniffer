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
				Thread.sleep(10000);//Uygulamay� test etmek amac�yla 10 saniye bekletilmi�tir thread.
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
									&& isProjectMail(inbox.getMessage(i))) {//burda al�nan mesaj�n son 2 saat i�inde geldi�i,mesaj�n okunmam�� olmas� gerekti�i ve proje maili olup olmad���n�n kontrol� yap�lmaktad�r.

								unReadMessages.add(inbox.getMessage(i));//�lgili mail listeye al�n�r 
								inbox.getMessage(i).setFlag(Flags.Flag.SEEN, true);//Al�nan mail g�r�ld� yap�l�rki tekrar listeye al�nmas� diye.
							} else
								break;
						} catch (Exception e) {
							continue;
						}
					}
					for (Message m : unReadMessages) {
						try {

							List<Ogrenci> list = extractStudents(m);
							//mesajlardaki ��renciler parse edilerek d�zg�n formattda ��renci listesine al�n�r. 
							for (Ogrenci i : list) {
								MongoManager.insertStudent(i.getGroup_no(), i.getNumber(), i.getNameSurname(),
										i.getNote());
									//Parse edilen ��renciler veritab�na kaydedilir.
							}
							Address[] addresses = m.getFrom();
							//Gelen mailler kimden gelmi� onlar al�n�r.
							for (Address a : addresses) {
								sendAckKnowledgeMessage(extractSender(a.toString()));//Kimden geldi�i al�nan ki�ilere mesaj�n�z bize ula�m��t�r diye geri mail at�l�r.
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
		//Bu metod bize proje ile ilgili mail olup olamad��� d�nd�r�r.
		Multipart mp = (Multipart) msg.getContent();
		BodyPart bp = mp.getBodyPart(0);
		String content = bp.getContent().toString();//Content olarak bize t�m metin gelir.
		String[] array = content.split("\r\n");
		if ((bp.getContent().toString().contains("Syt") || bp.getContent().toString().contains("SYT"))
				&& (bp.getContent().toString().contains("2.proje") || bp.getContent().toString().contains("2. proje")))
			return true;
		else
			return false;
	}

	private String extractSender(String str) {
		return str.substring(str.indexOf("<") + 1, str.indexOf(">"));
		//Bu metod bize mailin kimden geldi�ini d�nd�r�r.
	}

	private void sendAckKnowledgeMessage(String receiver) {
		//Bu metod bize atan ki�ilere geri mail atmaya yarar.
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
		//Bu metod bize gelen maili parse ederek �z�rencileri d�zg�n formata sokarak bize bir ��renci listesi d�nd�r�r.
		return list;

	}

	public String getPassword() throws Exception {
		//Veritaban�nda bulunan �ifre decrypt edilerek d�nd�r�l�r.
		MongoManager.connect();
		return Encryption.decrypt(MongoManager.adminPasswordControl(username));

	}

}