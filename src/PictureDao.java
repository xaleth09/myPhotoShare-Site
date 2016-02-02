package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle picture objects
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class PictureDao {
  private static final String LOAD_PICTURE_STMT = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\" FROM Pictures WHERE \"picture_id\" = ?";

  private static final String SAVE_PICTURE_STMT = "INSERT INTO " +
      "Pictures (\"album_id\", \"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\", \"owner_id\") VALUES (?, ?, ?, ?, ?, ?, ?)";

  private static final String DEL_PICTURE_STMT = "DELETE FROM pictures WHERE "+
              "picture_id = ?";

  private static final String ALL_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures ORDER BY \"picture_id\" DESC";

  private static final String ALBUM_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures WHERE album_id = ? ORDER BY \"picture_id\" DESC";

  private static final String CHECK_ALBUM_NAME_STMT = "SELECT " +
      "COUNT(*) FROM Albums WHERE name = ? AND owner_id = ?";
      
  private static final String ALBUM_ID_FROM_PIC = "SELECT "+
      "album_id FROM Pictures WHERE picture_id = ?";
      

  public Picture load(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
    try {
	  conn = DbConnection.getConnection();
   	  stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
      stmt.setInt(1, id);
			rs = stmt.executeQuery();
      if (rs.next()) {
        picture = new Picture();
        picture.setId(id);
        picture.setCaption(rs.getString(1));
        picture.setData(rs.getBytes(2));
        picture.setThumbdata(rs.getBytes(3));
        picture.setSize(rs.getLong(4));
        picture.setContentType(rs.getString(5));
      }

			rs.close();
			rs = null;
		
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return picture;
	}

	public void save(Picture picture) {
		PreparedStatement stmt = null;
		Connection conn = null;
	    ResultSet rs = null;
		AlbumDao albDao = new AlbumDao();
		int alb_id = albDao.nameToId(picture.getAlbumName());
		
		try {
		    conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(CHECK_ALBUM_NAME_STMT);
            stmt.setString(1, picture.getAlbumName());
            stmt.setInt(2, picture.getUploaderId());
            rs = stmt.executeQuery();
            if (!rs.next()) {
               // Theoretically this can't happen, but just in case...
               return;
            }
            int result = rs.getInt(1);
            if (result == 0) {
              // This album doesn't exist
              // or the album doesn't belong to this user
              return; 
            }
      
            try { stmt.close(); }
            catch (Exception e) { }
		
			stmt = conn.prepareStatement(SAVE_PICTURE_STMT);
			stmt.setInt(1,alb_id);
			stmt.setString(2, picture.getCaption());
			stmt.setBytes(3, picture.getData());
			stmt.setBytes(4, picture.getThumbdata());
			stmt.setLong(5, picture.getSize());
			stmt.setString(6, picture.getContentType());
			stmt.setInt(7, picture.getUploaderId());
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}

  public void deletePicture(int picId) throws SQLException{
  Connection conn = null;
  PreparedStatement stmt = null;
  try {
        NewUserDao userDao = new NewUserDao();
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(DEL_PICTURE_STMT);
		stmt.setInt(1, picId);
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
  

	public List<Integer> allPicturesIds() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_PICTURE_IDS_STMT);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return picturesIds;
	}
	
	public List<Integer> PictureIdsOfAlbum(int album_id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALBUM_PICTURE_IDS_STMT);
			stmt.setInt(1, album_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
			}

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}

		return picturesIds;
	}
	
	
	
    public int albumid(int thisid) throws SQLException {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
		    conn = DbConnection.getConnection();
		    stmt = conn.prepareStatement(ALBUM_ID_FROM_PIC);
		    stmt.setInt(1, thisid);
		    rs = stmt.executeQuery(); 
            int albid;

            if (!rs.next()) {
               // Theoretically this can't happen, but just in case...
               return -1;
            }

		    albid = rs.getInt(1);
		
		    rs.close();
		    stmt.close();
		    conn.close();
		    return albid;

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
