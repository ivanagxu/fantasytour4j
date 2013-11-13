package com.ivan.exchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;

public class ExchangeClient {
	
	private ArrayList<PSTMessage> messages = new ArrayList<PSTMessage>();
	private String gmail;
	private String password;
	public static final String EMAIL_LOG_FILE = "email.log";
	public static final String PDF_TMP_FOLDER = "tmp";

	public static void main(String[] args) {
		if(args.length != 3)
		{
			System.out.println("Usage: ExchangeClient ?gmail ?pwd ?outlookfile");
			return;
		}
		
		ExchangeClient client = new ExchangeClient();
		try{
			//client.testConnect();
			
			client.setPassword(args[1]);
			
			client.setGmail(args[0]);
			
			ArrayList<PSTMessage> unreadEmails = client.readOst(args[2], true);
			
			String existingUnread = "0";
			if(new File(EMAIL_LOG_FILE).exists()){
				existingUnread = client.readText(EMAIL_LOG_FILE);
			}
			
			String newUnread = "" + unreadEmails.size();
			
			if(!newUnread.equals(existingUnread))
			{
				System.out.println("New email arrived.");
				client.sendToGmail(unreadEmails);
				
				FileOutputStream fos = new FileOutputStream(EMAIL_LOG_FILE);
				fos.write(newUnread.getBytes());
				fos.close();
				System.out.println("Email log updated.");
			}else{
				System.out.println("No new email found.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<PSTMessage> readOst(String file, boolean showUnread){
		try {
			this.messages = new ArrayList<PSTMessage>();
			
            PSTFile pstFile = new PSTFile(file);
            //System.out.println(pstFile.getMessageStore().getDisplayName());
            processFolder(pstFile.getRootFolder(), showUnread);
            
	    } catch (Exception err) {
	            err.printStackTrace();
	    }
		return messages;
	}
	
	int depth = -1;
    public void processFolder(PSTFolder folder, boolean showUnread)
                    throws PSTException, java.io.IOException
    {
            depth++;
            // the root folder doesn't have a display name
            if (depth > 0) {
                    //printDepth();
            		//Print folder name
                    //System.out.println(folder.getDisplayName());
            }
            

            // go through the folders...
            if (folder.hasSubfolders()) {
                    Vector<PSTFolder> childFolders = folder.getSubFolders();
                    for (PSTFolder childFolder : childFolders) {
                    	if(childFolder.getDisplayName().equals("Root - Mailbox") || childFolder.getDisplayName().equals("IPM_SUBTREE") || childFolder.getDisplayName().equals("收件匣"))
                            processFolder(childFolder, showUnread);
                    }
            }

            // and now the emails for this folder
            if (folder.getContentCount() > 0) {
                    depth++;
                    PSTMessage email = (PSTMessage)folder.getNextChild();
                    while (email != null) {
                    	if(showUnread)
                    	{
                    		if(!email.isRead())
                    		{
                    			//printDepth();
                    			System.out.println("Email: " + email.getSubject());
                    			//System.out.println("Content:");
                    			//System.out.println(email.getBodyHTML());
                    			messages.add(email);
                    			
                    			//sendToGmail(email.getSubject(), email.getBodyHTML());
                    		}
                    	}else
                    	{
                    		//printDepth();
                			System.out.println("Email: " + email.getSubject());
                			messages.add(email);
                    	}
                        email = (PSTMessage)folder.getNextChild();
                    }
                    depth--;
            }
            depth--;
    }

    public void printDepth() {
            for (int x = 0; x < depth-1; x++) {
                    System.out.print(" | ");
            }
            System.out.print(" |- ");
    }
    
    public void sendToGmail(ArrayList<PSTMessage> messages) throws DocumentException, IOException{
    	
    	String subject = "Notification from HP email, " + messages.size() + " unread email";
    	
		
    	
    	Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(gmail,password);
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gmail));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(gmail));
			message.setSubject(subject);
			
			Multipart multiPart = new MimeMultipart();
			
			File[] tmpPDF = new File(PDF_TMP_FOLDER).listFiles();
			for(int i = 0 ; i < tmpPDF.length; i++){
				tmpPDF[i].delete();
			}
			
			MimeBodyPart textPart = new MimeBodyPart();
	    	textPart.setText("Attached are the email detail in PDF");
	    	multiPart.addBodyPart(textPart);
	
			for(int i = 0; i < messages.size(); i++){

				File pdfFile = new File(PDF_TMP_FOLDER + "/" + getA_ZCharacter(messages.get(i).getSubject()) + ".pdf");
				int c = 1;
				while(pdfFile.exists()){
					pdfFile = new File(PDF_TMP_FOLDER + "/" + getA_ZCharacter(messages.get(i).getSubject()) + "_" + c + ".pdf");
					c++;
				}
				
				Document doc = new Document(PageSize.A4);
				PdfWriter.getInstance(doc, new FileOutputStream(pdfFile));
				doc.open();
				doc.add(new Paragraph("[" + messages.get(i).getSubject() + "]")); 
				HTMLWorker hw = new HTMLWorker(doc);
				hw.parse(new StringReader(messages.get(i).getBodyHTML()));
				doc.close();
				
				MimeBodyPart mbp2 = new MimeBodyPart();
	            FileDataSource fds = new FileDataSource(pdfFile);
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());
                multiPart.addBodyPart(mbp2);
			}
			message.setContent(multiPart);
			//message.setText("Attached are the email detail in PDF");
			
