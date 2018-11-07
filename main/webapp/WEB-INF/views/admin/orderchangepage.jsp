<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Order change page</title>
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
					<form action="${pageContext.request.contextPath}/orderchangepage" method="post">
						<c:if test="${updatedOrder != null}">
							<h1>Change order ${updatedOrder.description}</h1>
						</c:if>
						<c:if test="${updatedOrder == null}">
							<h1>Something goes wrong...</h1>
						</c:if>
						<div class="form-group">
							<label for="item_pNumber">New personal number:</label>
							<input type="text" class="form-control" id="item_pNumber" placeholder="Enter new personal number" name="personalNumber" style="width:450px" >
						</div>
						<div class="form-group">
							<label for="item_descr">New order description:</label>
      						<input type="text" class="form-control" id="item_descr" placeholder="Enter new order description" name="description" style="width:450px" >
							<input type="text" hidden name="id" value="${updatedOrder.id}">
						</div>
						<div class="form-group">
							<label for="order_status">New status:</label>
							<select class="form-control" id="order_status" name="status" style="width:450px">
								<option hidden>Not selected</option>
								<option>NOT_PREPARED</option>
								<option>PREPARED</option>
								<option>EXECUTING</option>
								<option>EXECUTED</option>
							</select>
						</div>
						<div class="form-group">
							<label for="add_cargos">Add cargos</label>
							 <select multiple class="form-control" id="add_cargos" size="10"  name="cargosInOrder" style="width:450px">
        						<c:if test="${availableCargos != null}">
									<c:if test="${not empty availableCargos}">
										<c:forEach items="${availableCargos}" var="cargo">
								 			<option value="${cargo.id}">${cargo.name}</option>
										</c:forEach>
									</c:if>
									<c:if test="${empty availableCargos}">
											<option>No cargos available</option>
									</c:if>
								</c:if>
      							</select>
						</div>
						<div>
							<button type="submit" class="btn btn-primary">Go to assign new truck</button>
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


</body>
</html>
