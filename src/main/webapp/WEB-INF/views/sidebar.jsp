<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:choose>
	<c:when test="${user.role == 'admin'}">
    <div class="sidenav">

        <button class="dropdown-btn">
            Class Management <i class="fa fa-caret-down"></i>
        </button>

        <div class="dropdown-container">
            <a href="/StudentRegistrationMvc/course/">Course Registration</a>
             <a href="/StudentRegistrationMvc/course/displayCourse">Display Course</a>
            <a href="/StudentRegistrationMvc/student/displayStudent">Student Search</a>
            <a href="/StudentRegistrationMvc/student/displayOldStudent">Old Students</a>
        </div>

        <button class="dropdown-btn">
            User Management <i class="fa fa-caret-down"></i>
        </button>

        <div class="dropdown-container">
            <a href="/StudentRegistrationMvc/user/">User Registration</a>
            <a href="/StudentRegistrationMvc/user/displayUser">Display User</a>
            <a href="/StudentRegistrationMvc/user/profile">Profile</a>
        </div>

    </div>
</c:when>
<c:otherwise>
	  <div class="sidenav">

        <button class="dropdown-btn">
            Class Management <i class="fa fa-caret-down"></i>
        </button>

        <div class="dropdown-container">
            <a href="/StudentRegistrationMvc/student/">Student Registration</a>
            <a href="/StudentRegistrationMvc/student/displayStudent">Student Search</a>
        </div>
       
        </div>

</c:otherwise>
	</c:choose>
</body>
</html>