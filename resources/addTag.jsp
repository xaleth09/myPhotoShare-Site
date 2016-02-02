<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.CommentDao" %>
<%@ page import="photoshare.CommentBean" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.TagDao"%>
<%@ page import="photoshare.TagBean"%>


<html>
<head><title>Adding New Tag</title></head>

<body>

<% 
   String err = null;
   String tag  = request.getParameter("tagtext");
   int pic_id = 0;
   String tagpicId = request.getParameter("picid");
   if(tagpicId!=null && !tagpicId.equals("")){
    pic_id = Integer.parseInt(tagpicId);
   }

   NewUserDao userDao = new NewUserDao();
   int owner_id = userDao.emailtoid(request.getUserPrincipal().getName());


   if (!tag.equals("") || !tag.equals("Enter text here...")) {
    
      String [] res = tag.split(",");
      TagDao tagDao = new TagDao();
      boolean success = false;
      for(int i = 0; i < res.length; i++){
        success = tagDao.tagOn(res[i], pic_id);
      }
       
       if (!success) {
         err = "Couldn't create tags (no text entered)";      }
   } else {
	 err = "You have to provide tag(s)";
   }
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<p> <a href="index.jsp">Go Back</a>
<% }
   else { %>

<h2>Success!</h2>

<p>You've added your tag(s).
You can now return to the <a href="tag.jsp?picid=<%=pic_id%>">tag page</a>.

<% } %>

</body>
</html>
