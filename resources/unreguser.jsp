<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.AlbumBean" %>
<%@ page import="photoshare.LikeDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
<head><title>Photo Sharing</title></head>

<body>
<h1>A skeleton photo sharing application for CS460/660 PA1</h1>

Hello <b><code><%= request.getUserPrincipal().getName()  %></code></b>, click here to
<a href="/photoshare/logout.jsp">log out</a>
<br>
<%
    AlbumDao albDao = new AlbumDao();
    PictureDao pictureDao = new PictureDao();
    NewUserDao userDao = new NewUserDao();
    int owner_id = userDao.emailtoid(request.getUserPrincipal().getName());
    
    
        String del_album_id = request.getParameter("deleteAlbum");
        if(del_album_id != null && !del_album_id.equals("")){
            int albId = Integer.parseInt(del_album_id);
            albDao.delAlbum(albId);
        }
        
        String comPicStr = request.getParameter("commentPic");
        if(comPicStr != null && !comPicStr.equals("")){
            int compicId = Integer.parseInt(comPicStr);
            response.sendRedirect("comment.jsp?picid="+compicId);
        }
    
    LikeDao likeDao = new LikeDao();
    String likestr = request.getParameter("likePic");
    if(likestr != null && !likestr.equals("")){
      int likepicId = Integer.parseInt(likestr);
      if(likeDao.didiLike(likepicId,owner_id)==0){ 
        likeDao.iLike(likepicId, owner_id);
      }
    }
    
    List<NewUserBean> users = userDao.allUsers(owner_id);
    if(!userDao.isRegistered(request.getUserPrincipal().getName())){
%>
    <h2>These could be your friends if you register an account!</h2><br>
<%
    }else{
%>

    <h2><a href="index.jsp">Go Home!</a></h2>
<%
    }
%>
    All Users: 
    <br> 
<%
    for(NewUserBean user : users){    
%>
    <strong><a href="friendpage.jsp?friendid=<%= user.getId() %>"><%= user.getFirstName() %></a></strong>&nbsp;    
<% 
     }
%>

<h2>All Albums</h2>
<%
    List<AlbumBean> albums = albDao.allAlbums();
    for(AlbumBean alb : albums){
%>

 <strong><a href="thisAlbum.jsp?albumid=<%=alb.getAlbId() %>"><%= alb.getName() %></a></strong>&nbsp; 

<%}%>

<h2>Existing pictures</h2>
<table>
    <tr>
    <form action="unreguser.jsp" method="post">
        <%
            
            List<Integer> pictureIds = pictureDao.allPicturesIds();
            
            for (Integer pictureId : pictureIds) {
            int albid = pictureDao.albumid(pictureId);
            int numLikes = likeDao.howManyLikes(pictureId);
            AlbumBean picAlb = albDao.albumfromid(albid);
            boolean myPic = picAlb.getOwnerId() == owner_id;
        %>
            <a href="/photoshare/img?picture_id=<%= pictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/></a>
        

            <span style="display: inline-block; width: 65px; vertical-align: top;">
            <button type="submit" name="likePic" value=<%= pictureId%>>Likes: <%=numLikes%></button>
           
        <%if(!myPic){%>    
            <button type="submit" name="commentPic" value=<%= pictureId%>>Comment</button>
        <%}%>    
        <%if(myPic){%> 
            <button type="submit" name="tagPic" value=<%= pictureId%>>Tag</button>
            <button type="submit" name="deletePic" value=<%= pictureId%>>Del</button>
        <%}%>        
          </span> &nbsp;
        <%
            }
        %>
    </tr>
</table>

</body>
</html>

