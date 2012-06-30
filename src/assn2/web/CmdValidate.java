/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import assn2.beans.UserBean;
import assn2.daosImpl.PinDAOImpl;
import assn2.daosImpl.RecordDAOImpl;
import assn2.daosImpl.UserDAOImpl;

/**
 * @author ASUS
 *
 */
public class CmdValidate implements Command{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tPIN = Integer.parseInt((String)request.getParameter("tPIN")); //from getPIN.jsp user input
		int ePIN = (Integer) request.getSession().getAttribute("ePIN");
		if(tPIN == ePIN){
			//search user security level
			PinDAOImpl pindao = new PinDAOImpl();
			int recordid = pindao.getRecordid(ePIN); //id of PIN 
			RecordDAOImpl rdao = new RecordDAOImpl();
			int userid = rdao.getUserid(recordid);
			UserDAOImpl udao = new UserDAOImpl();
			UserBean userbean = udao.get(userid);
			String security_level = userbean.getSecurity_level();
			//set attribute, because listbooking servlet need this
			request.getSession().setAttribute("security_level", security_level);
			request.getSession().setAttribute("user",userbean);
			return "listBooking";
		}else{
			return "/PINwrong.jsp";
		}
	}
}
