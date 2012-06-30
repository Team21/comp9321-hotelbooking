/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.HotelBean;
import assn2.daosImpl.HotelDAOImpl;
import assn2.daosImpl.RoomTypeDAOImpl;
import assn2.exceptions.DataAccessException;

/**
 * @author ASUS
 *
 */
public class CmdSetDiscount implements Command {
	final static String[] roomtypes = {"single","twin","queen","executive","suite"};

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

			HttpSession session = request.getSession();
			
			int 		hotelid = Integer.parseInt(request.getParameterValues("setDiscount_hotelid")[0]);
			String 		type 	= request.getParameterValues("setDiscount_type")[0];
			int 		rate 	= Integer.parseInt(request.getParameterValues("setDiscount_rate")[0]);
			Timestamp 	from 	= parseDate(request.getParameterValues("setDiscount_from")[0]);
			Timestamp 	to 		= parseDate(request.getParameterValues("setDiscount_to")[0]);
			if(from.after(to) || from.equals(to)){
				return "/error.jsp"; //TODO:SADFSDF
			}
			if(!isValid(hotelid,type,rate)){ //Invalid information received!!
				session.setAttribute("errorMsg", "Invalid ID, Room Type or Discount Rate!");
			}else{
				System.out.println("Valid!!!!!!!!!!!!!!!!!!!!!!!");

				try{
					setDiscount(hotelid, type, rate, from, to);
					System.out.println("List Booking!!!!!!!!!!!!!!!!!!!!!!!");

					return "listBooking";
				}catch(DataAccessException e){
					e.printStackTrace();
				}
			}
			return "/error.jsp";
		}

		private Timestamp parseDate(String rawin) {
			String[] ri = rawin.split("/");
			int month = Integer.parseInt(ri[0]);
			int day = Integer.parseInt(ri[1]);
			int year = Integer.parseInt(ri[2]);	
			java.util.Date date = new Date(year,month,day);
			DateFormat  df = new SimpleDateFormat("MM/dd/yyyy");
			try {
				date = df.parse(rawin);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Timestamp t = new Timestamp(date.getTime());
			return t;
		}

		private void setDiscount(int hotelid, String type, int rate, Timestamp from, Timestamp to) {
			RoomTypeDAOImpl rtDao = new RoomTypeDAOImpl();
			Double r = Double.parseDouble(Integer.toString(rate))/100;
			type = type.toLowerCase();
			rtDao.setDiscount(hotelid, type, r, from, to);
		}

		private boolean isValid(int hotelid, String type, int rate) {
			HotelBean h = null;
			HotelDAOImpl hDao = new HotelDAOImpl();
			h = hDao.getHotel(hotelid);
			if(h==null) return false;
			
			if(rate < 0 || rate > 100) return false;
			
			for(String s : roomtypes)
				if(s.equalsIgnoreCase(type)) return true;
			
			return false;
		}	
	}