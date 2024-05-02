<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link href="<c:url value="/resources/css/test.css" />" rel="stylesheet">
<title> Student Registration</title>
</head>
<body class="login-page-body"> 
  
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Welcome!</h1>
            <p>${error}</p>
          </div>
        </div>
       <form:form  class="login-form" action="/StudentRegistrationMvc/login" method="post" modelAttribute="login">
        
          <input type="text" name="email" placeholder="Enter mail" />
          
          <input type="password" name="password" value="" placeholder="Password" />
          <button>login</button>
          <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form:form>
      </div>
    </div>
</body>

</html>