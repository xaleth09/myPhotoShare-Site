package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new user data
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class AlbumBean {
  private int id = 0;
  private String name = "";
  private String createdate = "";
  private int owner_id= 0;
  private String owner_email = "";

  public int getAlbId(){
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }

  public String getCreateDate() {
    return this.createdate;
  }

  public int getOwnerId() {
    return this.owner_id;
  }
  
  public String getOwnerEmail() {
    return this.owner_email;
  }

  public void setAlbId(int albid){
    this.id = albid;
  }

  public void setOwnerEmail(String email) {
    this.owner_email = email;
  }

  public void setOwnerId(int ownerid) {
    this.owner_id = ownerid;
  }

  public void setName(String albname) {
    this.name = albname;
  }
  
  public void setCreateDate(String date){
  this.createdate = date;
  }
}
