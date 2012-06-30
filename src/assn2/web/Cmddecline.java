/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.BookingBean;
import assn2.daosImpl.BookingDAOImpl;
import assn2.daosImpl.RecordDAOImpl;
import assn2.daosImpl.RoomCalDAOImpl;

/**
 * @author ASUS
 *
 */
public class Cmddecline implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//go back to origin 
		RoomCalDAOImpl rcDao = new RoomCalDAOImpl();
		BookingDAOImpl bDao = new BookingDAOImpl();
		RecordDAOImpl rDao = new RecordDAOImpl();
		HttpSession session = request.getSession();
		ArrayList<Integer> changes = (ArrayList<Integer>)(session.getAttribute("changes"));
		for (Integer i : changes) {
			//delete record
			rDao.deleteAllByBooking(i);
			System.out.println("changes:"+i);
			//first delete booking
			bDao.delete(i);
			//delete room calendar
			rcDao.deleteByBooking(i);
		}
		//delete it
		request.getSession().setAttribute("changes", null);
		return "listBooking";
	}
}
