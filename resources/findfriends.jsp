<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.AlbumBean" %>
<%@ page import="photoshare.FriendsDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
<body>

    <h2><a href="index.jsp">Go Home!</a><h2>

    <%
        FriendsDao friends = new FriendsDao();
        String f_add_email = request.getParameter("add");
        if(f_add_email != null && !f_add_email.equals("")){
            friends.create(f_add_email, request.getUserPrincipal().getName());
            f_add_email = null;
        }
    %>

       

    <%
        NewUserDao userDao = new NewUserDao();
        int thisId = userDao.emailtoid(request.getUserPrincipal().getName());
        List<NewUserBean> users = friends.notMyFriends(thisId);
        for(NewUserBean user : users){
    %>
       <form action="findfriends.jsp" method="post">
       <%=user.getFirstName()%> <%=user.getLastName()%>
       <button type="submit" name="add" value=<%=user.getEmail()%> >ADD</button><br>
    <%}%>    
    </form>

</body>
</html>
