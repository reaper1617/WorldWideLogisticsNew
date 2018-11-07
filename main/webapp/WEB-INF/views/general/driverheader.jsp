<%--
  Created by IntelliJ IDEA.
  User: Reaper
  Date: 23.10.2018
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<div class = "container-fluid">
    <h2>Fixed-top container</h2>
    <nav class="nav nav-tabs bg-primary bg-dark navbar-dark fixed-top">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/drivermainpage/0">Driver account</a>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs bg-primary bg-dark navbar-dark" role="tablist">
            <form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="get">
                <li class="nav-item">
                    <a class="nav-link bg-dark" data-toggle="tab" href="#" onclick="logOut()">Log out</a>
                </li>
            </form>
            <button id="logoutBtn" hidden type="submit" class="btn btn-dark" form="logoutForm">Log out</button></li>
        </ul>

    </nav>
</div>
<script>
    function logOut() {
        var logoutBtn = document.getElementById('logoutBtn');
        logoutBtn.click();
    }
</script>