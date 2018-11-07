<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<head>
	<title>Action failed!</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>


	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/web/css/adminmainpage.css">

</head>
<body class="gradientbackgr">
<div class="gradientstyle">

	<div class="container-fluid">

		<jsp:include page="general/neutralheader.jsp"/>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div class = "container-fluid">
		<div align="center" >
			<h1 class="text-primary">Log in failed!</h1>
			<br>
			<h2>Invalid personal number or password.</h2>
			<form action="${pageContext.request.contextPath}/index" method="get">
				<button type="submit" class="btn btn-primary">Go home</button>
			</form>
		</div>
	</div>
	<jsp:include page="general/footer.jsp"/>
</div>
</body>
</html>
