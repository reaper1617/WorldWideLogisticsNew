<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Manager account</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/web/css/bootstrap.css"> <!-- Bootstrap-Core-CSS -->--%>
	<%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/web/css/style.css" type="text/css" media="all" /> <!-- Style-CSS -->--%>


	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/web/css/adminmainpage.css">

</head>
<body class="gradientbackgr">


<div class="container-fluid">



	<jsp:include page="../general/managerheader.jsp"/>
	<c:if test="${loggedUser != null}">
		<h3 id="loggedUserHeader">Logged as: ${loggedUser.name} ${loggedUser.middleName} ${loggedUser.lastName}</h3>
	</c:if>
	<br>
	<div class = "container-fluid">
		 <div class="tab-content">
  			<div id="home" class="tab-pane active ">
    				<div class = "sticky-top">
						<form action="${pageContext.request.contextPath}/addneworderpage", method="get">
							<h3>Orders <button class = "btn btn-primary" type = "submit">Add new</button> <button class = "btn btn-primary" type = "submit" form="googlePageForm">Show on map</button> </h3>
						</form>
						<form action="${pageContext.request.contextPath}/adminmainpagegoogle" method="get" id="googlePageForm"></form>
				<div><input class="form-control" id="myInput" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myTable" class="table table-bordered table-active table-hover">
    						<thead>
      							<tr>
        							<th>Id</th>
        							<th>Description</th>
									<th>Date/time</th>
									<th>Status</th>
        							<th>Assigned truck</th>
									<th>Assigned drivers</th>
        							<th>Route</th>
      							</tr>
						</thead>
							<tbody>
							<c:if test="${ordersList != null}">
								<c:forEach items="${ordersList}" var="order">
									<tr>
										<td>${order.personalNumber}</td>
										<td>${order.description}</td>
										<td>${order.date}</td>
										<td>${order.status}</td>
										<c:if test="${order.assignedTruck != null}">
											<td>${order.assignedTruck.registrationNumber}</td>
										</c:if>
										<c:if test="${order.assignedTruck == null}">
											<td>No assigned truck</td>
										</c:if>
										<td>
											<div class="dropdown">
												<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show assigned drivers
													<span class="caret"></span></button>
													<ul class="dropdown-menu">
														<%--<c:if test="${order.assignedTruck != null}">--%>
															<%--<c:if test="${not empty order.assignedTruck}">--%>
                                                                <%--<c:forEach items="${order.assignedTruck.driversInTruck}" var="driverInTruck">--%>
                                                                    <%--<li><a href="#">${driverInTruck.user.name} ${driverInTruck.user.middleName} ${driverInTruck.user.lastName}</a></li>--%>
                                                                <%--</c:forEach>--%>
															<%--</c:if>--%>
															<%--<c:if test="${empty order.assignedTruck}">--%>
																<%--<li><a href="#">No assigned drivers</a></li>--%>
															<%--</c:if>--%>
														<%--</c:if>--%>
                                                        <%--<c:if test="${order.assignedTruck == null}">--%>
                                                            <%--<li><a href="#">No assigned drivers</a></li>--%>
                                                        <%--</c:if>--%>
                                                        <c:if test="${order.assignedTruck != null}">
                                                            <c:if test="${order.assignedTruck.driversInTruck != null}">
                                                                <c:forEach items="${order.assignedTruck.driversInTruck}" var="driverInTruck">
                                                                    <li><a href="#">${driverInTruck.user.name} ${driverInTruck.user.middleName} ${driverInTruck.user.lastName}</a></li>
                                                                </c:forEach>
                                                            </c:if>
                                                            <c:if test="${order.assignedTruck.driversInTruck == null}">
                                                                <li><a href="#">No assigned drivers</a></li>
                                                            </c:if>
                                                            <c:if test="${empty order.assignedTruck.driversInTruck}">
                                                                <li><a href="#">No assigned drivers</a></li>
                                                            </c:if>
                                                        </c:if>
                                                        <c:if test="${order.assignedTruck == null}">
                                                            <li><a href="#">No assigned drivers</a></li>
                                                        </c:if>

                                                    </ul>
											</div>
										</td>
										<td>
											<div class="dropdown">
												<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show route
													<span class="caret"></span></button>
													<ul class="dropdown-menu">
														<c:forEach items="${order.route}" var="city">
															<li><a href="#">${city.name}</a></li>
														</c:forEach>
													</ul>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							</tbody>
    					
    					</table>
				</div>
				<br>


  			</div>
  			<div id="managetrucks" class="tab-pane fade">
    				<div class = "sticky-top">
						<form action="${pageContext.request.contextPath}/addnewtruckpage" method="get">
							<h3>Trucks <button class = "btn btn-primary" type = "submit">Add new</button></h3>
						</form>
				<div><input class="form-control" id="myInput2" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myTable2" class="table table-bordered table-active table-hover">
    						<thead>
      							<tr>
								<th></th>
        						<th>Registration number</th>
        						<th>Number of drivers</th>
								<th>Capacity</th>
								<th>State</th>
        						<th>Current city</th>
								<th>Assigned drivers</th>
        						<th>Assigned order</th>
      							</tr>
						</thead>
						<tbody>
						<c:if test="${trucksList != null}">
							<c:forEach items="${trucksList}" var="cell">
								<tr>
								<td>
									<form action="${pageContext.request.contextPath}/managermainpage/2" method="post" >
										<button type="submit" class="btn btn-primary">Edit</button>
										<input type="text" hidden name="id" value="${cell.id}">
									</form>
								</td>
								<td>${cell.registrationNumber}</td>
								<td>${cell.numOfDrivers}</td>
								<td>${cell.capacity}</td>
								<td>${cell.state}</td>
								<td>${cell.currentCity.name}</td>
								<td>
									<div class="dropdown">
										<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show drivers
											<span class="caret"></span></button>
										<ul class="dropdown-menu">
											<c:if test="${cell.driversInTruck != null}">
												<c:forEach items="${cell.driversInTruck}" var="driver">
													<li><a href="#">${driver.user.name} ${driver.user.middleName} ${driver.user.lastName}</a></li>
												</c:forEach>
											</c:if>
											<c:if test="${empty cell.driversInTruck}">
												<li><a href="#">No assigned driver</a></li>
											</c:if>
										</ul>
									</div>
								</td>

								<c:if test="${cell.assignedOrder != null}">
									<td>${cell.assignedOrder}</td>
								</c:if>
								<c:if test="${cell.assignedOrder == null}">
									<td>No assigned order</td>
								</c:if>
								</tr>
                            </c:forEach>
						</c:if>
						</tbody>
    					</table>
				</div>
  			</div>
  			<div id="managedrivers" class="tab-pane fade">
    				<div class = "sticky-top">
						<form action="${pageContext.request.contextPath}/addnewdriverpage" method="get">
							<h3>Drivers <button class = "btn btn-primary" type = "submit">Add new</button></h3>
						</form>
				<div><input class="form-control" id="myInput3" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myTable3" class="table table-bordered table-active table-hover">
    						<thead>
      							<tr>
								<th></th>
        						<th>First name</th>
        						<th>Middle name</th>
								<th>Last name</th>
								<th>Personal number</th>
        						<th>Hours worked</th>
								<th>Status</th>
        						<th>Current city</th>
								<th>Current truck</th>
      							</tr>
						</thead>	
						<tbody>
						<c:if test="${driversList != null}">
                            <c:forEach items="${driversList}" var="cell">
                                <tr>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/managermainpage/1" method="post" >
                                            <button type="submit" class="btn btn-primary">Edit</button>
                                            <input type="text" hidden name="id" value="${cell.id}">
                                        </form>
                                    </td>
                                    <td>${cell.name}</td>
                                    <td>${cell.middleName}</td>
                                    <td>${cell.lastName}</td>
                                    <td>${cell.personalNumber}</td>
                                    <td>${cell.driver.hoursWorked}</td>
                                    <td>${cell.driver.status}</td>
                                    <td>${cell.driver.currentCity.name}</td>
									<c:if test="${cell.driver.currentTruck != null}">
                                    	<td>${cell.driver.currentTruck.registrationNumber}</td>
									</c:if>
									<c:if test="${cell.driver.currentTruck == null}">
										<td>No assigned truck</td>
									</c:if>
                                </tr>
                            </c:forEach>
						</c:if>
						</tbody>				
    					</table>
				</div>
  			</div>
			<div id="managecargos" class="tab-pane fade">
				<div class = "sticky-top">
					<form action="${pageContext.request.contextPath}/addnewcargopage" method="get">
						<h3>Cargos <button class = "btn btn-primary" type="submit">Add new</button></h3>
					</form>
				<div><input class="form-control" id="myInput4" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myTable4" class="table table-bordered table-active table-hover">
    						<thead>
      							<tr>
									<th></th>
									<th>Item Id</th>
        							<th>Name</th>
        							<th>Weight</th>
									<th>City from</th>
									<th>City to</th>
									<th>Status</th>
      							</tr>
						</thead>	
						<tbody>
						<c:if test="${cargoList != null}">
							<c:forEach items="${cargoList}" var="cell">
								<tr>
									<td>
										<form action="${pageContext.request.contextPath}/managermainpage/0" method="post" >
											<button type="submit" class="btn btn-primary">Edit</button>
											<input type="text" hidden name="id" value="${cell.id}">
										</form>
									</td>
									<td>${cell.personalNumber}</td>
									<td>${cell.name}</td>
									<td>${cell.weight}</td>
									<td>${cell.route.cityFrom.name}</td>
									<td>${cell.route.cityTo.name}</td>
									<td>${cell.status}</td>
								</tr>
							</c:forEach>
						</c:if>
						</tbody>				
    					</table>
				</div>
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

<script>
$(document).ready(function(){
  $("#myInput2").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable2 tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>

<script>
$(document).ready(function(){
  $("#myInput3").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable3 tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>

<script>
$(document).ready(function(){
  $("#myInput4").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable4 tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>
</body>
</html>
