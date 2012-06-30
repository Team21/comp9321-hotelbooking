/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.activation.*;

public class CmdSendEmail implements Command
{

	/* (non-Javadoc)
	 * @see assn2.web.Command#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
//   public static void main(String [] args)
//   {
//      
//      // Recipient's email ID needs to be mentioned.
//      String to = "zhuangdeyouxian@gmail.com";
//
//      // Sender's email ID needs to be mentioned
//      String from = "yourAddress@gmail.com";
//
//      // Assuming you are sending email from localhost
////      String host = "smtp";
//
//      // Get system properties
//      Properties properties = System.getProperties();
//      //for outside campus
////      Properties properties = new Properties(); 
////      properties.put("mail.smtp.auth", "true"); 
////      properties.put("mail.smtp.starttls.enable", "true");
////      properties.put("mail.smtp.host", "smtp.gmail.com"); 
////      properties.put("mail.smtp.port", "587");
//
//      //for cse lab machine
//      properties.setProperty("mail.smtp.host", "smtp.cse.unsw.edu.au");
//      properties.setProperty("mail.smtp.port", "465");
//
//      // Get the default Session object.
//      Session session = Session.getDefaultInstance(properties);
//
//      try{
//         // Create a default MimeMessage object.
//         MimeMessage message = new MimeMessage(session);
//
//         // Set From: header field of the header.
//         message.setFrom(new InternetAddress(from));
//
//         // Set To: header field of the header.
//         message.addRecipient(Message.RecipientType.TO,
//                                  new InternetAddress(to));
//
//         // Set Subject: header field
//         message.setSubject("This is the Subject Line!");
//
//         // Now set the actual message
//         message.setText("This is actual message");
//
//         // Send message
//         Transport.send(message);
//         System.out.println("Sent message successfully....");
//      }catch (MessagingException mex) {
//         mex.printStackTrace();
//      }
//   }
}