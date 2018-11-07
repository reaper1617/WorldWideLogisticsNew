<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>User change page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/web/css/adminmainpage.css">
</head>
<body class="gradientbackgr">


<div class="container-fluid">



	<jsp:include page="../general/neutralheader.jsp"/>
	<br>
	<br>
	<div class = "container-fluid" >
		
			<div align="center" >
				<%--<div class="media-left">--%>
		      			<%--<img src="img_avatar1.png" class="media-object" style="width:100px">--%>
				<%--</div>--%>
				<div>
					<form action="${pageContext.request.contextPath}/userchangepage" method="post">
						<c:if test="${updatedUser != null}">
							<h1>Change user ${updatedUser.personalNumber}:</h1>
						</c:if>
						<div>
							<c:if test="${updatedUser != null}">
								<input type="text" hidden name="id" value="${updatedUser.id}">
							</c:if>
						</div>
						<div class="form-group">
					      		<label for="driver_name">Name:</label>
      							<input type="text" class="form-control" id="driver_name" placeholder="Enter new user name" name="firstName" style="width:450px" >
						</div>
						<div class="form-group">
					      		<label for="driver_middle_name">Middle name:</label>
      							<input type="text" class="form-control" id="driver_middle_name" placeholder="Enter new middle name" name="middleName" style="width:450px" >
						</div>
						<div class="form-group">
					      		<label for="driver_last_name">Last name:</label>
      							<input type="text" class="form-control" id="driver_last_name" placeholder="Enter new last name" name="lastName" style="width:450px" >
						</div>
						<div class="form-group">
							<label for="driver_password">Password:</label>
							<input type="password" class="form-control" id="driver_password" placeholder="Enter password" name="password" style="width:450px" >
						</div>

						<%--if its driver--%>
						<c:if test="${updatedUser.driver != null}">
							<div class="form-group">
									<label for="hours_worked">Hours worked:</label>
									<input type="text" class="form-control" id="hours_worked" placeholder="Enter number of hours worked" name="hoursWorked" style="width:450px" >
							</div>
							<div class="form-group">
								<label for="driver_current_city">Current city</label>
								 <select class="form-control" id="driver_current_city"  name="currentCityName" style="width:450px">
									 <c:if test="${citiesList != null}">
										 <c:forEach items="${citiesList}" var="city">
											<option>${city.name}</option>
										 </c:forEach>
									 </c:if>
									 <c:if test="${citiesList == null}">
											 <option>No cities available</option>
									 </c:if>
									</select>
							</div>
							<div class="form-group">
								<label for="driver_current_truck">Assign truck (if necessary):</label>
								 <select class="form-control" id="driver_current_truck" name="currentTruckRegistrationNumber" style="width:450px">
									 <c:if test="${trucksList != null}">
										 <c:forEach items="${trucksList}" var="truck">
											<option>${truck.registrationNumber}</option>
										 </c:forEach>
									 </c:if>
									 <c:if test="${trucksList == null}">
											 <option>No trucks available</option>
									 </c:if>
									</select>
							</div>
						</c:if>
						<div>
							<button type="submit" class="btn btn-primary">Save changes</button>
							<button type="submit" class="btn btn-primary" form="rollback">Rollback changes</button>
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/index" method="get" id="rollback"></form>
					<br>
					<br>
				</div>
			
					
    			</div>
  			
	</div>
	<jsp:include page="../general/footer.jsp"/>
	
</div>


<script>
$(document).ready(function(){
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>

</body>
</html>
