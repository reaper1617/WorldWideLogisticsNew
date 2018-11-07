<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Add new city page</title>
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
					<form action="${pageContext.request.contextPath}/addnewcitypage" method="post" id="addNewCityForm">
						<div>
							<h1>Add new city:</h1>
						</div>
						<div class="form-group">
					      		<label for="city_name">Name:</label>
      							<input type="text" class="form-control" id="city_name" placeholder="Enter city name" name="name" style="width:450px" required="required">
						</div>
						<div class="form-group">
							<label for="city_has_agency">Has agency:</label>
							<select class="form-control" id="city_has_agency" required="required" name="hasAgency" style="width:450px">
								<option>HAS</option>
								<option>HAS_NOT</option>
							</select>
						</div>
						<div>
							<button type="submit" class="btn btn-primary" form="addNewCityForm">Save changes</button>
							<button type="submit" class="btn btn-primary" form="rollbackChanges">Rollback changes</button>
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/index" method="get" id="rollbackChanges"></form>
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
