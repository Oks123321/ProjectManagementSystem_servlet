<%@tag description="Company Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="methodName" required="true" type="java.lang.String" %>
<div class="col-md-3 col-lg-3">
    <h4 class="lg-3">${methodName} developer</h4>
    <form class="needs-validation" action="${pageContext.request.contextPath}/developers"
          method="post">
        <div class="row g-3">

            <div class="col-3">
                <label class="form-label" for="id">id</label>
                <input class="form-control" disabled id="id" name="id" value="${developer.id}"
                       placeholder="${developer.id}" type="number">
            </div>

            <div class="col-3">
                <label class="form-label" for="first_name">First_name </label>
                <input class="form-control" id="first_name" name="first_name" value="${developer.first_name}"
                       placeholder="${developer.first_name}" required type="text">
            </div>
            <div class="col-3">
                <label class="form-label" for="last_name">Last_name </label>
                <input class="form-control" id="last_name" name="last_name" value="${developer.last_name}"
                       placeholder="${developer.last_name}" required type="text">
            </div>

            <div class="col-3">
                <label class="form-label" for="age">Age</label>
                <input class="form-control" id="age" name="age" value="${developer.age}"
                       placeholder="${developer.age}" type="number">
            </div>

            <div class="col-3">
                <label class="form-label" for="salary">Salary</label>
                <input class="form-control" id="salary" name="salary" value="${developer.salary}"
                       placeholder="${developer.salary}" type="number">
            </div>
            <hr class="my-4">

            <button class="btn btn-secondary " type="submit"
                    <c:if test="${methodName eq 'Update'}"> name="update_data" resource="${pageContext.request.contextPath}/developers" </c:if>>
                ${methodName} developer
            </button>
            <hr class="my-4">

            <button type="button" class="btn btn-dark">
              <a href="${pageContext.request.contextPath}/developers">Go to developers page</a>
            </button>
        </div>
    </form>
</div>

