package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new user data
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class TagBean {
  private String tag = "";

  public String getTag(){
    return this.tag;
  }
  
  public void setTag(String in){
    this.tag = in;
  }
 
}
