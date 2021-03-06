package assn2.daosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import assn2.beans.BookingBean;
import assn2.daos.BookingDAO;

import assn2.database.DBConnectionFactory;
import assn2.exceptions.DataAccessException;
import assn2.exceptions.ServiceLocatorException;



/**
 * @author Kaihang
 * 
 */
public class BookingDAOImpl implements BookingDAO {


	/**
	 * The service locator to retrieve database connections from
	 */
	private DBConnectionFactory services;

	/** Creates a new instance of ContactDAOImpl */
	public BookingDAOImpl() {
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
	}

	public BookingDAOImpl(DBConnectionFactory services) {
		this.services = services;
	}

	public void insert(BookingBean bean) throws DataAccessException {
		Connection con = null;
		try {
			// get the connection
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("Insert Into booking (bookingid, userid, totalprice, bookingdate) values (?, ?, ?, ?)");
			stmt.setInt(1, bean.getBookingid());
			stmt.setInt(2, bean.getUserid());
			stmt.setDouble(3, bean.getTotalprice());
			stmt.setTimestamp(4, bean.getBookingdate());
			// execute the update
			int n = stmt.executeUpdate();
			if (n != 1)// remember to catch the exceptions
				throw new DataAccessException(
						"Did not insert one row into database");
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			if (con != null) {
				try {
					con.close();// and close the connections etc
				} catch (SQLException e1) { // if not close properly
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void insert(int userid, double totalprice, Timestamp bookingdate) throws DataAccessException {
		Connection con = null;
		try {
			// get the connection
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("Insert Into booking (userid, totalprice, bookingdate) values (?, ?, ?)");
			
			stmt.setInt(1, userid);
			stmt.setDouble(2, totalprice);
			stmt.setTimestamp(3, bookingdate);
			// execute the update
			int n = stmt.executeUpdate();
			if (n != 1)// remember to catch the exceptions
				throw new DataAccessException(
						"Did not insert one row into database");
		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; "
					+ e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; "
					+ e.getMessage(), e);
		} finally {
			if (con != null) {
				try {
					con.close();// and close the connections etc
				} catch (SQLException e1) { // if not close properly
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void delete(int id) throws DataAccessException {
		Connection conn = null;
		try {
			conn = services.createConnection();
			PreparedStatement stmt = conn
					.prepareStatement("DELETE FROM booking WHERE bookingid = (?)");
			stmt.setInt(1, id);
			int n = stmt.executeUpdate();
			if (n != 1)// remember to catch the exceptions
				throw new DataAccessException(
						"cannot delete that entity of that id");
		} catch (ServiceLocatorException e) {
			e.printStackTrace();// no connection
		} catch (SQLException e) {
			e.printStackTrace();// not execution of statement
		} finally {
			if (conn != null) {
				try {
					conn.close();// and close the connections etc
				} catch (SQLException e1) { // if not close properly
					e1.printStackTrace();
				}
			}
		}
	}

	public List<BookingBean> getBookingsByUser(int id) throws DataAccessException {
		BookingBean r = null;
		Connection conn = null;
		List<BookingBean> list = new ArrayList<BookingBean>();
		try {
			conn = services.createConnection();
			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM booking WHERE userid = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs == null)// remember to catch the exceptions
				throw new DataAccessException(
						"cannot find any record owned by that id");
			while(rs.next()){
				BookingBean bb = createBookingBean(rs);
				list.add(bb);
			}
			return list;
			
		} catch (ServiceLocatorException e) {
			e.printStackTrace();// no connection
		} catch (SQLException e) {
			e.printStackTrace();// not execution of statement
		} finally {
			if (conn != null) {
				try {
					conn.close();// and close the connections etc
				} catch (SQLException e1) { // if not close properly
					e1.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * finds one particular record for the given id Directly return contact
	 * given by its number
	 */
	public BookingBean get(int id) throws DataAccessException {
		Connection conn = null;
		try {
			conn = services.createConnection();
			PreparedStatement stmt = conn
					.prepareStatement("select * from booking where bookingid = ?");
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();
			if (rs == null)// remember to catch the exceptions
				throw new DataAccessException("cannot find entity of that id");
			if (rs.next()) {
				BookingBean r = createBookingBean(rs);
				stmt.close(); // close it
				return r;//TODO:can be problematic by not closing connection
			}
		} catch (ServiceLocatorException e) {
			e.printStackTrace();// no connection
		} catch (SQLException e) {
			e.printStackTrace();// not execution of statement
		} finally {
			if (conn != null) {
				try {
					conn.close();// and close the connections etc
				} catch (SQLException e1) { // if not close properly
					e1.printStackTrace();
				}
			}
		}
		return null; // not found
	}

	public int getId(Timestamp ts) throws DataAccessException {
		Connection conn = null;
		int r = -1;
		try {
			conn = services.createConnection();
			PreparedStatement stmt = conn
					.prepareStatement("select * from booking where bookingdate = ?");
			stmt.setTimestamp(1, ts);

			ResultSet rs = stmt.executeQuery();
			if (rs == null)// remember to catch the exceptions
				throw new DataAccessException("cannot find entity of that id");
			rs.next();
			r = rs.getInt("bookingid");
		} catch (ServiceLocatorException e) {
			e.printStackTrace();// no connection
		} catch (SQLException e) {
			e.printStackTrace();// not execution of statement
		} finally {
			if (conn != null) {
				try {
					conn.close();// and close the connections etc
				} catch (SQLException e1) { // if not close properly
					e1.printStackTrace();
				}
			}
		}
		return r; // not found
	}

	
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private BookingBean createBookingBean(ResultSet rs) throws SQLException {
		BookingBean c = new BookingBean();
		c.setBookingdate(rs.getTimestamp("bookingdate"));
		c.setBookingid(rs.getInt("bookingid"));
		c.setTotalprice(rs.getDouble("totalprice"));
		c.setUserid(rs.getInt("userid"));
		return c;
	}

}