<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewUserDao" %>

<html>
<head><title>Adding New User</title></head>

<body>

<% 
   String err = null;
   String email  = request.getParameter("email");
   String password1 = request.getParameter("password1");
   String password2 = request.getParameter("password2");
   String firstName = request.getParameter("FirstName");
   String lastName = request.getParameter("LastName");
   String dob = request.getParameter("DoB");

   if (!email.equals("")) {
     if (!password1.equals(password2)) {
       err = "Both password strings must match";

     }
     else if (password1.length() < 4) {
       err = "Your password must be at least four characters long";
     }
     else if(firstName.equals("")){
       err = "please enter your first name";
     }
     else if(lastName.equals("")){
       err = "Please enter your last name";
     }else if(dob.length() < 8){
       err = "Please enter DoB in the format mmddyyyy";
     }
     else {
       // We have valid inputs, try to create the user
       NewUserDao newUserDao = new NewUserDao();
       boolean success = newUserDao.create(email, password1, firstName, lastName, dob);
       if (!success) {
         err = "Couldn't create user (that email may already be in use)";
       }
     }
   } else {
	 err = "You have to provide an email";
   }
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<p> <a href="newuser.jsp">Go Back</a>
<% }
   else { %>

<h2>Success!</h2>

<p>A new user has been created with email <%= email %>.
You can now return to the <a href="index.jsp">login page</a>.

<% } %>

</body>
</html>
