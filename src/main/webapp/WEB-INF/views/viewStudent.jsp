<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
 <!DOCTYPE html>
<html lang="en">


<head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
      
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
   <spring:url value="/resources/css/test.css" var="testCss" />
<link href="${testCss}" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    
        <title>Student Management System</title>
        <style>
        h2.col-md-6.offset-md-2.mb-5.mt-4 {
    color: black;
	}
        label.col-md-2.col-form-label {
    color: black;
	}
	.form-check-inline {
    color: black;
}
#testfooter{
  background:#f2f2f2;
    height: 60px;  
    text-align: center;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 20px 20px 0px 0px;
    position: fixed;
    width: 100%;
    float: bottom;
    color: black;
}
	.popup {
  width: 400px;
  background: #F8F9F9;
  position: fixed;
  top: 30%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  padding: 30px;
  visibility: hidden;
  color: black;
  transition: transform 0.4s, top 0.4s;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.open-popup { 
  visibility: visible;
}

.popup img {
  width: 100px;
  border-radius: 50%;
  margin-bottom: 20px;
}

.popup h2 {
  font-size: 24px;
  font-weight: 500;
  margin: 10px 0;
}

.popup button {
  width: 100%;
  padding: 10px 10;
  background: #6fd649;
  color: #fff;
  border: 0;
  outline: none;
  font-size: 20px;
  border-radius: 4px;
  cursor: pointer;
  box-shadow: 0 5px 5px rgba(0, 0, 0, 0.2);
}

  h2.col-md-6.offset-md-2.mb-5.mt-4 {
    color: black;
	}
     label.col-md-2.col-form-label {
    color: black;
	}
        </style>
       
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>
      <div class="main_contents">
        <jsp:include page="sidebar.jsp"></jsp:include>
        
        <c:choose>
        <c:when test="${user.role == 'admin'}">
    <div id="sub_content">
     <div class="row justify-content-left">
                <div class="col-md-5">
    <div class="card">
                <div class="card-body">
                	<div class="student-details" >
                	<p class="card-title text-black"><img src="data:image/jpeg;base64,${sutdent.photo}" alt="Student Photo" class="image"></p>
                	</div><br>
                	<div class="student-details">
                		<p class="card-text text-black">Student ID: ${sutdent.id}</p>
                	</div><br>
                	<div class="student-details">
                		 <p class="card-text text-black">Name: ${sutdent.name}</p>
                	</div><br>
                	<div class="student-details">
                		 <p class="card-text text-black">Date Of Birth: ${sutdent.dob}</p>
                	</div><br>
                	<div class="student-details">
                		<p class="card-text text-black">Gender: ${sutdent.gender}</p>
                	</div><br>
                	<div class="student-details">
                		 <p class="card-text text-black">Phone-Number: ${sutdent.phone}</p>
                	</div><br>
                	<div class="student-details">
                		<p class="card-text text-black">Education Background: ${sutdent.education}</p>
                	</div><br>
                	<div class="student-details">
                		<p class="card-text text-black">Courses:
                		<c:forEach var="course" items="${sutdent.courses}"> 
						${course.name},
					</c:forEach> 
           
                	</div><br>
                	
                	<div class="student-details  d-flex justify-content-center">
                		 
                		 <button  class="btn btn-secondary  "
							onclick="location.href = '/StudentRegistrationMvc/student/displayStudent'">
							Back</button>
                	</div>
                	
    
                </div>
       </div>
       </div>
        </div>    
   </div>
   </c:when>
   <c:otherwise>
   			 <div id="sub_content">
     <div class="row justify-content-left">
                <div class="col-md-5">
    <div class="card">
                <div class="card-body">
                	<div class="student-details" >
                	<p class="card-title text-black"><img src="data:image/jpeg;base64,${sutdent.photo}" alt="Student Photo" class="image"></p>
                	</div><br>
                	<div class="student-details">
                		<p class="card-text text-black">Student ID: ${sutdent.code}</p>
                	</div><br>
                	<div class="student-details">
                		 <p class="card-text text-black">Name: ${sutdent.name}</p>
                	</div><br>
                	<div class="student-details">
                		 <p class="card-text text-black">Date Of Birth: ${sutdent.dob}</p>
                	</div><br>
                	<div class="student-details">
                		<p class="card-text text-black">Gender: ${sutdent.gender}</p>
                	</div><br>
                	<div class="student-details">
                		 <p class="card-text text-black">Phone-Number: ${sutdent.phone}</p>
                	</div><br>
                	<div class="student-details">
                		<p class="card-text text-black">Education Background: ${sutdent.education}</p>
                	</div><br>
                	<div class="student-details">
                		<p class="card-text text-black">Courses:
                		<c:forEach var="course" items="${sutdent.courses}"> 
						${course.name},
					</c:forEach> 
           
                	</div><br>
                	
                	<div class="student-details  d-flex justify-content-center">
                		 <a href="/StudentRegistrationMvc/student/updateStudent/${sutdent.id}"><button  class="btn btn-primary ">Update</button></a>
                		 <button  class="btn btn-secondary  "
							onclick="location.href = '/StudentRegistrationMvc/student/displayStudent'">
							Back</button>
                	</div>
                	
    
                </div>
       </div>
       </div>
        </div>    
   </div>
   </c:otherwise>
   </c:choose>
</div>
        <div id="testfooter">
            <span>Copyright &#169; ACE Inspiration 2024</span>
        </div>
       
        <script>
            /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
            var dropdown = document.getElementsByClassName("dropdown-btn");
            var i;
            
            for (i = 0; i < dropdown.length; i++) {
              dropdown[i].addEventListener("click", function() {
              this.classList.toggle("active");
              var dropdownContent = this.nextElementSibling;
              if (dropdownContent.style.display === "block") {
              dropdownContent.style.display = "none";
              } else {
              dropdownContent.style.display = "block";
              }
              });
            }
            </script>
</body>

</html>
