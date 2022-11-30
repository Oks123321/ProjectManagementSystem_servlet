<%@tag description="Project Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Name</th>
        <th scope="col">Descriptions</th>
        <th scope="col">Cost</th>
        <th scope="col">Date</th>
        <th scope="col">Developers</th>
        <th scope="col" colspan="2">Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${projects}" var="project">
        <tr>
            <th scope="row">${project.id}</th>
            <td>${project.name}</td>
            <td>${project.descriptions}</td>
            <td>${project.cost}</td>
            <td>${project.date}</td>
            <td><a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/developers?projectId=${project.id}">Get list of
                developers</a>
            </td>
            <td><a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/projectEdit?id=${project.id}">Edit</a>
            </td>
            <td>
                <button type="button" class="btn btn-danger"
                        onclick="makeDELETErequest('${pageContext.request.contextPath}/projects?id=${project.id}')">
                    Delete
                </button>
            </td>
        </tr>
        </c:forEach>

    </tbody>

</table>
