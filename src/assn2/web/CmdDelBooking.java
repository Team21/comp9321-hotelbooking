/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.*;
import assn2.daosImpl.*;

/**
 * @author Kaihang
 *
 */
public class CmdDelBooking implements Command {
	final static long TWO_DAYS = 2*24*60*60*1000; //2-days in milliseconds

	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		BookingBean booking = (BookingBean) session.getAttribute("booking");
		Timestamp date =  booking.getBookingdate();
		Timestamp now = new Timestamp(System.currentTimeMillis());
//		if((now.getTime()-date.getTime())>=TWO_DAYS){
//			session.setAttribute("over2days", true);
//		}else{
//			session.setAttribute("over2days", false);
			deleteBookings(request);
			return "/home.jsp";  //after all deletion
//		}
//		return "/home.jsp";
	}

	private void deleteBookings(HttpServletRequest request) {
		RoomCalDAOImpl rcDao = new RoomCalDAOImpl();
		BookingDAOImpl bDao = new BookingDAOImpl();
		RecordDAOImpl rDao = new RecordDAOImpl();
		HttpSession session = request.getSession();
		BookingBean b = (BookingBean) session.getAttribute("booking");
		//del record
		rDao.deleteAllByBooking(b.getBookingid());
		//del booking
		bDao.delete(b.getBookingid());
		//del roomcalendar
		rcDao.deleteByBooking(b.getBookingid());
		//need to delete user or PIN ? TODO:
	}
}