/**
 * 
 */
package assn2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import assn2.beans.*;
import assn2.daosImpl.UserDAOImpl;
import assn2.exceptions.*;

/**
 * @author Kaihang
 *
 */
public class CmdLogin implements Command {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			UserBean user = login(
				request.getParameter("username"), request.getParameter("password"));
			if (user == null) {
				return "/index.jsp";
			} 
			HttpSession session = request.getSession();
			//set user bean !!
			session.setAttribute("user", user);
			// this is not a jsp so it will chain the commands together
			if(user.getSecurity_level().equals("manager") || user.getSecurity_level().equals("owner"))
				return "listBooking";
			else
				return "/index.jsp";
		} catch (UserLoginFailedException e) {
			e.printStackTrace();
			return "/loginfailed.jsp";
		}
	}

	/**
	 * @param parameter
	 * @param parameter2
	 * @return
	 */
	private UserBean login(String username, String password) throws UserLoginFailedException{
		UserBean user = null;
		UserDAOImpl userDao = new UserDAOImpl();
        //TODO: this should try to find a UserBean using the UserDAO  
        //TODO: throw LoginFailedException if the user is not found or the operation fails.
        //TODO: if the user is found, return the user
		user = userDao.getByLogin(username, password);
		if(user == null)
			System.out.println("Cannot find the User");		
		return user;
	}
}
