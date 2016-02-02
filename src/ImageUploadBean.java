package photoshare;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles file uploads
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class ImageUploadBean {
  public Picture upload(HttpServletRequest request) throws FileUploadException {
    RequestContext requestContext = new ServletRequestContext(request);
    if (FileUpload.isMultipartContent(requestContext)) {
      Picture picture = new Picture();
      FileItemFactory fileItemFactory = new DiskFileItemFactory();
      ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
      List<FileItem> fileItems = servletFileUpload.parseRequest(request);

      for (FileItem fileItem : fileItems) {
        if (fileItem.isFormField()) {
          if (fileItem.getFieldName().equals("caption")) {
            picture.setCaption(fileItem.getString());
          }
          if(fileItem.getFieldName().equals("albname")){
            picture.setAlbumName(fileItem.getString());
            NewUserDao userDao = new NewUserDao();
            int uperId = userDao.emailtoid(request.getUserPrincipal().getName());
            picture.setUploaderId(uperId);
          }
          
        } else {
          if (fileItem.getFieldName().equals("filename")) {
            picture.setContentType(fileItem.getContentType());
            picture.setSize(fileItem.getSize());
            picture.setData(fileItem.get());
  
          }
        }
      }

      picture.createThumbnail();
      return picture;
    } else {
      return null;
    }
  }
  
  
  public Picture upload(HttpServletRequest request, String albname, int uperid) throws FileUploadException {
    RequestContext requestContext = new ServletRequestContext(request);
    if (FileUpload.isMultipartContent(requestContext)) {
      Picture picture = new Picture();
      FileItemFactory fileItemFactory = new DiskFileItemFactory();
      ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
      List<FileItem> fileItems = servletFileUpload.parseRequest(request);

      for (FileItem fileItem : fileItems) {
        if (fileItem.isFormField()) {
          if (fileItem.getFieldName().equals("caption")) {
            picture.setCaption(fileItem.getString());
          }
          if(fileItem.getFieldName().equals("albname")){
            picture.setAlbumName(fileItem.getString());
            NewUserDao userDao = new NewUserDao();
            int uperId = userDao.emailtoid(request.getUserPrincipal().getName());
            picture.setUploaderId(uperId);
          }
          
        } else {
          if (fileItem.getFieldName().equals("filename")) {
            picture.setUploaderId(uperid);
            picture.setAlbumName(albname);
            picture.setContentType(fileItem.getContentType());
            picture.setSize(fileItem.getSize());
            picture.setData(fileItem.get());
  
          }
        }
      }

      picture.createThumbnail();
      return picture;
    } else {
      return null;
    }
  }
  
}
