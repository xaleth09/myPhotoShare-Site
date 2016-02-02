package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new user data
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class NewUserBean {
  private String email = "";
  private String password1 = "";
  private String password2 = "";
  private String first_name = "";
  private String last_name = "";
  private int user_id;

  public String saySomething() {
    System.out.println("Hello!");
    return "Test";
  }
  
  public String getEmail() {
    return email;
  }

  public String getPassword1() {
    return password1;
  }

  public String getPassword2() {
    return password2;
  }
  
  public String getFirstName(){
    return first_name;
  }
  
  public String getLastName(){
    return last_name;
  }
  
  public int getId(){
    return user_id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }
  
  public void setFirstName(String first){
    this.first_name = first;
  }
  
  public void setLastName(String last){
    this.last_name = last;
  }
  
  public void setId(int id){
    this.user_id = id;
  }
}
