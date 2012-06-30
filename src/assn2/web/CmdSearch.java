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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.HotelBean;
import assn2.beans.RoomTypeBean;
import assn2.beans.UserBean;
import assn2.daosImpl.HotelDAOImpl;
import assn2.daosImpl.RoomTypeDAOImpl;
import assn2.daosImpl.SearchDAOImpl;
import assn2.daosImpl.UserDAOImpl;
import assn2.exceptions.UserLoginFailedException;

/**
 * @author ASUS
 *
 */
public class CmdSearch implements Command {

	final static double peakRate = 1.4;
	/* (non-Javadoc)
	 * @see assn2.web.Command#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String rr = request.getParameter("requireRoomAmount");
			String rrregex = "^[0-9]+$";
			Pattern rrpattern = Pattern.compile(rrregex);
			Matcher rrm = rrpattern.matcher(rr);
			if( ! rrm.find()){  //is number or not 
				return "/searchresult.jsp";
			}
			
			int requireRoomAmount = Integer.parseInt(rr);
			
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
				return "/index.jsp";
			}//
			Date d1 = new Date(year,month,day);
			Date d2 = new Date(year2,month2,day2);
			java.util.Date now= new java.util.Date();
			if(d1.after(d2) || d1.before(now) || d2.before(now)){  //indate > outdate
				return "/index.jsp";
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
				return "/repeatmessage.jsp"; //try search with different parameter
			}
			double priceofone = rtdao.getprice(roomtypeid);
			
			int days = (int) ((checkoutdate.getTime()-checkindate.getTime())/(1000*60*60*24));
			System.out.println("Days to live:"+days);
			
			int peakDays = getPeakDays(checkindate,checkoutdate);
			int offPeakDays = days-peakDays;
			System.out.println("Peak Days:"+peakDays);
			System.out.println("Off Peak Days:"+offPeakDays);
			
			double totalprice = (priceofone * requireRoomAmount * peakDays * peakRate) + (priceofone * requireRoomAmount * offPeakDays);
			
			RoomTypeBean rt = rtdao.getRoomType(roomtypeid);
			if(rt.getDiscountrate()!=0 && rt.getDiscountfrom()!=null && rt.getDiscountto()!=null){
				long from = rt.getDiscountfrom().getTime();
				long to = rt.getDiscountto().getTime();
				long in = checkindate.getTime();
				long out = checkoutdate.getTime();
				if((in>=from && in<=to)||(out>=from && out <= to)||(in<=from&&out>=to))
					totalprice *= rt.getDiscountrate();
			}
			HttpSession session = request.getSession();
			//show next page
			session.setAttribute("priceofone", priceofone);
			session.setAttribute("totalprice", totalprice);
			session.setAttribute("numberofroomleft", numberofroomleft);
			session.setAttribute("thathotel", thathotel);
			session.setAttribute("roomtypeid", roomtypeid);
			session.setAttribute("requireRoomAmount", requireRoomAmount);
			session.setAttribute("checkin", checkindate);
			session.setAttribute("checkout", checkoutdate);
			session.setAttribute("roomType", rt.getType());
			return "/searchresult.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/index.jsp";
		}
	}
	
	private static int getPeakDays(Timestamp aa, Timestamp bb) {
		Date[] sDates = {	new Date(2012,11,15),
							new Date(2013,2,25),
							new Date(2013,6,1),
							new Date(2013,8,20)};

		Date[] eDates ={	new Date(2013,1,15),
							new Date(2013,3,14),
							new Date(2013,6,20),
							new Date(2013,9,10)};
		int aLength=0;
		int bLength=0;
		if(aa.getYear()!=bb.getYear()){
			Date a = new Date(2012,aa.getMonth(),aa.getDate());
			Date b = new Date(2013,bb.getMonth(),bb.getDate());
			
			if(a.before(sDates[0]))
				if(b.after(sDates[0]))
					return getDiffDays(sDates[0],b);
			
			for(int i=0; i<4; i++){
				if(a.compareTo(sDates[i])>=0 && a.compareTo(eDates[i])<=0){
					if(b.compareTo(sDates[i])>=0 && b.compareTo(eDates[i])<=0){
						return getDiffDays(a,b);
					}else{
						aLength = getDiffDays(a, eDates[0]);
						if(i!=3){
							if(b.compareTo(sDates[i+1])>=0 && b.compareTo(eDates[i+1])<=0)
								bLength = getDiffDays(sDates[1], b);
						}
						return aLength+bLength;
					}
				}
			}
			
			for(int i=0; i<4; i++){
				if(b.compareTo(sDates[i])>=0 && b.compareTo(eDates[i])<=0){
					return getDiffDays(sDates[i],b);
				}
			}		
		}
		else{
			Date a = new Date(2013,aa.getMonth(),aa.getDate());
			Date b = new Date(2013,bb.getMonth(),bb.getDate());
			for(int i=0; i<4; i++){
				if(a.compareTo(sDates[i])>=0 && a.compareTo(eDates[i])<=0){
					if(b.compareTo(sDates[i])>=0 && b.compareTo(eDates[i])<=0){
						return getDiffDays(a,b);
					}else{
						aLength = getDiffDays(a, eDates[0]);
						if(i!=3){
							if(b.compareTo(sDates[i+1])>=0 && b.compareTo(eDates[i+1])<=0)
								bLength = getDiffDays(sDates[1], b);
						}
						return aLength+bLength;
					}
				}
			}
			for(int i=0; i<4; i++){
				if(b.compareTo(sDates[i])>=0 && b.compareTo(eDates[i])<=0){
					return getDiffDays(sDates[i],b);
				}
			}
		}
		return 0;
	}
	
	private static int getDiffDays(Date from, Date to){
		return (int)((to.getTime()-from.getTime())/(1000*60*60*24));
	}
}