<%@tag description="Company Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Name</th>
        <th scope="col">Country</th>
        <th scope="col" colspan="2">Actions</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${companies}" var="company">
        <tr>
            <th scope="row">${company.id}</th>
            <td>${company.name}</td>
            <td>${company.country}</td>
            <td><a class="btn btn-secondary"
                href="${pageContext.request.contextPath}/companyEdit?id=${company.id}">Edit</a>
            </td>
            <td>
                <button type="button" class="btn btn-danger"
                        onclick="makeDELETErequest('${pageContext.request.contextPath}/companies?id=${company.id}')">
                    Delete
                </button>
            </td>
        </tr>
    </c:forEach>

    </tbody>

</table>
