<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
    
        <title>Course Registration</title>
       <style>
        h2.col-md-6.offset-md-2.mb-3.mt-4 {
            color: black;
        }
        label.col-md-2.col-form-label {
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
		
</style>
 
 
</head>

<body> 

<jsp:include page="header.jsp"></jsp:include>
 <div class="main_contents">
        <jsp:include page="sidebar.jsp"></jsp:include>
        
         <div id="sub_content">
        <form:form action="/StudentRegistrationMvc/user/addUser" method="post" modelAttribute="user">
    <h2 class="col-md-6 offset-md-2 mb-3 mt-4">User Registration</h2>
      <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-6">
            <div style="color: red" class="alert alert-danger ${error == null ? 'd-none' : ''}">${error}</div>
        </div>
    </div>      
    <div class="row mb-4">
        <div class="col-md-2"></div>
        <label for="name" class="col-md-2 col-form-label">Name</label>
        <div class="col-md-4">
            <form:input path="name" class="form-control" placeholder="User Name" value="${name}"/>
        </div>
    </div>
    
    <div class="row mb-4">
        <div class="col-md-2"></div>
        <label for="email" class="col-md-2 col-form-label">Email</label>
        <div class="col-md-4">
            <form:input path="email" id="email" class="form-control" placeholder="Enter Email" value="${email }"/>
            
        </div>
        
        
    </div>
    
    <div class="row mb-4">
        <div class="col-md-2"></div>
        <label for="password" class="col-md-2 col-form-label">Password</label>
        <div class="col-md-4">
            <form:password path="password" id="password" class="form-control" placeholder="Enter Password" value="${password }"/>
            
        </div>
    </div>
    
    <div class="row mb-4">
        <div class="col-md-2"></div>
        <label for="role" class="col-md-2 col-form-label">User Role</label>
        <div class="col-md-4">
            <form:select path="role" id="role" class="form-select" aria-label="Education">
                
                <form:option value="user">User</form:option>
            </form:select>
        </div>
    </div>
    
    <div class="row mb-4">
        <div class="col-md-4"></div>
        <div class="col-md-6">              
            <button type="submit" class="btn btn-secondary col-md-2">Add</button>
        </div>
    </div>
</form:form>
             
             <div class="popup" id="popup">
		                <img src="https://www.shutterstock.com/image-vector/green-check-mark-icon-circle-260nw-576516469.jpg">
		                <h2>Thank You!</h2>
		                <p>Register Successful</p>
		                 
		                <a href="/StudentRegistrationMvc/user/displayUser"><button type="button">Ok</button></a>
           		  </div>
             
           </div>
          
          </div>
          <div id="testfooter">
		<span>Copyright &#169; ACE Inspiration 2024</span>
	</div>
          
          <script>
		
		 const popup = document.getElementById("popup");
	    //var result=0;
	    var result = ${result};
	    const openPopup = ()=>{
	      popup.classList.add("open-popup");
	    }
	  
	    
	    if(result === 1 ){
	      openPopup();
	    }
		
		
	</script>
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

    
