<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Assign truck to order page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>


	<script src="${pageContext.request.contextPath}/resources/web/js/managermainpage.js"></script>
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
					<form action="${pageContext.request.contextPath}/assigntrucktoorderpage", method="post">
						<div>
							<h1>Assign truck to order:</h1>
						</div>
						<div class="form-group">
							<input type="text" hidden class="form-control" id="item_descr"  name="description" style="width:450px" value="${orderDTO.description}">
						</div>
						<div class="form-group">
							<select  hidden multiple class="form-control" id="add_cargos"  name="cargosInOrder">
								<c:if test="${chosenCargos != null}">
									<c:if test="${not empty chosenCargos}">
										<c:forEach items="${chosenCargos}" var="cargo">
											<option selected value="${cargo.id}">${cargo.name}</option>
										</c:forEach>
									</c:if>
									<c:if test="${empty chosenCargos}">
										<option selected>No cargos available</option>
									</c:if>
								</c:if>
							</select>
						</div>
						<div class="form-group">
							<label for="add_truck">Assign truck:</label>
							 <select style="width: 450px" class="form-control" id="add_truck" size="10" required="required" name="assignedTruckId" onclick="showRouteOnTruckAssignPage();showMessageIfTruckHasDriversHoursOverLimit()" >
        						<c:if test="${availableTrucks != null}">
									<c:if test="${not empty availableTrucks}">
										<c:forEach items="${availableTrucks}" var="truck">
											<c:if test="${truck.driversInTruck == null or empty truck.driversInTruck}">
												<option id="truck+${truck.id}" value="${truck.id}" style="color: #bd2130">Reg.num: ${truck.registrationNumber}, current city: ${truck.currentCity.name}</option>
											</c:if>
											<c:if test="${truck.driversInTruck != null and not empty truck.driversInTruck}">
												<option value="${truck.id}">Reg.num: ${truck.registrationNumber}, current city: ${truck.currentCity.name}</option>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${empty availableTrucks}">
											<option>No trucks available</option>
									</c:if>
								</c:if>
      							</select>
						</div>
						<%--<div class="form-group">--%>
							<%--<label for="assign_truck">Assign truck</label>--%>
							 <%--<select class="form-control" id="assign_truck">--%>
        							<%--<option>rr44443</option>--%>
        							<%--<option>re45453</option>--%>
        							<%--<option>3</option>--%>
        							<%--<option>4</option>--%>
        							<%--<option>5</option>--%>
      							<%--</select>--%>
						<%--</div>--%>
						<div>
							<button id="commitAddNewOrder" type="submit" class="btn btn-primary">Create order with chosen truck</button>
							<button type="submit" class="btn btn-primary" form="rollback">Rollback changes</button>
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/index" method="get" id="rollback"></form>
				</div>
					<br>
					<div class="form-group">
						<label>Current route:</label>
						<div id="currentRoute"></div>
					</div>
				<%--<div class="form-group">--%>
					<%--<label for="set_city">Manual route set:</label>--%>
					<%--<div id="manualRouteChange">--%>
						<%--<select class="form-control" id="set_city" style="width: 450px">--%>
							<%--<option>Moscow</option>--%>
							<%--<option>SPb</option>--%>
						<%--</select>--%>
					<%--</div>--%>
				<%--</div>--%>
    			</div>
  			
	</div>
	<jsp:include page="../general/footer.jsp"/>
	
</div>

<script>
	$(document).ready(makeRedIfTruckHasDriversHoursOverLimit());
</script>

</body>
</html>
