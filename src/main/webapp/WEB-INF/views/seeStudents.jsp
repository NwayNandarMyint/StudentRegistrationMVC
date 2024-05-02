<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <spring:url value="/resources/css/test.css" var="testCss" />
	<link href="${testCss}" rel="stylesheet">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
     <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">

    <title>Course Registration</title>

    <style>
        img {
            
            width: 140px;
            height: 80px;
        }
        /* Custom styles for DataTable */

#studentTable_wrapper {
    width: 80%;
    overflow-x: auto;
}

.dataTables_filter {
    margin-bottom: 10px;
    
    position: absolute;
  margin-bottom:10px; /* Adjust top positioning */
    left: 40%; /* Move filter to 3/4 of the screen */
    width: 50%; /* Set width to half of the screen */
} 

 .modal {
  display: none; /* Hidden by default */
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

/* Clear floats */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
}


@media screen and (max-width: 300px) {
  .cancelbtn, .deletebtn {
     width: 100%;
  }
}

.container-delete {
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  max-width: 400px;
  margin: 0 auto;
}

h3 {
  font-size: 20px;
  margin-top: 0;
   text-align: center;
}

p {
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
}

.deletebtn {
  background-color: #f44336; /* Warning color */
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}


.cancelbtn:hover, .deletebtn:hover {
  opacity: 0.8;
}
 

</style>
    
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>

    <div class="main_contents">
        <jsp:include page="sidebar.jsp"></jsp:include>
        
        <div id="sub_content">
        
            <div>
                <table class="table table-striped width:30%" id="studentTable">
                    <thead>
                        <tr>
                           	
                            <th scope="col">Student ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Education</th>
                             <th scope="col">Birthday</th>
                             <th scope="col">Gender</th>
                             <th scope="col">Phone</th>
                             <th scope="col">Attend</th>
                               <th scope="col">Student Photo</th>
                                <th scope="col"><button  class="btn btn-secondary  "
							onclick="location.href = '/StudentRegistrationMvc/course/displayCourse'">
							Back</button></th>
                               
                            
                        </tr>
                    </thead>
                    <tbody>

                      
                        <c:forEach items="${sutdents}" var="student">
                            <tr>
                               	
                                <td>${student.code }</td>
                                <td>${student.name }</td>
                                <td>${student.education }</td>
                                <td>${student.dob }</td>
                                <td>${student.gender }</td>
                                 <td>${student.phone }</td>
                                <td><c:forEach var="course" items="${student.courses}"> 
										${course.name},
										</c:forEach></td>
                               <td><img src="data:image/jpeg;base64,${student.photo}" alt="Student Photo" class="image" style="width: 100px; height: 60px;" ></td>
                         		<td></td>

                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
                    
                
                  
            </div>
        </div>
       
        
    </div>

    <div id="testfooter">
        <span>Copyright &#169; ACE Inspiration 2024</span>
    </div>



<!-- Include jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- Include DataTables JS -->
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>

<script>
    $(document).ready(function() {
        $('#studentTable').DataTable();
    });
</script>
<script>
    var userIdToDelete;

    function openDeleteModal(id) {
    	userIdToDelete = id;
        var modal = document.getElementById('deleteModal');
        modal.style.display = "block";
    }

    function closeDeleteModal() {
        var modal = document.getElementById('deleteModal');
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        var modal = document.getElementById('deleteModal');
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    document.getElementById('confirmDeleteBtn').addEventListener('click', function() {
      
        fetch('/StudentRegistrationMvc/student/deleteStudent/' + userIdToDelete, {
            method: 'GET'
        })
        .then(response => {
            if (response.ok) {
               
                location.reload(); 
            } else {
               
                console.error('Failed to delete ticket');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    });
</script>
    <script>
        /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
        var dropdown = document.getElementsByClassName("dropdown-btn");
        var i;

        for (i = 0; i < dropdown.length; i++) {
            dropdown[i].addEventListener("click", function () {
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
