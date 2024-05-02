<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
   <spring:url value="/resources/css/test.css" var="testCss" />
<link href="${testCss}" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Course Registration</title>
   <style>
   #studentTable_wrapper {
    width: 80%;
    overflow-x: auto;
}

.dataTables_filter {
 margin-bottom: 10px;
    position: absolute;
    top: 10px; /* Adjust top positioning */
    left: 80%; /* Move filter to the center horizontally */
    transform: translateX(-50%); /* Center horizontally */
    width: 50%; /* Set width to half of the screen */
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

            <table class="table table-striped" id="studentTable">
                <thead>
                    <tr>
                    	<th scope="col">#</th>
                        <th scope="col">User Name</th>
                        <th scope="col">User Email</th>
                         <th scope="col">Role</th>
                        <th scope="col">Actions</th>
                       
                    </tr>
                </thead>
                <tbody>
                 <%int count = 1; %>
                    <c:forEach var="user" items="${users}">
                        <c:if test="${user.name != null}">
                            <tr>
                            	<th scope="row"><%=count ++ %></th>
                                <td>${user.name}</td>
                                <td>${user.email}</td>
                                 <td>${user.role}</td>
                                <td >
                                	 <a href=" /StudentRegistrationMvc/user/updateUser/${user.id}"><button type="button" class="btn btn-secondary">Update</button></a>
                                	 <button type="button" class="btn btn-secondary" onclick="openDeleteModal(${user.id})">Delete</button>
                                	
                                </td>
                            
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
            <div id="deleteModal" class="modal">
    <div class="container-delete">    
        <h3>Delete Item</h3>
        <p>Are you sure you want to delete this item?</p>
        <div style="text-align: center;">
            <button type="button" onclick="closeDeleteModal()" class="cancelbtn">No</button>
            <button id="confirmDeleteBtn" type="button" class="deletebtn">Yes</button>
        </div>
    </div> 
</div>
            
        </div>
    </div>
  

    <div id="testfooter">
        <span>Copyright &#169; ACE Inspiration 2024</span>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
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
      
        fetch('deleteUser/' + userIdToDelete, {
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
