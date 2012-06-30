/**
 * 
 */
package assn2.daosImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import assn2.beans.RoomTypeBean;

import assn2.daos.RoomTypeDAO;
import assn2.database.DBConnectionFactory;
import assn2.exceptions.DataAccessException;
import assn2.exceptions.ServiceLocatorException;


/**
 * @author Kaihang
 *
 */
public class RoomTypeDAOImpl implements RoomTypeDAO {

	/**
	 * The service locator to retrieve database connections from
	 */
	private DBConnectionFactory services;
	
	/** Creates a new instance of RoomTypeDAOImpl */
	public RoomTypeDAOImpl() {
		try {
			services = new DBConnectionFactory();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Constructor with argument
	 * @param services
	 */
	public RoomTypeDAOImpl(DBConnectionFactory services) {
		this.services = services;
	}
	
	@Override
	public RoomTypeBean getRoomType(int id) throws DataAccessException {
		Connection conn = null;
		try {
			conn = services.createConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from RoomType where roomtypeid = ? ");
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			if (rs == null)//remember to catch the exceptions
			 	  throw new DataAccessException("cannot find entity of that roomtypeid");
			if (rs.next()){
				RoomTypeBean r = createRoomTypeBean(rs);
				stmt.close(); //close it
				return r;
			}
		} catch (ServiceLocatorException e) {
			e.printStackTrace();//no connection
		} catch (SQLException e) {
			e.printStackTrace();//not execution of statement
		} finally {
		      if (conn != null) {
			         try {
			           conn.close();//and close the connections etc
			   		 } catch (SQLException e1) {  //if not close properly
			           e1.printStackTrace();
			         }
			  }
		}
		return null; //not found
	}

	@Override
	public List<RoomTypeBean> getAllByHotel(int id) throws DataAccessException {
		Connection conn = null;
		List<RoomTypeBean> list = new ArrayList<RoomTypeBean>();
		try {
			conn = services.createConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from RoomType where hotelid = ? ");
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			if (rs == null)//remember to catch the exceptions
			 	  throw new DataAccessException("cannot find entity of that hotelid");
			while (rs.next()){
				RoomTypeBean r = createRoomTypeBean(rs);
				list.add(r);
			}
		} catch (ServiceLocatorException e) {
			e.printStackTrace();//no connection
		} catch (SQLException e) {
			e.printStackTrace();//not execution of statement
		} finally {
		      if (conn != null) {
			         try {
			           conn.close();//and close the connections etc
			   		 } catch (SQLException e1) {  //if not close properly
			           e1.printStackTrace();
			         }
			  }
		}
		return list;
	}

	@Override
	public List<RoomTypeBean> getAllByType(String type)
			throws DataAccessException {
		Connection conn = null;
		List<RoomTypeBean> list = new ArrayList<RoomTypeBean>();
		try {
			conn = services.createConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from RoomType where type = ? ");
			stmt.setString(1, type);
			
			ResultSet rs = stmt.executeQuery();
			if (rs == null)//remember to catch the exceptions
			 	  throw new DataAccessException("cannot find entity of that type");
			while (rs.next()){
				RoomTypeBean r = createRoomTypeBean(rs);
				list.add(r);
			}
		} catch (ServiceLocatorException e) {
			e.printStackTrace();//no connection
		} catch (SQLException e) {
			e.printStackTrace();//not execution of statement
		} finally {
		      if (conn != null) {
			         try {
			           conn.close();//and close the connections etc
			   		 } catch (SQLException e1) {  //if not close properly
			           e1.printStackTrace();
			         }
			  }
		}
		return list;
	}
	
	public int getamount (int roomtypeid) {
		Connection con = null;	
		int amount = 0;
		   try {
			 //get the connection 
			 con = services.createConnection();
			 PreparedStatement stmt = con.prepareStatement("SELECT amount FROM  `roomtype` WHERE roomtypeid=(?)");
			 stmt.setInt(1, roomtypeid);
		     //execute the update
		     ResultSet rs = stmt.executeQuery();
		     if (rs == null)//remember to catch the exceptions
		 	   throw new DataAccessException("Did not get amount");
		     while(rs.next()){
		    	 amount = rs.getInt("amount");
		     }
		   } catch (ServiceLocatorException e) {
		       throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
		   } catch (SQLException e) {
		       throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
		   } finally {
		      if (con != null) {
		         try {
		           con.close();//and close the connections etc
		   		 } catch (SQLException e1) {  //if not close properly
		           e1.printStackTrace();
		         }
		      }
		   }
		   return amount;
	}
	
	public int getroomtypeid(int hotelid, String roomtype){
		Connection con = null;
		int tid = 0;
		   try {
			 //get the connection 
			 con = services.createConnection();
			 PreparedStatement stmt = con.prepareStatement("SELECT * FROM  `roomtype` WHERE type=(?) AND hotelid=(?)");
			 stmt.setString(1, roomtype);
			 stmt.setInt(2, hotelid);
			 //execute the update
		     ResultSet rs = stmt.executeQuery();
		     if (rs == null)//remember to catch the exceptions
		 	   throw new DataAccessException("Did not get rm type id");
		     while(rs.next()){
		    	 tid = rs.getInt("roomtypeid");
		     }
		   } catch (ServiceLocatorException e) {
		       throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
		   } catch (SQLException e) {
		       throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
		   } finally {
		      if (con != null) {
		         try {
		           con.close();//and close the connections etc
		   		 } catch (SQLException e1) {  //if not close properly
		           e1.printStackTrace();
		         }
		      }
		   }
		   return tid;
	}
	
	public double getprice (int roomtypeid) {
		Connection con = null;
		double price = 0.0d;
			try {
			 //get the connection 
			 con = services.createConnection();
			 PreparedStatement stmt = con.prepareStatement("SELECT price FROM  `roomtype` WHERE roomtypeid=(?)");
			 stmt.setInt(1, roomtypeid);
		     //execute the update
		     ResultSet rs = stmt.executeQuery();
		     if (rs == null)//remember to catch the exceptions
		 	   throw new DataAccessException("Did not get price");
		     while(rs.next()){
		    	 price = rs.getDouble("price");
		     }
		   } catch (ServiceLocatorException e) {
		       throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
		   } catch (SQLException e) {
		       throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
		   } finally {
		      if (con != null) {
		         try {
		           con.close();//and close the connections etc
		   		 } catch (SQLException e1) {  //if not close properly
		           e1.printStackTrace();
		         }
		      }
		   }
		   return price;
	}
	
	//hotel id, type, r, from, to
	public void setDiscount(int hotelid, String type, double Rate, Timestamp from, Timestamp to){
		Connection conn = null;
		try {
			conn = services.createConnection();
			PreparedStatement stmt = conn.prepareStatement("UPDATE  roomtype SET  discountrate =  (?)," +
			 		"discountfrom =  (?), discountto = (?) WHERE  hotelid = (?) AND type = (?);");
			stmt.setDouble(1, Rate);
			stmt.setTimestamp(2, from);
			stmt.setTimestamp(3, to);
			stmt.setInt(4, hotelid);
			stmt.setString(5, type);
			
			int rs = stmt.executeUpdate();
			if (rs != 1)//remember to catch the exceptions
			 	  throw new DataAccessException("cannot find entity of that hotelid");
		} catch (ServiceLocatorException e) {
			e.printStackTrace();//no connection
		} catch (SQLException e) {
			e.printStackTrace();//not execution of statement
		} finally {
		      if (conn != null) {
			         try {
			           conn.close();//and close the connections etc
			   		 } catch (SQLException e1) {  //if not close properly
			           e1.printStackTrace();
			         }
			  }
		}
	}
	
	public RoomTypeBean createRoomTypeBean(ResultSet rs) throws SQLException {
		RoomTypeBean r = new RoomTypeBean(
				rs.getInt("roomtypeid"), 
				rs.getInt("amount"),
				rs.getInt("hotelid"), 
				rs.getString("type"), 
				rs.getDouble("price"), 
				rs.getDouble("discountrate"), 
				rs.getTimestamp("discountfrom"), 
				rs.getTimestamp("discountto"), 
				rs.getString("description"));
		return r;
	}
}