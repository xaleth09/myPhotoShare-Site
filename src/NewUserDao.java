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
public class NewUserDao {
  private static final String CHECK_EMAIL_STMT = "SELECT " +
      "COUNT(*) FROM Users WHERE email = ?";
      
  private static final String CHECK_AUTHORITY_STMT = "SELECT " +
      "role_name FROM Users WHERE email = ?";    

  private static final String NEW_USER_STMT = "INSERT INTO " + 
      "Users (email, password, first_name, last_name, dob) VALUES (?, ?, ?, ?, ?)";
                    
  private static final String EMAIL_TO_ID = "SELECT "+
      "user_id FROM Users WHERE email = ?";
      
  private static final String USER_FROM_ID = "SELECT "+
    "\"user_id\", \"first_name\", \"last_name\", \"email\" FROM users WHERE user_id = ?";
  
  private static final String ALL_USERS_STMT = "SELECT "+
      "\"user_id\", \"first_name\", \"last_name\", \"email\" FROM users";
      

  public boolean create(String email, String password, String firstName, String lastName, String dob) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(CHECK_EMAIL_STMT);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return false;
      }
      int result = rs.getInt(1);
      if (result > 0) {
        // This email is already in use
        return false; 
      }
      
      try { stmt.close(); }
      catch (Exception e) { }

      stmt = conn.prepareStatement(NEW_USER_STMT);
      stmt.setString(1, email);
      stmt.setString(2, password);
      stmt.setString(3, firstName);
      stmt.setString(4, lastName);
      stmt.setString(5, dob);
      
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
  
  
  public boolean isRegistered(String email){
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(CHECK_AUTHORITY_STMT);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return false;
      }
      String result = rs.getString(1);
      if (result.equals("RegisteredUser")) {
        // This is a registered user
        return true; 
      }else{
        //UnregisteredUser aka anonymous
        return false;
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
  
  
  	public List<NewUserBean> allUsers(int myid) throws SQLException {
	Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
	try {
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(ALL_USERS_STMT);
		rs = stmt.executeQuery(); 
		List<NewUserBean> allNames = new ArrayList<NewUserBean>();
        int thisid;
		while (rs.next()) {
		   if( (thisid = rs.getInt(1)) != myid){
			 NewUserBean tmp = new NewUserBean();
			 tmp.setId(thisid);
			 tmp.setFirstName(rs.getString(2));
			 tmp.setLastName(rs.getString(3));
			 tmp.setEmail(rs.getString(4));
			 allNames.add(tmp);
			}
		}
		rs.close();
		stmt.close();
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
  
  
    public int emailtoid(String email){
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(EMAIL_TO_ID);
      stmt.setString(1, email);
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
  
  	public NewUserBean userfromid(int thisid) throws SQLException {
	Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
	try {
		conn = DbConnection.getConnection();
		stmt = conn.prepareStatement(USER_FROM_ID);
		stmt.setInt(1, thisid);
		rs = stmt.executeQuery(); 
		NewUserBean user = new NewUserBean();

        if (!rs.next()) {
           // Theoretically this can't happen, but just in case...
           return user;
        }

		user.setId(rs.getInt(1));
   	    user.setFirstName(rs.getString(2));
	    user.setLastName(rs.getString(3));
		user.setEmail(rs.getString(4));
		
		rs.close();
		stmt.close();
		conn.close();
		return user;

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
