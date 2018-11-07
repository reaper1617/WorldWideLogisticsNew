<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Add new order page</title>
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
		 
			<div align="center">
				<%--<div class="media-left">--%>
		      			<%--<img src="img_avatar1.png" class="media-object" style="width:100px">--%>
				<%--</div>--%>
				<div>
					<form action="${pageContext.request.contextPath}/addneworderpage" method="post">
						<div>
							<h1>Add new order:</h1>
						</div>
						<div class="form-group">
					      		<label for="item_descr">New order description:</label>
      							<input type="text" class="form-control" id="item_descr" placeholder="Enter order description" name="description" style="width:450px" required="required">
						</div>
						<div class="form-group">
							<label for="add_cargos">Add cargos</label>
							 <select multiple class="form-control" style="width: 450px" id="add_cargos" size="10" required="required" name="cargosInOrder" onclick="showRoute() ">
        						<c:if test="${availableCargos != null}">
									<c:if test="${not empty availableCargos}">
										<c:forEach items="${availableCargos}" var="cargo">
								 			<option value="${cargo.id}">${cargo.name}: from ${cargo.route.cityFrom.name} to ${cargo.route.cityTo.name} </option>
										</c:forEach>
									</c:if>
									<c:if test="${empty availableCargos}">
											<option>No cargos available</option>
									</c:if>
								</c:if>
      							</select>
						</div>
						<div>
							<button type="submit" class="btn btn-primary">Go to assign truck</button>
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
					
    			</div>
  			
	</div>
	<jsp:include page="../general/footer.jsp"/>
	
</div>





</body>
</html>