			//message.setContent(contentHTML, "text/html; charset=utf-8");
 
			Transport.send(message);
			
			tmpPDF = new File(PDF_TMP_FOLDER).listFiles();
			for(int i = 0 ; i < tmpPDF.length; i++){
				tmpPDF[i].delete();
			}
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    
    
	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	private String readText(String path) throws FileNotFoundException {
		FileInputStream is = new FileInputStream(path);
	    StringBuilder sb = new StringBuilder(512);
	    try {
	        Reader r = new InputStreamReader(is, "UTF-8");
	        int c = 0;
	        while ((c = r.read()) != -1) {
	            sb.append((char) c);
	        }
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    return sb.toString();
	}
	
	private String getA_ZCharacter(String val){
		if(val == null)
			return "";
		String res = "";
		for(int i = 0; i < val.length(); i++){
			if((val.charAt(i) <= '9' && val.charAt(i) >= '0') || (val.charAt(i) <= 'z' && val.charAt(i) >= 'A') || val.charAt(i) == ' '){
				res += val.charAt(i);
			}
		}
		return res;
	}

	
//	public void testConnect() throws MessagingException{
//		Properties props = System.getProperties();
//		// Session configuration is done using properties. In this case, the IMAP port. All the rest are using defaults
//
//		
//		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//        props.setProperty("proxySet","true");
//        props.setProperty("socksProxyHost","sync.atlanta.hp.com");
//        props.setProperty("socksProxyPort","443");
//        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.port", "443");
//        props.setProperty("mail.smtp.socketFactory.port", "443");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.debug", "true");
//        props.put("mail.store.protocol", "pop3");
//        props.put("mail.transport.protocol", "smtp");
//		props.setProperty("mail.imap.port", "443");
//
//		// creating the session to the mail server
//		Session session = Session.getInstance(props, null);
//		// Store is JavaMails name for the entity holding the mails
//		Store store = session.getStore("imaps");
//		// accessing the mail server using the domain user and password
//		store.connect("casarray1.atlanta.hp.com", "ASIAPACIFIC\\ngd", "qwert12345!!");
//		// retrieving the inbox folder
//		Folder inbox = store.getFolder("INBOX");
//		
//		store.close();
//		
//		System.out.println(inbox.getName());
//	}

}
