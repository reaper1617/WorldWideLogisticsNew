<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Driver account</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/web/css/adminmainpage.css">

	<script src="<c:url value="/resources/web/js/drivermainpage.js"/>"></script>

</head>
<body class="gradientbackgr">


<div class="container-fluid">



	<jsp:include page="../general/driverheader.jsp"/>
	<br>
	<div class = "container-fluid">
		 <div>
			 <c:if test="${loggedDriver != null}">
			 	<h2>Driver: ${loggedDriver.name} ${loggedDriver.middleName} ${loggedDriver.lastName}</h2>
			 </c:if>
			 <c:if test="${loggedDriver == null}">
				 <h2>Driver: undefined</h2>
			 </c:if>
		 </div>
		<div>
			<b>Info:</b>
			<div>
				<table id="driverInfo" class="table table-bordered table-active table-hover">
					<thead>
						<th>Personal number</th>
						<th>Assistants</th>
						<th>Assigned truck</th>
						<th>Current order</th>
						<th>Route</th>
					</thead>
					<tbody>
						<tr>
							<td>${loggedDriver.personalNumber}</td>
							<td>
								<div class="dropdown">
									<button id="driverStatusDropdownBtn" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show assistants
										<span class="caret"></span></button>
									<ul class="dropdown-menu">
										<c:if test="${loggedDriver.driver.currentTruck != null}">
											<c:if test="${not empty loggedDriver.driver.currentTruck.driversInTruck}">
												<c:forEach items="${loggedDriver.driver.currentTruck.driversInTruck}" var="driver">
													<c:if test="${driver.user.personalNumber != loggedDriver.personalNumber}">
														<li><a href="#">${driver.user.name} ${driver.user.middleName} ${driver.user.lastName}</a></li>
													</c:if>
												</c:forEach>
											</c:if>
											<c:if test="${loggedDriver.driver.currentTruck.driversInTruck.size() == 1}">
												<li><a href="#">No assistants</a></li>
											</c:if>
										</c:if>
										<c:if test="${loggedDriver.driver.currentTruck == null}">
												<li><a href="#">No assistants</a></li>
										</c:if>
									</ul>
								</div>
							</td>
							<c:if test="${loggedDriver.driver.currentTruck != null}">
								<td>${loggedDriver.driver.currentTruck.registrationNumber}</td>
							</c:if>
							<c:if test="${loggedDriver.driver.currentTruck == null}">
								<td>No assigned truck</td>
							</c:if>
							<c:if test="${loggedDriver.driver.currentTruck.assignedOrder != null}">
								<td>${loggedDriver.driver.currentTruck.assignedOrder.description}</td>
							</c:if>
							<c:if test="${loggedDriver.driver.currentTruck.assignedOrder == null}">
								<td>No assigned order</td>
							</c:if>
							<td>
								<div class="dropdown">
									<button id="routeStatusDropdownBtn" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Show route
										<span class="caret"></span></button>
									<ul class="dropdown-menu">
										<c:if test="${orderWithRoute != null}">
											<c:if test="${not empty orderWithRoute.route}">
												<c:forEach items="${orderWithRoute.route}" var="city">
													<li><a href="#">${city.name}</a></li>
												</c:forEach>
											</c:if>
											<c:if test="${empty orderWithRoute.route}">
													<li><a href="#">No route</a></li>
											</c:if>
										</c:if>
										<c:if test="${orderWithRoute == null}">
													<li><a href="#">No route</a></li>
										</c:if>
									</ul>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>


		</div>


		<div>
			<input hidden type="text" value="${loggedDriver.id}" name="driverId">
			<table id="driverStatusAndOrderInfo" class="table table-active table-hover" align="center">
				<thead align="center">
					<th align="center">Your status</th>
					<th align="center">Current order status</th>
				</thead>
				<tbody>
					<tr>
						<td align="center" id="driverStatusSelect">
							<select class="form-control" id="driver_status" name="driverStatus" style="width: 400px;">
								<c:if test="${loggedDriver.driver != null}">
									<option style="color: #0ed61f">${loggedDriver.driver.status}</option>
									<c:forEach items="${driverStatusList}" var="status">
										<c:if test="${status != loggedDriver.driver.status}">
											<option>${status}</option>
										</c:if>
									</c:forEach>
								</c:if>
							</select>
						</td>
						<td align="center" id="orderStatusSelect">
							<select class="form-control" id="order_status" name="orderStatus" style="width: 400px">
								<c:if test="${loggedDriver.driver.currentTruck != null}">
									<c:if test="${loggedDriver.driver.currentTruck.assignedOrder != null}">
										<option style="color: #0ed61f">${loggedDriver.driver.currentTruck.assignedOrder.status}</option>
										<c:forEach items="${orderStatusList}" var="orderStatus">
											<c:if test="${orderStatus != loggedDriver.driver.currentTruck.assignedOrder.status}">
												<option>${orderStatus}</option>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${loggedDriver.driver.currentTruck.assignedOrder == null}">
										<option>No assigned order</option>
									</c:if>
								</c:if>
								<c:if test="${loggedDriver.driver.currentTruck == null}">
									<option>No assigned order</option>
								</c:if>
							</select>
						</td>
					</tr>
					<tr>
						<td align="center">
							<button type="button" class="btn btn-primary" onclick="updateDriverStatus(${loggedDriver.driver.id})" >Update your status</button>
						</td>
						<td align="center">
							<c:if test="${loggedDriver.driver.currentTruck != null}">
								<c:if test="${loggedDriver.driver.currentTruck.assignedOrder != null}">
									<button type="button" class="btn btn-primary" onclick="updateOrderStatus(${loggedDriver.driver.currentTruck.assignedOrder.id})" >Update order status</button>
								</c:if>
								<c:if test="${loggedDriver.driver.currentTruck.assignedOrder == null}">
									<button type="button" class="btn btn-primary">Update order status</button>
								</c:if>
							</c:if>
							<c:if test="${loggedDriver.driver.currentTruck == null}">
								<button type="button" class="btn btn-primary" >Update order status</button>
							</c:if>
						</td>
					</tr>
				</tbody>


			</table>

		</div>


		<div>

				<div class="sticky-top">
					<b>Cargo details:</b>
					<div><input class="form-control" id="myCargoDetailsInput" type="text" placeholder="Search.."></div>
				</div>
				<div>
					<table id="myCargoDetailsTable" class="table table-bordered table-active table-hover">
						<thead>
							<th>Personal number</th>
							<th>Name</th>
							<th>City From</th>
							<th>City To</th>
							<th>Status</th>
							<th></th>
						</thead>
						<tbody>
							<c:if test="${loggedDriver.driver.currentTruck != null}">
								<c:if test="${loggedDriver.driver.currentTruck.assignedOrder != null}">
									<c:if test="${loggedDriver.driver.currentTruck.assignedOrder.cargosInOrder != null}">
										<c:forEach items="${loggedDriver.driver.currentTruck.assignedOrder.cargosInOrder}" var="cargo">
											<tr>
												<%--<form action="${pageContext.request.contextPath}/drivermainpage/2" method="post" >--%>
													<input hidden type="text" value="${cargo.id}" name="cargoId">
													<td>${cargo.personalNumber}</td>
													<td>${cargo.name}</td>
													<td>${cargo.route.cityFrom.name}</td>
													<td>${cargo.route.cityTo.name}</td>
													<td id="cargoStatusTd_${cargo.id}">
														<select class="form-control"  name="cargoStatus" style="width: 200px" id="cargoStatusSelect_${cargo.id}">
															<option style="color: #0ed61f">${cargo.status}</option>
															<c:if test="${cargoStatusList != null}">
																<c:forEach items="${cargoStatusList}" var="cargoStatus">
																	<c:if test="${cargoStatus != cargo.status}">
																		<option>${cargoStatus}</option>
																	</c:if>
																</c:forEach>
															</c:if>
														</select>
													</td>
													<td>
														<button type="button" class="btn btn-primary"  style="width: 200px" onclick="updateCargoStatus(${cargo.id})">Update cargo status</button>
													</td>
													<%--<td>--%>
														<%--<c:if test="${cargoStatusUpdatedSuccessfully != null}">--%>
															<%--<c:if test="${updatedCargoId == cargo.id}">--%>
																<%--<h5 style="color: darkcyan">Updated successfully!</h5>--%>
															<%--</c:if>--%>
															<%--<c:if test="${updatedCargoId != cargo.id}">--%>
																<%--<button type="submit" class="btn btn-primary"  style="width: 200px">Update cargo status</button>--%>
															<%--</c:if>--%>
														<%--</c:if>--%>
														<%--<c:if test="${cargoStatusUpdatedSuccessfully == null}">--%>
															<%--<button type="submit" class="btn btn-primary"  style="width: 200px">Update cargo status</button>--%>
														<%--</c:if>--%>
													<%--</td>--%>
												<%--</form>--%>
											</tr>
										</c:forEach>
									</c:if>
								</c:if>
							</c:if>
						</tbody>
					</table>
				</div>

			<br>
			<br>
		</div>
	</div>
	<jsp:include page="../general/footer.jsp"/>
	
</div>


</body>
</html>
