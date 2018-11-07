<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Google maps page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/web/css/adminmainpage.css">
	<style>
		#map {
			height: 100%;
		}
		html, body {
			height: 100%;
			margin: 0;
			padding: 0;
		}

	</style>
</head>
<body class="gradientbackgr">


<div class="container-fluid">



	<jsp:include page="../general/neutralheader.jsp"/>
	<br>
	<div class = "container-fluid">
		 <h1>Orders on map: <button id="goBackBtn" class = "btn btn-primary" type = "submit" form="goBackForm">Go back</button></h1>
		<form action="${pageContext.request.contextPath}/index" method="get" id="goBackForm"></form>
		<div id = respFromGoogle></div>
		<div id="googleMap" style="width:100%;height:500px;"></div>
	</div>
	<jsp:include page="/WEB-INF/views/general/footer.jsp"/>
	
</div>

<script src="${pageContext.request.contextPath}/resources/web/js/googlemaps.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD-kLRYC4_d1Z7zDgGJj_DplCfC7a_Mr3k&callback=myMap"></script>
</body>
</html>
