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
public class LikeDao {
  private static final String CHECK_LIKE_STMT = "SELECT " +
      "COUNT(*) FROM Likes WHERE pic_id = ? AND wholiked_id = ?";

  private static final String NEW_LIKE_STMT = "INSERT INTO "+
      "Likes(pic_id, wholiked_id) VALUES (?, ?)";
  
  private static final String HOW_MANY_LIKES_STMT = "SELECT " +
      "COUNT(*) FROM Likes WHERE pic_id = ?";
      
  private static final String WHO_LIKES_THIS_STMT = "SELECT " +
      "user_id, first_name, last_name FROM Users, Likes WHERE users.user_id = likes.wholiked_id AND likes.pic_id = ?";

  	public boolean iLike(int picid, int likerid) throws SQLException {
	Connection conn = null;
    PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
        conn = DbConnection.getConnection();
        stmt = conn.prepareStatement(NEW_LIKE_STMT);
        stmt.setInt(1, picid);
        stmt.setInt(2, likerid);

        stmt.executeUpdate();
        
        stmt.close();
        conn.close();

        return true;
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
    
    public int howManyLikes(int picid) throws SQLException{
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
        conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(HOW_MANY_LIKES_STMT);
        stmt.setInt(1, picid);
        rs = stmt.executeQuery();
        if (!rs.next()) {
          return -1;
        }
        int result = rs.getInt(1);
        
        stmt.close();
        rs.close();
        conn.close();
        return result;
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
   
 	public int didiLike(int picid, int likerid) throws SQLException {
	Connection conn = null;
    PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
        conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(CHECK_LIKE_STMT);
        stmt.setInt(1, picid);
        stmt.setInt(2, likerid);
        rs = stmt.executeQuery();
        if (!rs.next()) {
          return -1;
        }
        int result = rs.getInt(1);
        
        stmt.close();
        rs.close();
        conn.close();

        return result;
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
   
   
    public List<NewUserBean> whoLikesThis(int picid) throws SQLException {
	Connection conn = null;
    PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
        conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(WHO_LIKES_THIS_STMT);
        stmt.setInt(1, picid);
        rs = stmt.executeQuery();

       List<NewUserBean> allNames = new ArrayList<NewUserBean>();
        int thisid;
		while (rs.next()) {
		   if( (thisid = rs.getInt(1)) != -1){
			 NewUserBean tmp = new NewUserBean();
			 tmp.setId(thisid);
			 tmp.setFirstName(rs.getString(2));
			 tmp.setLastName(rs.getString(3));
			 allNames.add(tmp);
			}
		}
		
        stmt.close();
        rs.close();
        conn.close();

        return allNames;
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
   
}
