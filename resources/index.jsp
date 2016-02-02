<%@ page import="photoshare.NewUserBean" %>
<%@ page import="photoshare.NewUserDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<body>

    <% 
    String email = request.getUserPrincipal().getName(); 
    NewUserDao test = new NewUserDao();
    if(test.isRegistered(email)){
        response.sendRedirect("reguser.jsp");
    }else{
        response.sendRedirect("unreguser.jsp");
    }
    
    %>

</body>
</html>
