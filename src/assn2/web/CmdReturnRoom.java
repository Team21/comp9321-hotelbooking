/**
 * 
 */
package assn2.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.BookingBean;
import assn2.beans.HotelBean;
import assn2.beans.RoomBean;
import assn2.daosImpl.BookingDAOImpl;
import assn2.daosImpl.RoomDAOImpl;
import assn2.exceptions.DataAccessException;

/**
 * @author jiakaihang
 *
 */
public class CmdReturnRoom implements Command {

	/* (non-Javadoc)
	 * @see assn2.web.Command#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		HotelBean	hotel = (HotelBean) session.getAttribute("hotel");
		
		if(request.getParameterValues("returnrooms")[0]==null)
			return "listBooking";
		
		System.out.println(request.getParameterValues("returnrooms")[0]);
		int[]		roomids = parseRoomID(request.getParameterValues("returnrooms")[0]);
		
		if(!isValid(hotel,roomids)){
			session.setAttribute("errorMsg", "Conditions n ot valid");
		}else{
			try{
				returnRooms(roomids);
				return "listBooking";
			}catch(DataAccessException e){
				e.printStackTrace();
			}
		}
		
		return "/error.jsp";
	}
	private void returnRooms(int[] roomids) {
		RoomDAOImpl rDao = new RoomDAOImpl();
		for(int id: roomids)
			rDao.setCondition(id, "available");
	}
	private boolean isValid(HotelBean hotel, int[] roomids) {
		RoomDAOImpl rDao = new RoomDAOImpl();
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
