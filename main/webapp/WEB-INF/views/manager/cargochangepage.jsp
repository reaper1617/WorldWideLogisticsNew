<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Cargo change page</title>
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
					<form action="${pageContext.request.contextPath}/cargochangepage" method="post">
						<c:if test="${updatedCargoId != null}">
							<h1>Cargo ${updatedCargoId} update:</h1>
						</c:if>
						<c:if test="${updatedCargoId == null}">
							<h1>Something goes wrong...</h1>
						</c:if>
						<div>
							<c:if test="${updatedCargoId != null}">
								<input type="text" hidden name="id" value="${updatedCargoId}">
							</c:if>
							<c:if test="${updatedCargoId == null}">
								<input type="text" hidden name="id" value="0">
							</c:if>
						</div>
    						<div class="form-group">
					      		<label for="item_id">New cargo personal number:</label>
      							<input type="text" class="form-control" id="item_id" placeholder="Enter new cargo personal number" name="personalNumber" style="width:450px">
						</div>
						<div class="form-group">
					      		<label for="cargo_name">New cargo name</label>
      							<input type="text" class="form-control" id="cargo_name" placeholder="Enter new cargo name" name="name" style="width:450px">
						</div>
						<div class="form-group">
					      		<label for="weight">New weight</label>
      							<input type="text" class="form-control" id="weight" placeholder="Enter new cargo weight" name="weight" style="width:450px">
						</div>
						<div class="form-group">
							<label for="city_from">City from</label>
							<select class="form-control" id="city_from" name="cityFrom" style="width:450px">
								<c:if test="${citiesList != null}">
									<option hidden>Not selected</option>
									<c:forEach items="${citiesList}" var="cell">
										<option>${cell.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${citiesList == null}">
									<option>No cities available</option>
								</c:if>
							</select>
						</div>
						<div class="form-group">
							<label for="city_to">City to</label>
							<select class="form-control" id="city_to" name="cityTo" style="width:450px">
								<c:if test="${citiesList != null}">
									<option hidden>Not selected</option>
									<c:forEach items="${citiesList}" var="cell">
										<option>${cell.name}</option>
									</c:forEach>
								</c:if>
								<c:if test="${citiesList == null}">
									<option>No cities available</option>
								</c:if>
							</select>
						</div>
						<div class="form-group">
							<label for="status">Status</label>
							<select class="form-control" id="status" name="status" style="width:450px">
								<option hidden>Not selected</option>
								<option >Prepared</option>
								<option >Loaded</option>
								<option >Shipping</option>
								<option >Delivered</option>
							</select>
						</div>
						<div>
							<button type="submit" class="btn btn-primary">Save changes</button>
							<button type="submit" class="btn btn-primary" form="rollback">Rollback changes</button>
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/index" method="get" id="rollback"></form>
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
