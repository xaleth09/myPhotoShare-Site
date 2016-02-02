<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.CommentDao" %>
<%@ page import="photoshare.CommentBean" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>


<html>
<head><title>Adding New User</title></head>

<body>

<% 
   String err = null;
   String date  = request.getParameter("date");
   String comm = request.getParameter("comment");
   int pic_id = 0;
   String compicId = request.getParameter("picid");
   if(compicId!=null && !compicId.equals("")){
    pic_id = Integer.parseInt(compicId);
   }

   NewUserDao userDao = new NewUserDao();
   int owner_id = userDao.emailtoid(request.getUserPrincipal().getName());


   if (!comm.equals("") || !comm.equals("Enter text here...")) {
    
     
      CommentDao comDao = new CommentDao();
       boolean success = comDao.commentOn(comm, date, pic_id, owner_id);
       if (!success) {
         err = "Couldn't create comment (dunno why)";
        }
   } else {
	 err = "You have to provide an text for the comment";
   }
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<p> <a href="index.jsp">Go Back</a>
<% }
   else { %>

<h2>Success!</h2>

<p>You've added your comment.
You can now return to the <a href="comment.jsp?picid=<%=pic_id%>">comment page</a>.

<% } %>

</body>
</html>
