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
public class AlbumDao {
  private static final String CHECK_NAME_STMT = "SELECT " +
      "COUNT(*) FROM Albums WHERE name = ?";
      
  private static final String NAME_TO_ID = "SELECT "+
      "album_id FROM Albums WHERE name = ?";
  
  private static final String ID_TO_NAME = "SELECT "+
      "name FROM Albums WHERE album_id = ?";
          
  private static final String CHECK_AUTHORITY_STMT = "SELECT " +
      "role_name FROM Users WHERE email = ?";    

  private static final String OWNER_EMAIL_TO_ID = "SELECT "+
      "user_id FROM Users WHERE email = ?";

  private static final String THIS_USERS_ALBUMS_STMT = "SELECT " + 
      "\"album_id\", \"name\", \"date_created\", \"owner_id\" FROM albums WHERE \"owner_id\" = ?";

  private static final String ALL_ALBUMS_STMT = "SELECT " + 
      "* FROM albums";
      
  private static final String ALL_BUT_MYALBUMS_STMT = "SELECT " + 
      "* FROM albums WHERE owner_id != ?";
      
  //FINALLY LEARNED WHAT BEANS ARE FOR..... BOUT TIME I USE EM!
  private static final String ALBUM_FROM_ID = "SELECT "+
      "\"album_id\", \"name\", \"owner_id\" FROM albums WHERE \"album_id\" = ?";


  private static final String NEW_ALBUM_STMT = "INSERT INTO " + 
                    "Albums (name, date_created, owner_id) VALUES (?, ?, ?)";
                    
  private static final String DEL_ALBUM = "DELETE FROM "+
      "Albums WHERE album_id = ?";
      
  private static final String DEL_PICSN_ALBUM = "DELETE FROM "+
      "Pictures WHERE pictures.album_id = ?";

  public boolean create(String albumname, String date, String ownerEmail) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(CHECK_NAME_STMT);
      stmt.setString(1, albumname);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return false;
      }
      int result = rs.getInt(1);
      if (result > 0) {
        albumname = albumname + "" + (result+1);
      }
      
      try { stmt.close(); }
      catch (Exception e) { }
      
      stmt = conn.prepareStatement(OWNER_EMAIL_TO_ID);
      stmt.setString(1, ownerEmail);
      rs = stmt.executeQuery();
      if(!rs.next()){
        return false;
      }
      int ownerid = rs.getInt(1);
      
      try { stmt.close(); }
      catch (Exception e) { }
      
      stmt = conn.prepareStatement(NEW_ALBUM_STMT);
      stmt.setString(1, albumname);
      stmt.setString(2, date);
      stmt.setInt(3, ownerid);
      
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
  
  
  public void delAlbum(int albId) throws SQLException{
	  Connection conn = null;
	  PreparedStatement stmt = null;
	  try {
		  conn = DbConnection.getConnection();
		  stmt = conn.prepareStatement(DEL_PICSN_ALBUM);
		  stmt.setInt(1, albId);
		  stmt.executeUpdate();
     
          stmt = conn.prepareStatement(DEL_ALBUM);
          stmt.setInt(1, albId);
		  stmt.executeUpdate();
		  
		  stmt.close();
		  conn.close();

	  } catch (SQLException e) {
		  e.printStackTrace();
		  throw e;
	  }finally {
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
 

	public List<AlbumBean> allMyAlbums(int ownerid) throws SQLException {
        Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(THIS_USERS_ALBUMS_STMT);
			stmt.setInt(1, ownerid); 
			rs = stmt.executeQuery(); 
			List<AlbumBean> myalbums = new ArrayList<AlbumBean>();

			while (rs.next()) {
				AlbumBean tmp = new AlbumBean();
				tmp.setAlbId(rs.getInt(1));
				tmp.setName(rs.getString(2));
				tmp.setCreateDate(rs.getString(3));
				tmp.setOwnerId(rs.getInt(4));
				myalbums.add(tmp);
			}
			rs.close();
			stmt.close();
			conn.close();
			return myalbums;

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
	
	
	public List<AlbumBean> allAlbums() throws SQLException {
  	  Connection conn = null;
	  PreparedStatement stmt = null;
	  ResultSet rs = null;
	  try {
		  conn = DbConnection.getConnection();
		  stmt = conn.prepareStatement(ALL_ALBUMS_STMT);
		  rs = stmt.executeQuery(); 
		  List<AlbumBean> myalbums = new ArrayList<AlbumBean>();

		  while (rs.next()) {
			  AlbumBean tmp = new AlbumBean();
			  tmp.setAlbId(rs.getInt(1));
			  tmp.setName(rs.getString(2));
			  tmp.setCreateDate(rs.getString(3));
			  tmp.setOwnerId(rs.getInt(4));
			  myalbums.add(tmp);
		  }
		  rs.close();
		  stmt.close();
		  conn.close();
		  return myalbums;

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
	
  public AlbumBean albumfromid(int albId) throws SQLException {
	  Connection conn = null;
	  PreparedStatement stmt = null;
	  ResultSet rs = null;
    try {
	    conn = DbConnection.getConnection();
	    stmt = conn.prepareStatement(ALBUM_FROM_ID);
	    stmt.setInt(1, albId); 
	    rs = stmt.executeQuery(); 
	    AlbumBean thisAlbum = new AlbumBean();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return thisAlbum;
      }
	    thisAlbum.setAlbId(rs.getInt(1));
	    thisAlbum.setName(rs.getString(2));
	    thisAlbum.setOwnerId(rs.getInt(3));
    
	    rs.close();
	    stmt.close();
	    conn.close();
	    return thisAlbum;

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
  
    public List<AlbumBean> allAlbums(int ownerId) throws SQLException {
	  Connection conn = null;
	  PreparedStatement stmt = null;
      ResultSet rs = null;
        try {
	        conn = DbConnection.getConnection();
	        stmt = conn.prepareStatement(ALL_BUT_MYALBUMS_STMT);
	        stmt.setInt(1, ownerId); 
	        rs = stmt.executeQuery(); 
	        List<AlbumBean> myalbums = new ArrayList<AlbumBean>();

	        while (rs.next()) {
		        AlbumBean tmp = new AlbumBean();
		        tmp.setAlbId(rs.getInt(1));
		        tmp.setName(rs.getString(2));
		        tmp.setCreateDate(rs.getString(3));
		        tmp.setOwnerId(rs.getInt(4));
		        myalbums.add(tmp);
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
	        return myalbums;

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
  
	
	public int nameToId(String name){
	PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(NAME_TO_ID);
      stmt.setString(1, name);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return -1;
      }else{
        return rs.getInt(1);
      }
      
    }catch (SQLException e) {
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
  
  
  public String idtoname(int id){
	    PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
          conn = DbConnection.getConnection();
          stmt = conn.prepareStatement(ID_TO_NAME);
          stmt.setInt(1, id);
          rs = stmt.executeQuery();
          if (!rs.next()) {
            // Theoretically this can't happen, but just in case...
            return "";
          }else{
            return rs.getString(1);
          }
          
        }catch (SQLException e) {
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
}

