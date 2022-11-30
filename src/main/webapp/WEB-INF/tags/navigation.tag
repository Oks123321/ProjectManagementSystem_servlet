<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Project Management System</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${contextPath}/companies">Companies</a></li>
            <li><a href="${contextPath}/developers">Developers</a></li>
            <li><a href="${contextPath}/skills">Skills</a></li>
            <li><a href="${contextPath}/projects">Projects</a></li>
            <li><a href="${contextPath}/customers">Customers</a></li>
        </ul>
    </div>
</nav>
