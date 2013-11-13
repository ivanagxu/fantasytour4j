package com.ivan.exchange;

import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import com.pff.PSTException;
import com.pff.PSTFile;
import com.pff.PSTFolder;
import com.pff.PSTMessage;

public class ExchangeClient {
	
	private ArrayList<PSTMessage> messages = new ArrayList<PSTMessage>();

	public static void main(String[] args) {
		ExchangeClient client = new ExchangeClient();
		try{
			//client.testConnect();
			client.readOst("C:\\Users\\Administrator\\AppData\\Local\\Microsoft\\Outlook\\outlook.ost", true);
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
                    			messages.add(email);
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
	
	public void testConnect() throws MessagingException{
		Properties props = System.getProperties();
		// Session configuration is done using properties. In this case, the IMAP port. All the rest are using defaults

		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props.setProperty("proxySet","true");
        props.setProperty("socksProxyHost","sync.atlanta.hp.com");
        props.setProperty("socksProxyPort","443");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "443");
        props.setProperty("mail.smtp.socketFactory.port", "443");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
		props.setProperty("mail.imap.port", "443");

		// creating the session to the mail server
		Session session = Session.getInstance(props, null);
		// Store is JavaMails name for the entity holding the mails
		Store store = session.getStore("imaps");
		// accessing the mail server using the domain user and password
		store.connect("casarray1.atlanta.hp.com", "ASIAPACIFIC\\ngd", "qwert12345!!");
		// retrieving the inbox folder
		Folder inbox = store.getFolder("INBOX");
		
		store.close();
		
		System.out.println(inbox.getName());
	}

}
