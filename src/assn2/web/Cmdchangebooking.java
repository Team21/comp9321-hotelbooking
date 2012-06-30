/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.BookingBean;
import assn2.beans.HotelBean;
import assn2.beans.RecordBean;
import assn2.beans.UserBean;
import assn2.daosImpl.BookingDAOImpl;
import assn2.daosImpl.HotelDAOImpl;
import assn2.daosImpl.RecordDAOImpl;
import assn2.daosImpl.RoomCalDAOImpl;
import assn2.daosImpl.RoomTypeDAOImpl;
import assn2.daosImpl.SearchDAOImpl;
import assn2.exceptions.DataAccessException;

/**
 * @author ASUS
 *
 */
public class Cmdchangebooking implements Command{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//get results but without entering user details. then go back to changebooking.jsp
		try {
			ArrayList<Integer> changes = (ArrayList<Integer>)(request.getSession().getAttribute("changes"));
			int requireRoomAmount = Integer.parseInt(request.getParameter("requireRoomAmount"));
			UserBean user = (UserBean)(request.getSession().getAttribute("user"));
			String city = request.getParameter("city");
			String roomtype = request.getParameter("roomtype");
			String rawin = request.getParameterValues("checkindate")[0];
			String[] ri = rawin.split("/");
			int month = Integer.parseInt(ri[0]);
			int day = Integer.parseInt(ri[1]);
			int year = Integer.parseInt(ri[2]);
			String rawout = request.getParameterValues("checkoutdate")[0];
			String[] ro = rawout.split("/");
			int month2 = Integer.parseInt(ro[0]);
			int day2 = Integer.parseInt(ro[1]);
			int year2 = Integer.parseInt(ro[2]);
			
			if (rawin == null || rawout == null) {
				return "/home.jsp";
			}//
			Date d1 = new Date(year,month,day);
			Date d2 = new Date(year2,month2,day2);
			java.util.Date now= new java.util.Date();
			if(d1.after(d2) || d1.before(now) || d2.before(now)){  //indate > outdate
				return "/puresearch.jsp";
			}
			DateFormat  df = new SimpleDateFormat("MM/dd/yyyy");
			java.util.Date date = null;
			java.util.Date date2 = null;
			try {
				date = df.parse(rawin);
				date2 = df.parse(rawout);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Timestamp checkindate = new Timestamp(date.getTime());
			Timestamp checkoutdate = new Timestamp(date2.getTime());
			//perform search
			int roomtypeid = 0;
			int hotelid = 0;
			HotelDAOImpl hdao = new HotelDAOImpl();
			List<HotelBean> hbs = hdao.getHotelByCity(city);
			HotelBean thathotel = hbs.get(0);
			hotelid = thathotel.getHotlid(); //TODO:assume 1 city 1 hotel,Hence only pick up one hotel
			RoomTypeDAOImpl rtdao = new RoomTypeDAOImpl();
			roomtypeid = rtdao.getroomtypeid(hotelid, roomtype);
			//same room type and find clashes
			SearchDAOImpl sd = new SearchDAOImpl();
			int numberofclash = sd.overlapnumber(city, roomtype, checkindate, checkoutdate,roomtypeid);
			int amount = rtdao.getamount(roomtypeid); //room type id
			int numberofroomleft = amount - numberofclash;
			//
			if(numberofroomleft < requireRoomAmount){
				return "/droporkeep.jsp"; //no such results
			}
			double priceofone = rtdao.getprice(roomtypeid);
			double totalprice = requireRoomAmount * priceofone;
			
			//add to db
			//create booking bean and get it's id
			BookingDAOImpl bkdao = new BookingDAOImpl();
			//get current time
			java.util.Date date21 = new java.util.Date();
			Timestamp now2 = new Timestamp(date21.getTime());
			//add booking bean to db without id
			bkdao.insert(user.getUserid(), totalprice, now2);
			int bookingid = bkdao.getId(now2);
			//keep track of booking id
			changes.add(bookingid);
			
			RecordDAOImpl rdao = new RecordDAOImpl();
			RoomCalDAOImpl rcdao = new RoomCalDAOImpl();
			for(int i=0; i < requireRoomAmount; i++){
				rdao.insert(bookingid,hotelid,roomtypeid,1,totalprice,checkindate,checkoutdate);
				rcdao.insert(bookingid,roomtypeid,checkindate,checkoutdate);
			}
			int recordid = rdao.getId(bookingid);
			//ADD new row in RoomCalendar -----------
			//construct list
			List<BookingBean> bookinglist = new ArrayList<BookingBean>();
			List<RecordBean> recordlist = new ArrayList<RecordBean>();
			HttpSession session = request.getSession();
			try{
				BookingDAOImpl bookingDao = new BookingDAOImpl();
				bookinglist =  bookingDao.getBookingsByUser(user.getUserid());
			}catch(DataAccessException e){
				e.printStackTrace();
			}
			if(bookinglist!=null){
				try{
					RecordDAOImpl recordDao = new RecordDAOImpl();
					for (BookingBean bb : bookinglist) {
						int bkid = bb.getBookingid();
						recordlist.addAll( recordDao.getAllByBooking(bkid));
					}
					//listBooking = List<RecordBean>
					session.setAttribute("changes", changes);
					session.setAttribute("bookinglist", bookinglist);
					session.setAttribute("recordlist", recordlist);
				}catch(DataAccessException e){
					e.printStackTrace();
				}
			}
			session.setAttribute("secondtryflag", "second");
			return "/changebooking.jsp";  //add directly
		} catch (Exception e) {
			e.printStackTrace();
			return "/puresearch.jsp";
		}
	}
}