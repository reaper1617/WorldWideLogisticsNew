<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Reassign truck to order page</title>
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
					<form action="${pageContext.request.contextPath}/reassigntrucktoorderpage", method="post">
						<div>
							<h1>Reassign truck to order:</h1>
						</div>
						<div class="form-group">
							<input type="text" hidden class="form-control" id="item_id"  name="id" style="width:450px" value="${updatedOrderDTO.id}">
						</div>
						<div class="form-group">
							<input type="text" hidden class="form-control" id="item_pNumber"  name="personalNumber" style="width:450px" value="${updatedOrderDTO.personalNumber}">
						</div>
						<div class="form-group">
							<input type="text" hidden class="form-control" id="item_descr"  name="description" style="width:450px" value="${updatedOrderDTO.description}">
						</div>
						<div class="form-group">
							<input type="text" hidden class="form-control" id="item_status"  name="status" style="width:450px" value="${updatedOrderDTO.status}">
						</div>
						<div class="form-group">
							<select hidden multiple class="form-control" id="add_cargos"  name="cargosInOrder" style="width:450px">
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
							 <select  class="form-control" id="add_truck" size="10" required="required" name="assignedTruckId" style="width:450px">
        						<c:if test="${availableTrucks != null}">
									<c:if test="${not empty availableTrucks}">
										<c:forEach items="${availableTrucks}" var="truck">
								 			<option value="${truck.id}">${truck.registrationNumber}</option>
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
							<button type="submit" class="btn btn-primary">Update order with chosen truck</button>
							<button type="submit" class="btn btn-primary" form="rollback">Rollback changes</button>
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/index" method="get" id="rollback"></form>
				</div>
			
					
    			</div>
  			
	</div>
	<jsp:include page="../general/footer.jsp"/>
	
</div>


</body>
</html>
