/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import assn2.daosImpl.PinDAOImpl;

/**
 * @author ASUS
 *
 */
public class CmdPin implements Command {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//table PIN is mapping URL <--> PIN <--CHECKINDATE
			//get from url in brownser
			int Crypto = Integer.parseInt(request.getParameter("Crypto"));
			PinDAOImpl pindao = new PinDAOImpl();
			//search it's PIN
			int PIN;
			try{
				PIN = pindao.getPin(Crypto);
			}catch(Exception e){
				e.printStackTrace(); //URL does not exist in database, it's fake
				return "/outdated.jsp";
			}
			//save in session
			request.getSession().setAttribute("ePIN", PIN);
			Timestamp checkindate = pindao.getCheckingdate(PIN);
			System.out.println(checkindate);
			
			Date d = new Date();
			long diff = checkindate.getTime() - d.getTime();//how many 1/1000 seconds
			System.out.println(d.getTime());
			if(diff < 0){  //after checkin date
				return "/outdated.jsp";
			}
			if(diff < 172800000){  //48 hour = 48 * 3600 * 1000 milliseconds
				return "/outdated.jsp";  //48 hours before check in date, cannot be viewed or changed any more
			}else{ //visit time is right, let user enter PIN and validate
				return "/getPIN.jsp";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "/pinfail.jsp";
		}
	}
}