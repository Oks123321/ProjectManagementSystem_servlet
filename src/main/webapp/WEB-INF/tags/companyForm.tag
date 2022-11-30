<%@tag description="Company Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="methodName" required="true" type="java.lang.String" %>
<div class="col-md-3 col-lg-3">
    <h4 class="lg-3">${methodName} company</h4>
    <form class="needs-validation" action="${pageContext.request.contextPath}/companies" method="post" novalidate="">
        <div class="row g-3">

            <div class="col-3">
                <label class="form-label" for="id">id</label>
                <input class="form-control" disabled id="id" name="id" value="${company.id}"  type="number">
            </div>

            <div class="col-3">
                <label class="form-label" for="name">Name</label>
                <input class="form-control" id="name" name="name" value="${company.name}" placeholder="${company.name}" required type="text">
            </div>

            <div class="col-3">
                <label class="form-label" for="country">Country </label>
                <input class="form-control" id="country" name="country" value="${company.country}" placeholder="${company.country}" type="text">
            </div>

            <hr class="my-4">

            <button class="w-100 btn btn-dark" type="submit"
                <c:if test="${methodName eq 'Update'}"> name="update_data" resource="${pageContext.request.contextPath}/companies" </c:if>>
                ${methodName} company
            </button>
            <hr class="my-4">
            <button type="button" class="btn btn-dark">
                <a href="${pageContext.request.contextPath}/companies">Go to companies page</a>
            </button>
        </div>
    </form>
</div>

