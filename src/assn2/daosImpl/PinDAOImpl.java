/**
 * 
 */
package assn2.daosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import assn2.beans.RoomBean;
import assn2.database.DBConnectionFactory;
import assn2.exceptions.DataAccessException;
import assn2.exceptions.ServiceLocatorException;

/**
 * @author ASUS
 * 
 */
public class PinDAOImpl {
	private DBConnectionFactory services;

	/** Creates a new instance of RoomDAOImpl */
	public PinDAOImpl() {
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
	}

	public PinDAOImpl(DBConnectionFactory services) {
		this.services = services;
	}
	
	public void insert(int crypto, int PIN, int recordid) throws DataAccessException {
		Connection con = null;
		
		try {
			// get the connection
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("insert into pin (crypto,PIN,recordid) values (?,?,?)");
			stmt.setInt(1, crypto);
			stmt.setInt(2, PIN);
			stmt.setInt(3, recordid);
			int rs = stmt.executeUpdate();
			if (rs == 0)// remember to catch the exceptions
				throw new DataAccessException("cannot insert table:PIN");
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
	
	//return PIN
	public int getPin(int crypto) throws DataAccessException {
		Connection con = null;
		int r = 0;
		try {
			// get the connection
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("select * from pin where crypto=(?)");
			stmt.setInt(1, crypto);
			ResultSet rs = stmt.executeQuery();
			if (rs == null)// remember to catch the exceptions
				throw new DataAccessException("cannot find any room by that id");
			rs.next();
			r = rs.getInt("PIN");
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
		return r; // the last
	}
	
	public Timestamp getCheckingdate(int PIN) throws DataAccessException {
		Connection con = null;
		Timestamp r = null;
		try {
			// get the connection
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("SELECT checkindate FROM  `pin` AS p, `record` AS r WHERE p.recordid = r.recordid AND p.PIN=(?)");
			stmt.setInt(1, PIN);
			ResultSet rs = stmt.executeQuery();
			if (rs == null)// remember to catch the exceptions
				throw new DataAccessException("cannot find any room by that id");
			rs.next();
			r = rs.getTimestamp("checkindate");
			return r;
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
	
	public int getRecordid(int PIN) throws DataAccessException {
		Connection con = null;
		int r = 0;
		try {
			// get the connection
			con = services.createConnection();
			PreparedStatement stmt = con
					.prepareStatement("SELECT * FROM  `pin` WHERE PIN=(?)");
			stmt.setInt(1, PIN);
			ResultSet rs = stmt.executeQuery();
			if (rs == null)// remember to catch the exceptions
				throw new DataAccessException("cannot find any room by that id");
			rs.next();
			r = rs.getInt("recordid");
			return r;
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
}
