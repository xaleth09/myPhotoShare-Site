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

<h2><a href="index.jsp">Go Home!</a></h2>


<%

   AlbumDao albDao = new AlbumDao();
   PictureDao pictureDao = new PictureDao();
   int albumid = Integer.parseInt(request.getParameter("albumid"));
   AlbumBean thisAlbm = albDao.albumfromid(albumid);
   String albName = thisAlbm.getName();
   NewUserDao curUserDao = new NewUserDao();
   LikeDao likeDao = new LikeDao();
   
   int curId = curUserDao.emailtoid(request.getUserPrincipal().getName());
   boolean myAlbum = false;
   if(curId == thisAlbm.getOwnerId()){
    myAlbum = true;
   }

    try {
        Picture picture = imageUploadBean.upload(request, albName, curId);
        if (picture != null) {
            picture.setAlbumId(albumid);
            picture.setUploaderId(curId);
            pictureDao.save(picture);
        }
    } catch (FileUploadException e) {
        e.printStackTrace();
    }
     
    String picIdstr = request.getParameter("deletePic");
    if(picIdstr != null && !picIdstr.equals("")){
      int delpicId = Integer.parseInt(picIdstr);
      pictureDao.deletePicture(delpicId);
    }
    
    String likestr = request.getParameter("likePic");
    if(likestr != null && !likestr.equals("")){
      int likepicId = Integer.parseInt(likestr);
      if(likeDao.didiLike(likepicId,curId)==0){ 
        likeDao.iLike(likepicId, curId);
      }
    }
    


if(myAlbum){   
%>
  <h2>Upload a new picture</h2>

  <form action="thisAlbum.jsp?albumid=<%= albumid%>" enctype="multipart/form-data" method="post">
      Filename: <input type="file" name="filename"/>
      <input type="submit" value="Upload"/>
  </form>

<%}%>



<h2>Pictures in album: <%= albName%>
<%if(myAlbum){%>
  <form action="reguser.jsp" method="post">
  <button type="submit" name="deleteAlbum" value=<%= albumid%> >Delete Album</button> &nbsp;
  </form>
<%}%>
</h2>
   <form action="thisAlbum.jsp?albumid=<%= albumid%>" method="post">
        <%
        List<Integer> pictureIds = pictureDao.PictureIdsOfAlbum(albumid);
        for (Integer pictureId : pictureIds) {
        int numLikes = likeDao.howManyLikes(pictureId);
        %>
            <a href="/photoshare/img?picture_id=<%= pictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/></a>
        

          <span style="display: inline-block; width: 65px; vertical-align: top;">
            <button type="submit" name="likePic" value=<%= pictureId%>>Likes: <%=numLikes%></button>
        <%if(!myAlbum){%>    
            <button type="submit" name="commentPic" value=<%= pictureId%>>Comment</button>
        <%}%>    
        <%if(myAlbum){%> 
            <button type="submit" name="tagPic" value=<%= pictureId%>>Tag</button>
            <button type="submit" name="deletePic" value=<%= pictureId%>>Del</button>
        <%}%>        
          </span>&nbsp;
        <%
        }
        %>

   </form>
</body>
</html>









