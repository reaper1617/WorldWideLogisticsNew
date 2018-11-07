<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Admin account</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/web/js/adminmainpagepgntd.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/web/css/adminmainpage.css">

</head>
<body class="gradientbackgr">


<div class="container-fluid">

	<jsp:include page="../general/adminheader.jsp"/>

	<br>
	<div class = "container-fluid">
		 <div class="tab-content">
  			<div id="manageorders" class="tab-pane active ">
    				<div class = "sticky-top">
						<form action="${pageContext.request.contextPath}/addneworderpage", method="get">
							<h3>Orders <button class = "btn btn-primary" type = "submit" id="addNewOrderBtn">Add new</button> <button class = "btn btn-primary" type = "submit" form="googlePageForm">Show on map</button> </h3>
						</form>
						<form action="${pageContext.request.contextPath}/adminmainpagegoogle" method="get" id="googlePageForm"></form>
				<div><input class="form-control" id="myInput" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myTable" class="table table-bordered table-active table-hover">
    						<thead class="thead-style">
      							<tr>
									<th></th>
									<th></th>
        							<th>Id</th>
        							<th>Description</th>
									<th>Date/time</th>
									<th>Status</th>
        							<th>Assigned truck</th>
									<th>Assigned drivers</th>
        							<th>Route</th>
      							</tr>
						</thead>
							<tbody class="tbody-style">
							<c:if test="${ordersPgntd != null}">
								<c:forEach items="${ordersPgntd}" var="order">
									<tr>
										<td>
											<form action="${pageContext.request.contextPath}/adminmainpage/1" method="post" >
												<button type="submit" class="btn btn-primary">Edit</button>
													<input type="text" hidden name="id" value="${order.id}">
											</form>
										</td>
										<td>
											<%--<form action="${pageContext.request.contextPath}/adminmainpage/2" method="post" >--%>
											<form action="#" >
												<button type="submit" id="del+${order.id}" class="btn btn-danger" onclick="deleteOrder(${order.id})">Delete</button>
												<input type="text" hidden name="id" value="${order.id}">
											</form>
										</td>
										<td>${order.personalNumber}</td>
										<td>${order.description}</td>
										<td>${order.date}</td>
										<td>${order.status}</td>
										<c:if test="${order.assignedTruckRegistrationNumber != null}">
											<td>${order.assignedTruckRegistrationNumber}</td>
										</c:if>
										<c:if test="${order.assignedTruckRegistrationNumber == null}">
											<td>No assigned truck</td>
										</c:if>
										<td>
											<div class="dropdown">
												<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show assigned drivers
													<span class="caret"></span></button>
													<ul class="dropdown-menu">
                                                        <c:if test="${order.assignedDrivers != null}">
															<c:if test="${empty order.assignedDrivers}">
																<li><a href="#">No assigned drivers</a></li>
															</c:if>
															<c:if test="${not empty order.assignedDrivers}">
																<c:forEach items="${order.assignedDrivers}" var="driverInTruck">
																	<li><a href="#">${driverInTruck}</a></li>
																</c:forEach>
															</c:if>
                                                        </c:if>
														<c:if test="${order.assignedDrivers == null}">
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
                                                        <c:if test="${order.route != null}">
														<c:forEach items="${order.route}" var="city">
															<li><a href="#">${city}</a></li>
														</c:forEach>
                                                        </c:if>
                                                        <c:if test="${order.route == null}">
                                                                <li><a href="#">No route</a></li>
                                                        </c:if>
                                                        <c:if test="${empty order.route}">
                                                            <li><a href="#">No route</a></li>
                                                        </c:if>
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
                <div align="center">
				    <button id="showMoreBtn" class="btn btn-primary" type="button" value="Show more orders" onclick="showMoreOrders()">Show more orders</button>
                </div>
				<input id="currentOrderPage" value="0" hidden>
				<br>
  			</div>
  			<div id="managetrucks" class="tab-pane fade">
    				<div class = "sticky-top">
						<form action="${pageContext.request.contextPath}/addnewtruckpage" method="get">
							<h3>Trucks <button id="addNewTruckBtn" class = "btn btn-primary" type = "submit">Add new</button></h3>
						</form>
				<div><input class="form-control" id="myInput2" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myTable2" class="table table-bordered table-active table-hover">
    						<thead class="thead-style">
      							<tr>
									<th></th>
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
						<tbody class="tbody-style">
						<c:if test="${trucksPgntd != null}">
							<c:forEach items="${trucksPgntd}" var="cell">
								<tr>
									<td>
										<form action="${pageContext.request.contextPath}/adminmainpage/3" method="post" >
											<button type="submit" class="btn btn-primary">Edit</button>
												<input type="text" hidden name="id" value="${cell.id}">
										</form>
									</td>
									<td>
										<form action="#" >
											<button type="submit" class="btn btn-danger" onclick="deleteTruck(${cell.id})">Delete</button>
                                            <input type="text" hidden name="id" value="${cell.id}">
										</form>
									</td>
								<td>${cell.registrationNumber}</td>
								<td>${cell.numberOfDrivers}</td>
								<td>${cell.capacity}</td>
								<td>${cell.state}</td>
								<td>${cell.currentCity}</td>
								<td>
									<div class="dropdown">
										<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show drivers
											<span class="caret"></span></button>
										<ul class="dropdown-menu">
											<c:if test="${cell.assignedDriversNames != null}">
												<c:forEach items="${cell.assignedDriversNames}" var="driver">
													<li><a href="#">${driver}</a></li>
												</c:forEach>
											</c:if>
											<c:if test="${empty cell.assignedDriversNames}">
												<li><a href="#">No assigned driver</a></li>
											</c:if>
										</ul>
									</div>
								</td>

								<c:if test="${cell.assignedOrderDescription != null}">
									<td>${cell.assignedOrderDescription}</td>
								</c:if>
								<c:if test="${cell.assignedOrderDescription == null}">
									<td>No assigned order</td>
								</c:if>
								</tr>
                            </c:forEach>
						</c:if>
						</tbody>
    					</table>
				</div>
                <br>
                <div align="center">
                    <button id="showMoreTrucksBtn" class="btn btn-primary" type="button" value="Show more trucks" onclick="showMoreTrucks()">Show more trucks</button>
                </div>
                <input id="currentTruckPage" value="0" hidden>
				<br>
  			</div>
  			<div id="manageusers" class="tab-pane fade">
    				<div class = "sticky-top">
						<form action="${pageContext.request.contextPath}/addnewuserpage" method="get">
							<h3>Users <button id="addNewUserBtn" class = "btn btn-primary" type = "submit">Add new</button></h3>
						</form>
				<div><input class="form-control" id="myInput3" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myTable3" class="table table-bordered table-active table-hover">
    						<thead class="thead-style">
      							<tr>
									<th></th>
									<th></th>
        							<th>First name</th>
        							<th>Middle name</th>
									<th>Last name</th>
									<th>Personal number</th>
        							<th>Role</th>
									<th>Hour worked</th>
									<th>Driver status</th>
									<th>Current city</th>
									<th>Current truck</th>
									<th>Current order</th>
      							</tr>
						</thead>
						<tbody class="tbody-style">
						<c:if test="${usersPgntd != null}">
                            <c:forEach items="${usersPgntd}" var="user">
                                <tr>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/adminmainpage/5" method="post" >
                                            <button type="submit" class="btn btn-primary">Edit</button>
                                            <input type="text" hidden name="id" value="${user.id}">
                                        </form>
                                    </td>
									<td>
										<form action="#"  >
											<button type="submit" class="btn btn-danger" onclick="deleteUser(${user.id})">Delete</button>
                                            <input type="text" hidden name="id" value="${user.id}">
										</form>
									</td>
                                    <td>${user.firstName}</td>
                                    <td>${user.middleName}</td>
                                    <td>${user.lastName}</td>
                                    <td>${user.personalNumber}</td>
									<td>${user.role}</td>
									<td>${user.hoursWorked}</td>
									<td>${user.driverStatus}</td>
									<td>${user.currentCityName}</td>
									<td>${user.currentTruckRegistrationNumber}</td>
									<td>${user.orderDescription}</td>
                                </tr>
                            </c:forEach>
						</c:if>
						</tbody>
    					</table>
				</div>
				<div align="center">
					<button id="showMoreUsersBtn" class="btn btn-primary" type="button" value="Show more trucks" onclick="showMoreUsers()">Show more users</button>
				</div>
				<input id="currentUserPage" value="0" hidden>
  			</div>
			<div id="managecargos" class="tab-pane fade">
				<div class = "sticky-top">
					<form action="${pageContext.request.contextPath}/addnewcargopage" method="get">
						<h3>Cargos <button class = "btn btn-primary" type="submit" id="addNewCargoBtn">Add new</button></h3>
					</form>
				<div><input class="form-control" id="myInput4" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myTable4" class="table table-bordered table-active table-hover">
    						<thead class="thead-style">
      							<tr>
									<th></th>
									<th></th>
									<th>Item Id</th>
        							<th>Name</th>
        							<th>Weight</th>
									<th>City from</th>
									<th>City to</th>
									<th>Status</th>
      							</tr>
						</thead>
						<tbody class="tbody-style">
						<c:if test="${cargosPgntd != null}">
							<c:forEach items="${cargosPgntd}" var="cell">
								<tr>
									<td>
										<form action="${pageContext.request.contextPath}/adminmainpage/7" method="post" >
											<button type="submit" class="btn btn-primary">Edit</button>
												<input type="text" hidden name="id" value="${cell.id}">
										</form>
									</td>
									<td>
										<form action="#" >
											<button type="submit" class="btn btn-danger" onclick="deleteCargo(${cell.id})">Delete</button>
                                            <input type="text" hidden name="id" value="${cell.id}">
										</form>
									</td>
									<td>${cell.personalNumber}</td>
									<td>${cell.name}</td>
									<td>${cell.weight}</td>
									<td>${cell.cityFrom}</td>
									<td>${cell.cityTo}</td>
									<td>${cell.status}</td>
								</tr>
							</c:forEach>
						</c:if>
						</tbody>
    					</table>
				</div>
				<div align="center">
					<button id="showMoreCargosBtn" class="btn btn-primary" type="button" value="Show more cargos" onclick="showMoreCargos()">Show more cargos</button>
				</div>
				<input id="currentCargoPage" value="0" hidden>
				<br>
  			</div>
			 <div id="managecities" class="tab-pane fade">
				 <div class = "sticky-top">
					 <form action="${pageContext.request.contextPath}/addnewcitypage" method="get">
						 <h3>Cities <button class = "btn btn-primary" type = "submit">Add new</button></h3>
					 </form>
					 <div><input class="form-control" id="myInput5" type="text" placeholder="Search.."></div>
				 </div>
				 <div>
					 <table id="myTable5" class="table table-bordered table-active table-hover">
						 <thead class="thead-style">
						 <tr>
							 <th></th>
							 <th></th>
							 <th>Name</th>
							 <th>Has agency</th>
							 <th>Drivers in city</th>
							 <th>Trucks in city</th>
						 </tr>
						 </thead>
						 <tbody class="tbody-style">
						 <c:forEach items="${citiesPgntd}" var="city">
						 <tr>
							 <td>
								 <form action="${pageContext.request.contextPath}/adminmainpage/9" method="post" >
									 <button type="submit" class="btn btn-primary">Edit</button>
									 <input type="text" hidden name="id" value="${city.id}">
								 </form>
							 </td>
							 <td>
								 <form action="#" >
									 <button type="submit" class="btn btn-danger" onclick="deleteCity(${city.id})">Delete</button>
									 <input type="text" hidden name="id" value="${city.id}">
								 </form>
							 </td>
							 <td>${city.name}</td>
							 <td>${city.hasAgency}</td>

							 <c:if test="${city.driversInCity != null}">
								 <c:if test="${not empty city.driversInCity}">
									 <td>
										<div class="dropdown">
											<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show drivers
												<span class="caret"></span></button>
											<ul class="dropdown-menu">
												<c:forEach items="${city.driversInCity}" var="driver">
													<li><a href="#">${driver}</a></li>
												</c:forEach>
											</ul>
										</div>
									</td>
								 </c:if>
								 <c:if test="${empty city.driversInCity}">
									 <td>
										 <div class="dropdown">
											 <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show drivers
												 <span class="caret"></span></button>
											 <ul class="dropdown-menu">
													 <li><a href="#">No drivers in city</a></li>
											 </ul>
										 </div>
									 </td>
								 </c:if>
							 </c:if>
							 <c:if test="${city.driversInCity == null}">
								 <td>
									 <div class="dropdown">
										 <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show drivers
											 <span class="caret"></span></button>
										 <ul class="dropdown-menu">
												 <li><a href="#">No drivers in city</a></li>
										 </ul>
									 </div>
								 </td>
							 </c:if>

							 <c:if test="${city.trucksInCity != null}">
								 <c:if test="${not empty city.trucksInCity}">
									 <td>
										 <div class="dropdown">
											 <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show trucks
												 <span class="caret"></span></button>
											 <ul class="dropdown-menu">
												 <c:forEach items="${city.trucksInCity}" var="truck">
													 <li><a href="#">${truck}</a></li>
												 </c:forEach>
											 </ul>
										 </div>
									 </td>
								 </c:if>
								 <c:if test="${empty city.trucksInCity}">
									 <td>
										 <div class="dropdown">
											 <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show trucks
												 <span class="caret"></span></button>
											 <ul class="dropdown-menu">
												 <li><a href="#">No trucks in city</a></li>
											 </ul>
										 </div>
									 </td>
								 </c:if>
							 </c:if>
							 <c:if test="${city.trucksInCity == null}">
								 <td>
									 <div class="dropdown">
										 <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show trucks
											 <span class="caret"></span></button>
										 <ul class="dropdown-menu">
											 <li><a href="#">No trucks in city</a></li>
										 </ul>
									 </div>
								 </td>
							 </c:if>
						 </tr>
						 </c:forEach>
						 </tbody>
					 </table>
				 </div>
				 <div align="center">
					 <button id="showMoreCitiesBtn" class="btn btn-primary" type="button" value="Show more cities" onclick="showMoreCities()">Show more cities</button>
				 </div>
				 <input id="currentCityPage" value="0" hidden>
				 <br>
			 </div>
			 <div id="manageroutes" class="tab-pane fade">
				 <div class = "sticky-top">
					 <form action="${pageContext.request.contextPath}/addnewroutepage" method="get">
						 <h3>Routes <button class = "btn btn-primary" type = "submit">Add new</button></h3>
					 </form>
					 <div><input class="form-control" id="myInput6" type="text" placeholder="Search.."></div>
				 </div>
				 <div>
					 <table id="myTable6" class="table table-bordered table-active table-hover">
						 <thead class="thead-style">
						 <tr>
							 <th></th>
							 <th></th>
							 <th>City from</th>
							 <th>City to</th>
							 <th>Distance</th>
						 </tr>
						 </thead>
						 <tbody class="tbody-style">
						 <c:if test="${routesPgntd != null}">
							 <c:forEach items="${routesPgntd}" var="route">
								<tr>
									<td>
										<form action="${pageContext.request.contextPath}/adminmainpage/11" method="post" >
											<button type="submit" class="btn btn-primary">Edit</button>
											<input type="text" hidden name="id" value="${route.id}">
										</form>
									</td>
									<td>
										<form action="#" >
											<button type="submit" class="btn btn-danger" onclick="deleteRoute(${route.id})">Delete</button>
											<input type="text" hidden name="id" value="${route.id}">
										</form>
									</td>
									<td>${route.cityFrom}</td>
									<td>${route.cityTo}</td>
									<td>${route.distance}</td>
								</tr>
							 </c:forEach>
						 </c:if>
						 </tbody>
					 </table>
				 </div>
                 <div align="center">
                     <button id="showMoreRoutesBtn" class="btn btn-primary" type="button" value="Show more cities" onclick="showMoreRoutes()">Show more routes</button>
                 </div>
                 <input id="currentRoutePage" value="0" hidden>
                 <br>
                 <br>
			 </div>
		</div>


	<jsp:include page="../general/footer.jsp"/>

	
</div>





</body>

</html>
