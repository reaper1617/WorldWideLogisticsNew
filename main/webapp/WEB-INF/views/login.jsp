<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

	<%--<link rel="stylesheet" href="resources/web/css/bootstrap.css">--%>
	<%--<script src="resources/web/js/bootstrap.js"></script>--%>
	<%--<script src="resources/web/popper.js"></script>--%>
	<%--<script src="resources/web/jquery-3.3.1.js"></script>--%>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/web/css/adminmainpage.css">

</head>
<body class="gradientbackgr">
<div class="gradientstyle">

	<div class="container-fluid">

		<jsp:include page="/WEB-INF/views/general/loginheader.jsp"/>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<div class = "container-fluid">
			<div align="center" >
				<h1 class="text-primary">Welcome to logistic system!</h1>
				<br>
				<form action="<c:url value='login'/>" method="post">
						<div class="form-group">
							<label class="text-primary"  for="text">Personal number:</label>
							<input style="width: 40%" type="text" class="form-control" id="text" placeholder="Enter your personal number" name="username" value="">
						</div>
						<div class="form-group">
							<label class="text-primary" for="pwd">Password:</label>
							<input style="width: 40% "type="password" class="form-control" id="pwd" placeholder="Enter password" name="password">
						</div>

					<div>
							<button type="submit" class="btn btn-primary">Login</button>
					</div>
					<c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
						<div style="color: #bd2130">
							Your login attempt was not successful, try again.<br />
							<%--Reason: ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}--%>
						</div>
					</c:if>
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/general/footer.jsp"/>
	</div>
</div>
</body>
</html>
