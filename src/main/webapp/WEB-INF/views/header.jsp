<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.modal {
  display: none; 
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  padding-top: 50px;
}

/* Modal Content/Box */
.modal-content {
  background-color: #fefefe;
  border: 1px solid #888;
  position: absolute;
 top: 50%;
 left: 50%;
 transform: translate(-50%, -50%);
  width: 80%;
  max-width: 300px; /* Could be more or less, depending on screen size */
}

/* Style the horizontal ruler */
hr {
  border: 1px solid #f1f1f1;
  margin-bottom: 15px;
}
 
/* The Modal Close Button (x) */
.close {
  position: absolute;
  right: 10px;
  top: 10px;
  font-size: 24px;
  font-weight: bold;
  color: #f1f1f1;
}

.close:hover,
.close:focus {
  color: #f44336;
  cursor: pointer;
}


.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

/* Change styles for cancel button and delete button on extra small screens */
@media screen and (max-width: 300px) {
  .cancelbtn, .deletebtn {
     width: 100%;
  }
}

.container-logout {
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  max-width: 400px;
  margin: 0 auto;
  
}

h4 {
	color:red;
  font-size: 20px;
  margin-top: 0;
   text-align: center;
   
}

h6 {
	color:black;
  margin-bottom: 20px;
   text-align: center;
}
p {
	color:red;
  margin-bottom: 20px;
  text-align: center;
}

.cancelbtn {
  background-color: #4caf50; /* Confirm color */
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  
  justify-content: center;
}

.deletebtn {
  background-color: #f44336; /* Warning color */
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  
  justify-content: center;
}


.cancelbtn:hover, .deletebtn:hover {
  opacity: 0.8;
}  	
	
</style>
</head>
<body>
	<div id="testheader">
		<div class="container">
			<div class=row>
				<div class="col-md-5">
					<a href="#" ><h3>Student Registration</h3></a>
				</div>
				<div class="col-md-5">
					<h6>
						User : ${user.name}</h6>
					<h6>Current Date :<%= LocalDate.now() %></h6>
				</div>
				<div class="col-md-1">
					<a href="#" onclick="document.getElementById('id').style.display='block'"><button type="button" class="btn btn-secondary">Logout</button></a>	
				</div>
				<div id="id" class="modal">
			           <div class="container-logout">  
			              <h4>LogOut</h4>
			              <p>Are you sure you want to logout this page?</p>
			              <div style="text-align: center;">
			                  <button type="button" onclick="document.getElementById('id').style.display='none'" class="cancelbtn">No</button>
			                 <a href="/StudentRegistrationMvc/"> <button type="button" class="deletebtn">Yes</button></a>
			              </div>
			        </div> 
			      </div>
				
				
			</div>
		</div>

	</div>  
	<script type="text/javascript">
function openFunction(){
document.getElementById("menu").style.width="300px";
document.getElementById("mainbox").style.marginleft="300px";
document.getElementById("mainbox").innerHTML="";
}
function closeFunction(){
document.getElementById("menu").style.width="0px";
document.getElementById("mainbox").style.marginleft="0px";
document.getElementById("mainbox").innerHTML="&#9776;";
}
var slideIndex = 0;

</script>   
</body>
</html>