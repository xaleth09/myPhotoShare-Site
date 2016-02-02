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
public class TagDao {

  private static final String ADD_TAG_STMT = "INSERT INTO "+
    "Tags(tag_word, pic_id) VALUES (?,?)";
    
  private static final String PIC_TAGS_STMT = "SELECT " +
    "tag_word FROM tags WHERE pic_id = ?";

  public boolean tagOn(String tag, int pic_id) throws SQLException{
    PreparedStatement stmt = null;
    Connection conn = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(ADD_TAG_STMT);
      stmt.setString(1, tag);
      stmt.setInt(2, pic_id);
      stmt.executeUpdate();
      

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
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
  
  
 public List<TagBean> allTags(int pic_id) throws SQLException{
    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(PIC_TAGS_STMT);
			stmt.setInt(1, pic_id); 
			rs = stmt.executeQuery(); 
			List<TagBean> tags = new ArrayList<TagBean>();

			while (rs.next()) {
				TagBean tmp = new TagBean();
				tmp.setTag(rs.getString(1));
				tags.add(tmp);
			}
			rs.close();
			stmt.close();
			conn.close();
			return tags;

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

