package com.ivan.exchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
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

import org.apache.commons.lang3.StringEscapeUtils;

import sun.misc.BASE64Decoder;

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
	private ArrayList<PSTMessage> commands = new ArrayList<PSTMessage>();
	private String gmail;
	private String password;
	public static final String EMAIL_LOG_FILE = "email.log";
	public static final String PDF_TMP_FOLDER = "tmp";
	public static final String EMAIL_COMMAND_SUBJECT_CONTAIN = "[Email notification from HP inbox]";
	public static boolean debug = false;
	public static boolean tryPDF = false;

	public static void main(String[] args) {
		if(args.length != 3)
		{
			System.out.println("Usage: ExchangeClient ?gmail ?pwd ?outlookfile");
			return;
		}
		
		ExchangeClient client = new ExchangeClient();
		try{
			BASE64Decoder decoder = new BASE64Decoder();
			
			String encryptedPwd = args[1];
			
			String password = new String(decoder.decodeBuffer(encryptedPwd), "utf-8");
			
			client.setPassword(password);
			
			client.setGmail(args[0]);
			
			System.out.println("Start at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			ArrayList<PSTMessage> unreadEmails = client.readOst(args[2], true);
			
			//Process fwd command, only unread email will be processed, skipped
			//client.processCommand(unreadEmails);
			
			String existingUnread = "0";
			String[] existingUnreadId = null;
			if(new File(EMAIL_LOG_FILE).exists()){
				existingUnread = client.readText(EMAIL_LOG_FILE);
				existingUnreadId = existingUnread.split(",");
			}
			
			String newUnread = "" + unreadEmails.size();
			String[] newUnreadid = new String[unreadEmails.size()];
			for(int i = 0; i < unreadEmails.size(); i++)
				newUnreadid[i] = "" + unreadEmails.get(i).getInternetArticleNumber();
				
			//Change compare method
			//if((!newUnread.equals(existingUnread) && unreadEmails.size() > 0) || debug)
			if((!Arrays.equals(existingUnreadId, newUnreadid) && unreadEmails.size() > 0) || debug)
			{
				System.out.println("New email arrived.");
				client.sendToGmail(unreadEmails);
			}else{
				System.out.println("No new email found.");
			}
			
			FileOutputStream fos = new FileOutputStream(EMAIL_LOG_FILE);
			for(int i = 0 ; i < newUnreadid.length; i++){
				fos.write(newUnreadid[i].getBytes());
				if(i < newUnreadid.length - 1)
					fos.write(",".getBytes());
			}
			fos.close();
			System.out.println("Email log updated.");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<PSTMessage> readOst(String file, boolean showUnread){
		try {
			this.messages = new ArrayList<PSTMessage>();
			this.commands = new ArrayList<PSTMessage>();
			
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
                    System.out.println(folder.getDisplayName());
            }
            

            // go through the folders...
            if (folder.hasSubfolders()) {
                    Vector<PSTFolder> childFolders = folder.getSubFolders();
                    for (PSTFolder childFolder : childFolders) {
                    	if(childFolder.getDisplayName().equals("Root - Mailbox") || childFolder.getDisplayName().equals("IPM_SUBTREE") || childFolder.getDisplayName().equals("收件匣") || childFolder.getDisplayName().equals("Inbox"))
                            processFolder(childFolder, showUnread);
                    }
            }

            // and now the emails for this folder
            if (folder.getContentCount() > 0) {
                    depth++;
                    PSTMessage email = (PSTMessage)folder.getNextChild();
                    while (email != null) {
                    	
                    	if(email.getSubject().indexOf(EMAIL_COMMAND_SUBJECT_CONTAIN) >= 0)
                    	{
                    		commands.add(email);
                    	}
                    	else{
                        	if(showUnread)
                        	{
                        		if(!email.isRead())
                        		{
                        			//printDepth();
                        			System.out.println("Email: (" + email.getInternetArticleNumber() + ")" + email.getSubject());
                        			//System.out.println("Content:");
                        			//System.out.println(email.getBodyHTML());
                        			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        			//System.out.println(sdf.format(email.getClientSubmitTime()));
                        			messages.add(email);
                        			
                        			//sendToGmail(email.getSubject(), email.getBodyHTML());
                        		}
                        	}else
                        	{
                        		//printDepth();
                    			System.out.println("Email: " + email.getSubject());
                    			messages.add(email);
                        	}
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
    
    public void processCommand(ArrayList<PSTMessage> unreadMsgs){
    	if(!debug)
    		return;
    	
    	System.out.println("Processing email command");
    	if(commands.size() == 0)
    	{
    		System.out.println("No email command");
    		return;
    	}
    	
    	ArrayList<String> notedEmails = new ArrayList<String>();
    	ArrayList<String> followedEmails = new ArrayList<String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    	//Prepare command list
    	for(int i = 0; i < commands.size(); i++){
    		System.out.println("Found command:");
    		String commandStr = StringEscapeUtils.unescapeHtml3(commands.get(i).getBodyHTML());
    		//System.out.println(commandStr);
    		
    		String emailsStr = "";
    		
    		String notedAction = "";
    		String followedAction = "";
    		
    		int endIdx = commandStr.indexOf("<End>");
    		int beginIdx = commandStr.indexOf("<Begin>");
    		
    		int notedIdx = commandStr.indexOf("Noted");
    		int followedIdx = commandStr.indexOf("Followed");
    		
    		if(endIdx > beginIdx && beginIdx > 0)
    		{
    			emailsStr = commandStr.substring(beginIdx, endIdx).replace("<Begin>\n", "").replace("\r", "").replace("<br>\n", "\n");
    			//System.out.println(emailsStr);
    			
    			//Get noted email
    			if(notedIdx > 0 && notedIdx < beginIdx){
    				notedAction = commandStr.substring(notedIdx);
    				notedAction = notedAction.substring(0, notedAction.indexOf("<"));
    				
    				System.out.println("Noted Action: " + notedAction);
    				
    				String[] notedEmail = notedAction.replace("Noted", "").trim().split(",");
    				
    				if(notedAction.trim().equalsIgnoreCase("noted all")){
    					notedEmail = emailsStr.split("\n");
    					notedEmails.addAll(Arrays.asList(notedEmail));
    				}
    				else{
    					for(int j = 0 ; j < notedEmail.length; j++){
        					if(emailsStr.indexOf("[" + notedEmail[j].trim() + "][") >= 0){
        						String val = emailsStr.substring(emailsStr.indexOf("[" + notedEmail[j].trim() + "]["), emailsStr.indexOf("\n", emailsStr.indexOf("[" + notedEmail[j].trim() + "]["))).trim();
        						notedEmails.add(val);
            					System.out.println(val);
        					}
        				}
    				}
    			}
    			
    			//Get followed email
    			if(followedIdx > 0 && followedIdx < beginIdx){
    				followedAction = commandStr.substring(followedIdx);
    				followedAction = followedAction.substring(0, followedAction.indexOf("<"));
    				
    				System.out.println("Followed Action: " + followedAction);
    				
    				String[] followedEmail = followedAction.replace("Followed", "").trim().split(",");
    				if(followedAction.trim().equalsIgnoreCase("followed all")){
    					followedEmail = emailsStr.split("\n");
    					followedEmails.addAll(Arrays.asList(followedEmail));
    				}
    				else{
    					for(int j = 0 ; j < followedEmail.length; j++){
        					if(emailsStr.indexOf("[" + followedEmail[j].trim() + "][") >= 0){
        						String val = emailsStr.substring(emailsStr.indexOf("[" + followedEmail[j].trim() + "]["), emailsStr.indexOf("\n", emailsStr.indexOf("[" + followedEmail[j].trim() + "]["))).trim();
        						followedEmails.add(val);
            					System.out.println(val);
        					}
        				}
    				}
    			}
    		}
    	}
    	//End preparing
    	
    	//Process noted email
    	for(int i = 0; i < unreadMsgs.size(); i++){
    		
    		//System.out.println(unreadMsgs.get(i).getDescriptorNodeId());
    		
			for(int j = 0; j < notedEmails.size(); j++){
				//Process noted
				if(notedEmails.get(j).indexOf("[" + sdf.format(unreadMsgs.get(i).getClientSubmitTime()) + "][" + unreadMsgs.get(i).getSubject() + "]") > 0){
					com.pff.PSTObject obj = (com.pff.PSTObject) unreadMsgs.get(i);
					
					//How to? this part moved to Macro of outlook
				}
			}
		}
    }
    
    public void sendToGmail(ArrayList<PSTMessage> messages) throws DocumentException, IOException{
    	
    	String subject = "[Email notification from HP inbox]";
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
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
	    	
	    	
	    	String text = "Belows are the email subjects and attached are the detail(s)" + "\n\n";
	    	
	    	text += "<Begin>\n";
	    	
	    	ArrayList<MimeBodyPart> toBeAddedPart = new ArrayList<MimeBodyPart>();
			for(int i = 0; i < messages.size(); i++){
				File pdfFile = new File(PDF_TMP_FOLDER + "/" + getA_ZCharacter(messages.get(i).getSubject()) + ".pdf");
				try{
					int c = 1;
					while(pdfFile.exists()){
						pdfFile = new File(PDF_TMP_FOLDER + "/" + getA_ZCharacter(messages.get(i).getSubject()) + "_" + c + ".pdf");
						c++;
					}
					
					Document doc = new Document(PageSize.A4);
					PdfWriter.getInstance(doc, new FileOutputStream(pdfFile));
					doc.open();
					doc.add(new Paragraph("[" + sdf.format(messages.get(i).getClientSubmitTime()) + "][" + messages.get(i).getSubject() + "]")); 
					HTMLWorker hw = new HTMLWorker(doc);
					
					text += "[" + (i+1)+ "]" + "[" + sdf.format(messages.get(i).getClientSubmitTime()) + "][" + getA_ZCharacter(messages.get(i).getSubject()) + "]\n";
					//boolean tryPDF = false;
					try{
						if(tryPDF){
							hw.parse(new StringReader(messages.get(i).getBodyHTML()));
							doc.close();
							
							MimeBodyPart mbp2 = new MimeBodyPart();
				            FileDataSource fds = new FileDataSource(pdfFile);
			                mbp2.setDataHandler(new DataHandler(fds));
			                mbp2.setFileName(fds.getName());
			                toBeAddedPart.add(mbp2);
						} else{
							FileOutputStream htmlOut = new FileOutputStream(pdfFile.getAbsolutePath().replace(".pdf", ".html"));
							OutputStreamWriter sw = new OutputStreamWriter(htmlOut, "utf-8");
							sw.write(messages.get(i).getBodyHTML());
						    //htmlOut.write(messages.get(i).getBodyHTML().getBytes());
							sw.close();
						    htmlOut.close();
						    doc.close();
						    
						    MimeBodyPart mbp2 = new MimeBodyPart();
				            FileDataSource fds = new FileDataSource(new File(pdfFile.getAbsolutePath().replace(".pdf", ".html")));
			                mbp2.setDataHandler(new DataHandler(fds));
			                mbp2.setFileName(fds.getName());
			                toBeAddedPart.add(mbp2);
						}
					}catch(Exception e){//Use HTML in exception
						FileOutputStream htmlOut = new FileOutputStream(pdfFile.getAbsolutePath().replace(".pdf", ".html"));
						OutputStreamWriter sw = new OutputStreamWriter(htmlOut, "utf-8");
						sw.write(messages.get(i).getBodyHTML());
					    //htmlOut.write(messages.get(i).getBodyHTML().getBytes());
						sw.close();
					    htmlOut.close();
					    doc.close();
					    
					    MimeBodyPart mbp2 = new MimeBodyPart();
			            FileDataSource fds = new FileDataSource(new File(pdfFile.getAbsolutePath().replace(".pdf", ".html")));
		                mbp2.setDataHandler(new DataHandler(fds));
		                mbp2.setFileName(fds.getName());
		                toBeAddedPart.add(mbp2);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Error:");
					System.out.println(e.getMessage() + ": " + pdfFile.getName());
				}
			}
			
			text += "<End>\n\n";
			text += "If you want to send commands for processing the below email(s), please forward this original message to your Exchange account with command:\n";
			text += "Noted {index of your email separated by comma}/{all}\n";
			text += "Followed {index of your email separated by comma}/{all}\n";
			text += "If the processing emails have been read or removed from inbox, command will be skipped\n\nThanks";
			
			textPart.setText(text);
			message.setContent(multiPart);
			multiPart.addBodyPart(textPart);
			
			for(int i = 0; i < toBeAddedPart.size(); i++){
				multiPart.addBodyPart(toBeAddedPart.get(i));
			}
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
			if((val.charAt(i) <= '9' && val.charAt(i) >= '0') || (val.charAt(i) <= 'z' && val.charAt(i) >= 'a') || (val.charAt(i) <= 'Z' && val.charAt(i) >= 'A') || val.charAt(i) == ' '){
				res += val.charAt(i);
			}
			else{
				res += ' ';
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
