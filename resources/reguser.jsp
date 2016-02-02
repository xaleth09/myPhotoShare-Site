<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.FriendsDao"%>
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
    <%
        NewUserDao userDao = new NewUserDao();
        int owner_id = userDao.emailtoid(request.getUserPrincipal().getName());
        AlbumDao albDao = new AlbumDao();
        PictureDao pictureDao = new PictureDao();  
        FriendsDao friendDao = new FriendsDao();
       
        String f_del_email = request.getParameter("deleteFriend");        
        if(f_del_email != null && !f_del_email.equals("")){
            friendDao.deleteFriend(f_del_email, request.getUserPrincipal().getName());
            f_del_email = null;
        }
        
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
        
          String tagPicStr = request.getParameter("tagPic");
        if(tagPicStr != null && !tagPicStr.equals("")){
            int tagpicId = Integer.parseInt(tagPicStr);
            response.sendRedirect("tag.jsp?picid="+tagpicId);
        }
        
        
        LikeDao likeDao = new LikeDao();
        String likestr = request.getParameter("likePic");
        if(likestr != null && !likestr.equals("")){
          int likepicId = Integer.parseInt(likestr);
          if(likeDao.didiLike(likepicId,owner_id)==0){ 
            likeDao.iLike(likepicId, owner_id);
          }
        }
          
    %>
<body>
<h1>A skeleton photo sharing application for CS460/660 PA1</h1>

<h2>Hello <b><code><%= request.getUserPrincipal().getName()%></code></b>, click here to
<a href="/photoshare/logout.jsp">log out</a></h2>

<h3>Browse all <a href="/photoshare/unreguser.jsp"> Albums and Photos</a><h3>

<h2>Friends List</h2>
<h3><a href="findfriends.jsp">Find Friends!</a></h3><br>

    <% 
    FriendsDao friendsDao = new FriendsDao();
    List<NewUserBean> friends = friendsDao.myFriendsList(owner_id);
    %>
    
    <form action="reguser.jsp" method="post">
    <%
    for(NewUserBean friend : friends){
    %>
    
    <strong><a href="friendpage.jsp?friendid=<%= friend.getId() %>"><%= friend.getFirstName() %></a></strong>: 
    <button type="submit" name="deleteFriend" value=<%=friend.getEmail()%> >Del</button> &nbsp;
    <%}%>
    </form>

<h2>Create New Album</h2>

<form action="makealbum.jsp" method="post">
    Album Name: <input type="text" name="albumname"><br>
    Todays Date: <input type="text" name="createdate">
    <input type="submit" value="Create"/><br/>
</form>

<h2>Upload a new picture</h2>

<form action="reguser.jsp" enctype="multipart/form-data" method="post">
    Album Name: <input type="text" name="albname"><br>
    Filename: <input type="file" name="filename"/>
    <input type="submit" value="Upload"/>
</form>


<%
    try {
        Picture picture = imageUploadBean.upload(request);
        if (picture != null) {
            pictureDao.save(picture);
        }
    } catch (FileUploadException e) {
        e.printStackTrace();
    }
%>


<h2> Your Albums</h2>

<form action="reguser.jsp" method="post">
<%
    List<AlbumBean> myAlbums = albDao.allMyAlbums(owner_id);
    for(AlbumBean alb : myAlbums){
%>
    <strong><a href="thisAlbum.jsp?albumid=<%= alb.getAlbId() %>"><%= alb.getName() %></a></strong>:
    <button type="submit" name="deleteAlbum" value=<%= alb.getAlbId()%> >Del</button> &nbsp;
<%}%>
</form>

<h2> All other users' Albums</h2>

<%
    List<AlbumBean> myAlbums2 = albDao.allAlbums(owner_id);
    for(AlbumBean alb2 : myAlbums2){
%>


<a href="thisAlbum.jsp?albumid=<%= alb2.getAlbId() %>"><%= alb2.getName() %></a>

<%}%>


<h2>Existing pictures</h2>
<table>
    <tr>
    <form action="reguser.jsp" method="post">
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
    </form>
    </tr>
</table>

</body>
</html>
