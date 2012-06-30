/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.BookingBean;
import assn2.beans.RecordBean;
import assn2.daosImpl.BookingDAOImpl;
import assn2.daosImpl.RecordDAOImpl;
import assn2.daosImpl.RoomCalDAOImpl;

/**
 * @author ASUS
 *
 */
public class CmdDelALL implements Command {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RoomCalDAOImpl rcDao = new RoomCalDAOImpl();
		BookingDAOImpl bDao = new BookingDAOImpl();
		RecordDAOImpl rDao = new RecordDAOImpl();
		
		List<BookingBean> bl = (ArrayList<BookingBean>) session.getAttribute("bookinglist");
		//List<RecordBean> rl = (ArrayList<RecordBean>)(session.getAttribute("recordlist"));
		for (BookingBean bb : bl) {
			int bid = bb.getBookingid();
			System.out.println("delete bk from bookinglist id=" + bid);
			rDao.deleteAllByBooking(bid);
			bDao.delete(bid);
			rcDao.deleteByBooking(bid);
		}
		request.getSession().invalidate();
		return "/home.jsp";
	}	
}
