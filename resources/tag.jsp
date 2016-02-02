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
<%@ page import="photoshare.TagDao" %>
<%@ page import="photoshare.TagBean"%>

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
   TagDao tagDao = new TagDao();
   int pic_id = 0;
   String compicId = request.getParameter("picid");
   if(compicId!=null && !compicId.equals("")){
    pic_id = Integer.parseInt(compicId);
   }
%>
<h2>Tags for PIC:<%=pic_id%>%></h2>
<h3>tags:</h3>
    


  <h3>Add a comment:</h3>
  <form action="addTag.jsp?picid=<%=pic_id%>" method="post" id="tagForm">
      Today's Date:    <input type="text" name="date"/><br>
      <input type="submit" value="Add"/>
  </form>
  
  <textarea name="tagsarea" form="tagForm">Enter tags here seperated by commas (,)</textarea>


<h3> Comments </h3>

<%
    List<TagBean> tags = tagDao.allTags(pic_id);
    for(TagBean tag : tags){
%>
    <%=tag.getTag()%><br>
<%
}
%>

</body>
</html>


