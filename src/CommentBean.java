package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new user data
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class CommentBean {
  private int id = 0;
  private String comment = "";
  private String createdate = "";
  private int owner_id= 0;
  private int pic_id = 0;

  public int getId(){
    return this.id;
  }
  
  public String getComment() {
    return this.comment;
  }

  public String getCreateDate() {
    return this.createdate;
  }

  public int getOwnerId() {
    return this.owner_id;
  }
  public int getPicId() {
    return this.pic_id;
  }

  public void setPicId(int picid){
    this.pic_id = picid;
  }

  public void setOwnerId(int ownerid) {
    this.owner_id = ownerid;
  }

  public void setComment(String comm) {
    this.comment = comm;
  }
  
  public void setCreateDate(String date){
  this.createdate = date;
  }
}
