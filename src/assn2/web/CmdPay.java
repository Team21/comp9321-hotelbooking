/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import assn2.beans.RecordBean;
import assn2.beans.UserBean;
import assn2.daosImpl.BookingDAOImpl;
import assn2.daosImpl.PinDAOImpl;
import assn2.daosImpl.RecordDAOImpl;
import assn2.daosImpl.RoomCalDAOImpl;
import assn2.daosImpl.UserDAOImpl;

public class CmdPay implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option = (String)(request.getParameter("option"));
		if(option.equals("cancel")){
			//empty booking cart
			request.getSession().invalidate();
			return "/index.jsp";
		}
		//roomtypeid, requireRoomAmount, extrabed
		HashMap<String,Integer> PINmap = new HashMap<String,Integer>();
		HashMap<String,Integer> URLmap = new HashMap<String,Integer>();
		List<Integer> amountList = (ArrayList<Integer>)(request.getSession().getAttribute("amountList"));
		
		//get user message
		List<RecordBean> recordlist = (ArrayList<RecordBean>) (request.getSession().getAttribute("recordlist"));
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String security_level = "user";
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String creditcard = request.getParameter("creditcard");
		
		if(username == null || password == null || address == null || email == null || fname == null || lname == null){
			return "/checkout.jsp"; //
		}
		//check email address
		String emailregex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
		Pattern pattern = Pattern.compile(emailregex);
		Matcher m = pattern.matcher(email);
		if( ! m.find()){
			return "/checkout.jsp";
		}
		//check number field
		//credit card
		String creditregex = "^[0-9]+$";
		Pattern creditpattern = Pattern.compile(creditregex);
		Matcher cm = creditpattern.matcher(creditcard);
		if( ! cm.find()){
			return "/checkout.jsp";
		}
		//address must contains both number and name
		String addressregex = "^[A-Za-z0-9. ]+$";
		Pattern addresspattern = Pattern.compile(addressregex);
		Matcher am = addresspattern.matcher(address);
		if( ! am.find()){
			return "/checkout.jsp";
		}
		UserDAOImpl userdao = new UserDAOImpl();
		userdao.insert(fname,lname,security_level,email,username,password,address);
		//get that user id
		int userid = userdao.getId(username);
		//create booking bean and get it's id
		BookingDAOImpl bkdao = new BookingDAOImpl();
		//get current time
		java.util.Date date= new java.util.Date();
		Timestamp now = new Timestamp(date.getTime());
		//sum up price
		double totalprice = 0.0d;
		for(RecordBean b : recordlist ){
			totalprice += b.getPrice();
		}
		//add booking bean to db without id
		bkdao.insert(userid, totalprice, now);
		int bookingid = bkdao.getId(now);
		Timestamp in = (Timestamp)(request.getSession().getAttribute("checkin"));
		Timestamp out = (Timestamp)(request.getSession().getAttribute("checkout"));
		RecordDAOImpl rdao = new RecordDAOImpl();
		RoomCalDAOImpl rcdao = new RoomCalDAOImpl();
		int recordid = 0;
		//room calendar and record relationship is 1:1
		//e.g. 5 single room, 2 queen room; The record will insert 7 row of data, as well as 
		for(int i=0; i < recordlist.size(); i++){
			RecordBean b = recordlist.get(i);
			for(int j=0; j< amountList.get(i); j++){ //queue pop 
				rdao.insert(bookingid,b.getHotelid(),b.getRoomtypeid(),b.getExtrabed(),b.getPrice(),b.getCheckindate(),b.getCheckoutdate());
				recordid = rdao.getId(bookingid);
				rcdao.insert(bookingid,b.getRoomtypeid(),b.getCheckindate(),b.getCheckoutdate());
			}
		}
		//generate PIN-------------------------------------
		UserBean ub = userdao.get(userid);
		int PIN = ub.hashCode();
		if(PIN < 0){
			PIN = -1 * PIN;
		}
		PINmap.put(username,PIN);
		//generate URL
		int crypto = (now.toString() + username).hashCode();
		if(crypto < 0){
			crypto = -1 * crypto;
		}
		String URL = "http://localhost:8080/9321_assn2/dispatcher?operation=decode&Crypto=" + crypto;
//		URLmap.put(URL,bookingid);
		//ADD to Pin table-----------------------
		PinDAOImpl pindao = new PinDAOImpl();
		pindao.insert(crypto,PIN,recordid);
		//TODO:save in database
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("URL", URL);
		request.getSession().setAttribute("PIN", PIN);
		return "/afterpayment.jsp";
	}
}
