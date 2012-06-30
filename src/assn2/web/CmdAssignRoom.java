/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.*;
import assn2.daosImpl.*;
import assn2.exceptions.DataAccessException;

/**
 * @author jiakaihang
 *
 */
public class CmdAssignRoom implements Command {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		HotelBean	hotel = (HotelBean) session.getAttribute("hotel");
		int 		bookingid = Integer.parseInt(request.getParameterValues("bookingid")[0]);
		if(request.getParameterValues("assignedrooms")==null) return "listBooking";
		int[]		roomids = parseRoomID(request.getParameterValues("assignedrooms")[0]);
		
		if(!isValid(hotel, bookingid, roomids)){
			session.setAttribute("errorMsg", "Invalid condition set");
		}else{
			try{
				assign(roomids);
				return "listBooking";
			}catch(DataAccessException e){
				e.printStackTrace();
			}
		}
		return "/error.jsp";
	}

	private void assign(int[] roomids) {
		RoomDAOImpl rDao = new RoomDAOImpl();
		for(int rid:roomids)
			rDao.setCondition(rid, "occupied");
		
	}

	private boolean isValid(HotelBean hotel, int bookingid, int[] roomids) {
		BookingBean b = null;
		BookingDAOImpl bDao = new BookingDAOImpl();
		RoomDAOImpl rDao = new RoomDAOImpl();
		b = bDao.get(bookingid);
		if(b==null) return false;
		List<RoomBean> list = rDao.getRoomByHotel(hotel.getHotlid());
		List<Integer> ROOM_IDs = new ArrayList();
		for(RoomBean r:list)
			ROOM_IDs.add(r.getRoomid());
		for(int id:roomids)
			if(!ROOM_IDs.contains(id)) return false;
		
		return true;
	}

	private int[] parseRoomID(String string) {
		String[] sArr = string.split(",");
		int[] iArr = new int[sArr.length];
		Pattern p = Pattern.compile( "([0-9]+)");
		for(int i=0; i<sArr.length; i++){
			Matcher m = p.matcher(sArr[i]);
			if(m.find())
				iArr[i] = Integer.parseInt(sArr[i]);
		}
		return iArr;
	}

}
