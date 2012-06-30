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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import assn2.beans.RoomBean;
import assn2.daosImpl.HotelDAOImpl;
import assn2.daosImpl.RoomDAOImpl;

/**
 * @author Kaihang
 *
 */
public class CmdChangeCondition implements Command {

	/* (non-Javadoc)
	 * @see assn2.web.Command#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		setDiscount_from
//		setDiscount_to
		String rawin = request.getParameterValues("conditionstart")[0];
		String[] ri = rawin.split("/");
		int month = Integer.parseInt(ri[0]);
		int day = Integer.parseInt(ri[1]);
		int year = Integer.parseInt(ri[2]);
		String rawout = request.getParameterValues("conditionend")[0];
		String[] ro = rawout.split("/");
		int month2 = Integer.parseInt(ro[0]);
		int day2 = Integer.parseInt(ro[1]);
		int year2 = Integer.parseInt(ro[2]);
		java.util.Date date = new Date(year,month,day);
		java.util.Date date2 = new Date(year2,month2,day2);
		DateFormat  df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			date = df.parse(rawin);
			date2 = df.parse(rawout);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp start = new Timestamp(date.getTime());
		Timestamp end = new Timestamp(date2.getTime());
	
		RoomDAOImpl rdao = new RoomDAOImpl();
		
		String ncond = (String)(request.getParameter("setCondition_cond"));
		int hid = Integer.parseInt((String)(request.getParameter("setCondition_hotelid")));
		List<RoomBean> rms = rdao.getRoomByHotel(hid);
		//set condition for each room in the hotel ,given hotel id
		for (RoomBean roomBean : rms) {
			rdao.setRoomCondition(roomBean.getRoomid(), ncond, start, end);
		}
		return "listBooking";
	}
}
