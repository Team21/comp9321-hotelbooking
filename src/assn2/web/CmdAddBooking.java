package assn2.web;
/**
 * User can add booking for serveral times before checkout
 * This page will record data to Record Table in database and go to checkout(payment)
 */
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import assn2.beans.*;
import assn2.daosImpl.*;
/**
 * @author Kaihang
 *
 */
public class CmdAddBooking implements Command {

	final static double peakRate = 1.4;
	/* (non-Javadoc)
	 * @see assn2.web.Command#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<RecordBean> recordlist = (ArrayList<RecordBean>)(request.getSession().getAttribute("recordlist"));
		List<Integer> amountList = (ArrayList<Integer>)(request.getSession().getAttribute("amountList"));
		
		if(recordlist == null)//create a new one
			recordlist = new ArrayList<RecordBean>();
		
		if(amountList == null)
			amountList = new ArrayList<Integer>();
		
		try{

			String extrabed = request.getParameter("extrabed");
			int requireRoomAmount = Integer.parseInt(request.getParameter("requireRoomAmount"));//3
			int roomtypeid = Integer.parseInt(request.getParameter("roomtypeid"));//1

			double totalprice = (Double)(request.getSession().getAttribute("totalprice"));
			HotelBean hb = (HotelBean)(request.getSession().getAttribute("thathotel"));
			int hotelid = hb.getHotlid();
			int ebed = 0;
			if(extrabed == null){
				ebed = 0;
			}else{
				ebed = 1;
			}
			Timestamp checkindate = (Timestamp)(request.getSession().getAttribute("checkin"));
			Timestamp checkoutdate = (Timestamp)(request.getSession().getAttribute("checkout"));
			
//			int days = (int) ((checkoutdate.getTime()-checkindate.getTime())/(1000*60*60*24));
//			System.out.println("Days to live:"+days);
//			
//			int peakDays = getPeakDays(checkindate,checkoutdate);
//			int offPeakDays = days-peakDays;
//			System.out.println("Peak Days:"+peakDays);
//			System.out.println("Off Peak Days:"+offPeakDays);
//			
//			//recalculate price regards to extra bed
			totalprice = totalprice + ebed * requireRoomAmount * 10 ;
//			double totalprice = (dayprice * peakDays * peakRate) + (dayprice * offPeakDays);
//			RoomTypeDAOImpl rtDao = new RoomTypeDAOImpl();
//			RoomTypeBean rt = rtDao.getRoomType(roomtypeid);
//			if(rt.getDiscountrate()!=0 && rt.getDiscountfrom()!=null && rt.getDiscountto()!=null){
//				long from = rt.getDiscountfrom().getTime();
//				long to = rt.getDiscountto().getTime();
//				long in = checkindate.getTime();
//				long out = checkoutdate.getTime();
//				if((in>=from && in<=to)||(out>=from && out <= to)||(in<=from&&out>=to))
//					totalprice *= rt.getDiscountrate();
//			}
			//warning: without any id
			RecordBean rb = new RecordBean(0,0,hotelid,roomtypeid,ebed,totalprice/requireRoomAmount,checkindate,checkoutdate);
			recordlist.add(rb);
			request.getSession().setAttribute("recordlist", recordlist);
			//
			amountList.add(requireRoomAmount);
			request.getSession().setAttribute("amountList", amountList);
			request.getSession().setAttribute("checkin", null);
			request.getSession().setAttribute("checkout", null);
			
			
			return "/home.jsp";
		}catch(Exception e){
			e.printStackTrace();
			return "/home.jsp";
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