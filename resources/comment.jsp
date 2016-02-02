<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.FriendsDao"%>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.AlbumBean" %>
<%@ page import="photoshare.CommentDao" %>
<%@ page import="photoshare.CommentBean" %>
<%@ page import="photoshare.LikeDao" %>

<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>
<html>
<head><title>Comments</title></head>


<body>
<%
   AlbumDao albDao = new AlbumDao();
   PictureDao picDao = new PictureDao();
   LikeDao likeDao = new LikeDao();
   CommentDao comDao = new CommentDao();
   NewUserDao userDao = new NewUserDao();
   int pic_id = 0;
   String compicId = request.getParameter("picid");
   if(compicId!=null && !compicId.equals("")){
    pic_id = Integer.parseInt(compicId);
   }
%>
<h2>Comments for PIC:<%=pic_id%> in  ALBUM: <%=picDao.albumid(pic_id)%></h2>
<h3>People who like this photo are:</h3>
<%
    List<NewUserBean> users = likeDao.whoLikesThis(pic_id);
    for(NewUserBean user : users){
%>    
    <strong><a href="friendpage.jsp?friendid=<%= user.getId() %>"><%= user.getFirstName() %></a></strong>&nbsp;

<%  }%>

  <h3>Add a comment:</h3>
  <form action="addcomment.jsp?picid=<%=pic_id%>" method="post" id="comForm">
      Today's Date:    <input type="text" name="date"/><br>
      <input type="submit" value="Add"/>
  </form>
  
  <textarea name="comment" form="comForm">Enter text here...</textarea>


<h3> Comments </h3>

<%
    List<CommentBean> coms = comDao.allComments(pic_id);
    for(CommentBean com : coms){
    NewUserBean usr = userDao.userfromid(com.getOwnerId());
%>
    <%=com.getComment()%><br>
    <%=usr.getFirstName()%> , <%=com.getCreateDate()%><br><br>
<%
}
%>

</body>
</html>


