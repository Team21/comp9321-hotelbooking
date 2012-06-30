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

import assn2.beans.BookingBean;
import assn2.beans.RecordBean;
import assn2.beans.UserBean;
import assn2.daosImpl.BookingDAOImpl;
import assn2.daosImpl.RecordDAOImpl;
import assn2.exceptions.DataAccessException;

/**
 * @author ASUS
 *
 */
public class CmdUserList implements Command {

	/* (non-Javadoc)
	 * @see assn2.web.Command#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<RecordBean> recordlist = (ArrayList<RecordBean>)(request.getSession().getAttribute("recordlist"));
		
		
		return "/home.jsp";
	}
}