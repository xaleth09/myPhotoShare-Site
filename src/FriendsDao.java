package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle the Users table
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class FriendsDao {
  private static final String CHECK_FRIENDS_STMT = "SELECT " +
       "COUNT(*) FROM friends WHERE friends.f_user_id = ? AND friends.owner_id = ?";

  private static final String ADD_FRIEND_STMT = "INSERT INTO " + 
       "Friends (f_user_id, owner_id)  VALUES (?, ?)";
  private static final String DEL_FRIEND_STMT = "DELETE FROM friends WHERE "+
              "f_user_id = ? AND owner_id = ?";
       
  private static final String MY_FRIENDS_STMT = "SELECT "+
  "\"user_id\", \"first_name\", \"last_name\", \"email\" FROM users, friends " + 
  "WHERE users.user_id = friends.f_user_id AND friends.owner_id = ?";

  private static final String NOT_MY_FRIENDS_STMT = "SELECT \"user_id\", \"first_name\", \"last_name\", \"email\" "+ 
                                    "FROM users FULL JOIN (SELECT f_user_id FROM friends WHERE owner_id = ?)" + 
                                    "AS tmp ON users.user_id = tmp.f_user_id WHERE tmp.f_user_id IS NULL AND users.user_id != ? AND users.user_id != 15";

  public boolean create(String friend_email, String owner_email) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    NewUserDao userDao = new NewUserDao();
    int f_id = userDao.emailtoid(friend_email);
    int o_id = userDao.emailtoid(owner_email);
    
    
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(ADD_FRIEND_STMT);
      stmt.setInt(1, f_id);
      stmt.setInt(2, o_id);
     
      stmt.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException e) { ; }
        rs = null;
      }
      
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException e) { ; }
        stmt = null;
      }
      
      if (conn != null) {
        try { conn.close(); }
        catch (SQLException e) { ; }
        conn = null;
      }
    }
  }
  
  public List<NewUserBean> myFriendsList(int o_id) throws SQLException{
  Connection conn = null;
  PreparedStatement stmt = null;
  ResultSet rs = null;
  
  try {
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(MY_FRIENDS_STMT);
		stmt.setInt(1, o_id);
		rs = stmt.executeQuery(); 
		List<NewUserBean> friends = new ArrayList<NewUserBean>();
		while (rs.next()) {
			 NewUserBean tmp = new NewUserBean();
			 tmp.setId(rs.getInt(1));
 			 tmp.setEmail(rs.getString("email"));
			 tmp.setFirstName(rs.getString("first_name"));
			 tmp.setLastName(rs.getString("last_name"));
			 friends.add(tmp);
		}
		rs.close();
		stmt.close();
		conn.close();
		return friends;

	} catch (SQLException e) {
		e.printStackTrace();
		throw e;
	} finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException e) { ; }
        rs = null;
      }
      
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException e) { ; }
        stmt = null;
      }
      
      if (conn != null) {
        try { conn.close(); }
        catch (SQLException e) { ; }
        conn = null;
      }
  }
}  
  public List<NewUserBean> notMyFriends(int o_id) throws SQLException{
  Connection conn = null;
  PreparedStatement stmt = null;
  ResultSet rs = null;
  try {
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(NOT_MY_FRIENDS_STMT);
		stmt.setInt(1, o_id);
		stmt.setInt(2, o_id);
		rs = stmt.executeQuery(); 
		List<NewUserBean> friends = new ArrayList<NewUserBean>();
		while (rs.next()) {
			 NewUserBean tmp = new NewUserBean();
			 tmp.setId(rs.getInt(1));
 			 tmp.setEmail(rs.getString("email"));
			 tmp.setFirstName(rs.getString("first_name"));
			 tmp.setLastName(rs.getString("last_name"));
			 friends.add(tmp);
		}
		rs.close();
		stmt.close();
		conn.close();
		return friends;

	} catch (SQLException e) {
		e.printStackTrace();
		throw e;
	} finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException e) { ; }
        rs = null;
      }
      
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException e) { ; }
        stmt = null;
      }
      
      if (conn != null) {
        try { conn.close(); }
        catch (SQLException e) { ; }
        conn = null;
      }
  }
}  
  public void deleteFriend(String f_email, String owner_email) throws SQLException{
    Connection conn = null;
	PreparedStatement stmt = null;
  try {
        NewUserDao userDao = new NewUserDao();
        int f_id = userDao.emailtoid(f_email);
        int o_id = userDao.emailtoid(owner_email);
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(DEL_FRIEND_STMT);
		stmt.setInt(1, f_id);
		stmt.setInt(2, o_id);
        stmt.executeUpdate(); 
        
		stmt.close();
		conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
		throw e;
	} finally {
      
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException e) { ; }
        stmt = null;
      }
      
      if (conn != null) {
        try { conn.close(); }
        catch (SQLException e) { ; }
        conn = null;
      }
    
  }
 }
  
}
