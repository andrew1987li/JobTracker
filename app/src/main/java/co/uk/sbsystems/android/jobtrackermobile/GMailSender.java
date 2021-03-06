package co.uk.sbsystems.android.jobtrackermobile;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;   
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;   
import javax.mail.Session;   
import javax.mail.Transport;   
import javax.mail.internet.InternetAddress;   
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;   
import javax.mail.internet.MimeMultipart;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.io.ByteArrayInputStream;   
import java.io.File;
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;   
import java.security.Security;   
import java.util.Properties;


public class GMailSender extends javax.mail.Authenticator {   
    private String mailhost = "smtp.gmail.com";   
    //private String mailhost = "mail.sbsystems.co.uk";   
    private String user;   
    private String password;   
    private Session session;   

    static {   
    	try {
            Security.addProvider(new com.provider.JSSEProvider());
		} catch (Exception e) {
			Log.i("GMailSender 1", e.getMessage());
		}
       
    }  

    public GMailSender(String user, String password) {   
        this.user = user;   
        this.password = password;   

        try {
            Properties props = new Properties();   
            props.setProperty("mail.transport.protocol", "smtp");   
            props.setProperty("mail.host", mailhost);   
            props.put("mail.smtp.auth", "true");   
            props.put("mail.smtp.port", "465");   //465
            props.put("mail.smtp.socketFactory.port", "465"); //465   
            props.put("mail.smtp.socketFactory.class",   
                    "javax.net.ssl.SSLSocketFactory");   
            props.put("mail.smtp.socketFactory.fallback", "false");   
            props.setProperty("mail.smtp.quitwait", "false");   

            session = Session.getDefaultInstance(props, this);   
			
		} catch (Exception e) {
			Log.i("GMailSender 2", e.getMessage());
		}
        
    }   

    protected PasswordAuthentication getPasswordAuthentication() {   
        return new PasswordAuthentication(user, password);   
    }   

   /* 
    public synchronized void sendMail(String subject, String body, String sender, String recipients,File attachment ) throws Exception {   
        try{
        MimeMessage message = new MimeMessage(session);   
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));   
        message.setSender(new InternetAddress(sender));   
        message.setSubject(subject);   
        message.setDataHandler(handler);   
        if (recipients.indexOf(',') > 0)   
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));   
        else  
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));   
        Transport.send(message);
        
        }catch(Exception e){
        	Log.i("sendMail",e.getMessage());
        }
    }   
    
   */
    
    
    public synchronized int sendMail(String subject, String body, String sender, String recipients, File attachment) throws Exception {
        try{
	        MimeMessage message = new MimeMessage(session);
	        message.setSender(new InternetAddress(sender));
	        message.setSubject(subject);
	
	        MimeBodyPart mbp1 = new MimeBodyPart();
	        mbp1.setText(body);
	
	        MimeBodyPart mbp2 = new MimeBodyPart();
	        if (attachment != null) {
		        FileDataSource fds = new FileDataSource(attachment);
		        mbp2.setDataHandler(new DataHandler(fds));
		        mbp2.setFileName(fds.getName());
	        }
	
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(mbp1);
	        
	        if (attachment != null){
	        	mp.addBodyPart(mbp2);
	        }
	
	        message.setContent(mp);
	
	        if (recipients.indexOf(',') > 0)
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
	        else
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
	        Transport.send(message);
	        return 1;
        }catch(Exception e){
        	Log.i("sendMail",e.getMessage());
        	return 0;
        }
    }    
    
    
   

    public class ByteArrayDataSource implements DataSource {   
        private byte[] data;   
        private String type;   

        public ByteArrayDataSource(byte[] data, String type) {   
            super();   
            this.data = data;   
            this.type = type;   
        }   

        public ByteArrayDataSource(byte[] data) {   
            super();   
            this.data = data;   
        }   

        public void setType(String type) {   
            this.type = type;   
        }   

        public String getContentType() {   
            if (type == null)   
                return "application/octet-stream";   
            else  
                return type;   
        }   

        public InputStream getInputStream() throws IOException {   
            return new ByteArrayInputStream(data);   
        }   

        public String getName() {   
            return "ByteArrayDataSource";   
        }   

        public OutputStream getOutputStream() throws IOException {   
            throw new IOException("Not Supported");   
        }   
    }   
} 