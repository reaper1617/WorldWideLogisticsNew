<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Truck change page</title>
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
					<form action="${pageContext.request.contextPath}/truckchangepage" method="post">
						<c:if test="${updatedTruck != null}">
							<h1>Truck ${updatedTruck.registrationNumber} update:</h1>
						</c:if>
						<div>
							<c:if test="${updatedTruckId != null}">
								<input type="text" hidden name="id" value="${updatedTruckId}">
							</c:if>
							<c:if test="${updatedTruckId == null}">
								<input type="text" hidden name="id" value="0">
							</c:if>
						</div>
						<div class="form-group">
					      		<label for="registration_number">New registration number:</label>
      							<input type="text" class="form-control" id="registration_number" placeholder="Enter new registration number" name="registrationNumber" style="width:450px">
						</div>
						<div class="form-group">
					      		<label for="num_of_drivers">New number of drivers:</label>
      							<input type="text" class="form-control" id="num_of_drivers" placeholder="Enter new number of drivers" name="numberOfDrivers" style="width:450px">
						</div>
						<div class="form-group">
					      		<label for="capacity">New capacity</label>
      							<input type="text" class="form-control" id="capacity" placeholder="Enter new capacity" name="capacity" style="width:450px">
						</div>
						<div class="form-group">
							<label for="truck_state">Status</label>
							<select class="form-control" id="truck_state" name="state" style="width:450px">
								<option hidden>Not selected</option>
								<option >Ready</option>
								<option >Not ready</option>
							</select>
						</div>
						<div class="form-group">
							<label for="change_city">New current city:</label>
							<select class="form-control" id="change_city"  name="currentCity" style="width:450px">
								<option hidden>Not selected</option>
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
						<%--<div class="form-group">--%>
							<%--<label for="assign_drivers">Assign drivers</label>--%>
							<%--<select class="form-control" id="assign_drivers" name="assignedDrivers" multiple>--%>
								<%--<option hidden>Not selected</option>--%>
								<%--<c:if test="${freeDrivers != null}">--%>
									<%--<c:forEach items="${freeDrivers}" var="driver">--%>
										<%--<option value="${driver.id}">${driver.name} ${driver.middleName} ${driver.lastName}</option>--%>
									<%--</c:forEach>--%>
								<%--</c:if>--%>
								<%--<c:if test="${freeDrivers == null}">--%>
									<%--<option>No drivers available</option>--%>
								<%--</c:if>--%>
							<%--</select>--%>
						<%--</div>--%>
						<div class="form-group">
				      			<h2>Manage assigned drivers</h2>

							<div class="form-group">
								<label for="change_assigned_drivers">New assigned drivers:</label>
								<select class="form-control" id="change_assigned_drivers"  name="assignedDrivers" multiple size="5" style="width:450px">
									<c:if test="${driversList != null}">
										<c:if test="${ empty driversList}">
											<option>No drivers available</option>
										</c:if>
										<c:if test="${not empty driversList}">
											<option>Do nothing</option>
											<option style="color: orangered">Unassign current drivers</option>
											<c:forEach items="${driversList}" var="driver">
												<option value="${driver.id}">${driver.name} ${driver.middleName} ${driver.lastName}</option>
											</c:forEach>
										</c:if>
									</c:if>


								</select>
							</div>

							<%--<table class="table table-bordered table-active table-hover">--%>
								<%--<thead>--%>
									<%--<tr>--%>
										<%--<th>Name</th>--%>
										<%--<th>Second name</th>--%>
										<%--<th>Last name</th>--%>
										<%--<th>Personal number</th>--%>
									<%--</tr>--%>
								<%--</thead>--%>
								<%--<tbody>--%>
										<%--<c:if test="${updatedTruck != null}">--%>
											<%--<c:if test="${updatedTruck.driversInTruck !=null}">--%>
											<%--<c:forEach items="${updatedTruck.driversInTruck}" var="driver">--%>
												<%--<tr>--%>
													<%--<td>${driver.user.name}</td>--%>
													<%--<td>${driver.user.middleName}</td>--%>
													<%--<td>${driver.user.lastName}</td>--%>
													<%--<td>${driver.user.personalNumber}</td>--%>
												<%--</tr>--%>
											<%--</c:forEach>--%>
											<%--</c:if>--%>
										<%--</c:if>--%>
								<%--</tbody>--%>
							<%--</table>--%>
						</div>
						<div>
							<button type="submit" class="btn btn-primary">Save changes</button>
							<button type="submit" class="btn btn-primary" form="rollback">Rollback changes</button>
							<br>
							<br>
							<br>
							<br>
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/index" method="get" id="rollback"></form>
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

<%--<script>--%>
    <%--$("add").click(function (){--%>
        <%--var firstName = $("#firstName").val();--%>
        <%--var lastName = $("#lastName").val();--%>
        <%--var entranceYear = $("#entranceYear").val();--%>
        <%--$.ajax({--%>
            <%--url: "studentAdd",--%>
            <%--data: {"firstName": firstName, "lastName": lastName, "entranceYear": entranceYear },--%>
            <%--type: "post",--%>
            <%--success: function(data){--%>
				<%----%>

                <%--$.each(data, function(index, student){--%>

                <%--})--%>
            <%--}--%>
    <%--})--%>
<%--</script>--%>


</body>
</html>
