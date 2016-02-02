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
public class CommentDao {

    private static final String ADD_COM_STMT = "INSERT INTO "+
        "Comments(text, comment_date, pic_id, owner_id) VALUES (?,?,?,?)";
    
    private static final String GET_PIC_COMS_STMT = "SELECT "+
        "text, comment_date, pic_id, owner_id FROM Comments WHERE pic_id = ?";

  	public boolean commentOn(String comment, String date, int picid, int ownerid) throws SQLException {
	Connection conn = null;
    PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
        conn = DbConnection.getConnection();
        stmt = conn.prepareStatement(ADD_COM_STMT);
        stmt.setString(1, comment);
        stmt.setString(2, date);
        stmt.setInt(3,picid);
        stmt.setInt(4,ownerid);

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
 	public List<CommentBean> allComments(int picid) throws SQLException {
	Connection conn = null;
    PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
        conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(GET_PIC_COMS_STMT);
        stmt.setInt(1, picid);
        rs = stmt.executeQuery();
        
        List<CommentBean> all = new ArrayList<CommentBean>();      
        while(rs.next()){
			 CommentBean tmp = new CommentBean();
			 tmp.setComment(rs.getString(1));
			 tmp.setCreateDate(rs.getString(2));
			 tmp.setPicId(rs.getInt(3));
			 tmp.setOwnerId(rs.getInt(4));
			 all.add(tmp);
        
        }
        
        stmt.close();
        rs.close();
        conn.close();

        return all;
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
   
   
    public List<NewUserBean> whoCommentedThis(int picid) throws SQLException {
	Connection conn = null;
    PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
        conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(GET_PIC_COMS_STMT);
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
