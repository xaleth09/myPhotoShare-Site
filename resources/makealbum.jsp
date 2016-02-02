<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.AlbumDao" %>

<html>
<head><title>Adding New Album</title></head>

<body>

<% 
   String err = null;
   String name  = request.getParameter("albumname");
   String createDate = request.getParameter("createdate");
   String owner_email = request.getUserPrincipal().getName();

 

   if (!name.equals("")) {
       
       // We have valid inputs, try to create the user
       AlbumDao albDao = new AlbumDao();
       boolean success = albDao.create(name, createDate, owner_email);
       if (!success) {
         err = "Couldn't create user (that email may already be in use)";
       }
   } else {
	 err = "You have to provide an album name";
   }
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<p> <a href="newuser.jsp">Go Back</a>
<% }
   else { %>

<h2>Success!</h2>

<p>A new album has been created with album name: <%= name %>.
You can now return to <a href="reguser.jsp">your page</a>.

<% } %>

</body>
</html>
