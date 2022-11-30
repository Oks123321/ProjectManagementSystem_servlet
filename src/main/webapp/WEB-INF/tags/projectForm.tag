<%@tag description="Company Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="methodName" required="true" type="java.lang.String" %>
<div class="col-md-3 col-lg-3">
        <h4 class="lg-3">${methodName} project</h4>
        <form class="needs-validation" action="${pageContext.request.contextPath}/projects"
              method="post">
        <div class="row g-3">

            <div class="col-3">
                <label class="form-label" for="id">id</label>
                <input class="form-control" disabled id="id" name="id" value="${project.id}" placeholder="${project.id}" type="number">
            </div>

            <div class="col-3">
                <label class="form-label" for="name">Name</label>
                <div class="input-group has-validation">

                    <input class="form-control" id="name" name="name" value="${project.name}" placeholder="${project.name}" required="" type="text">
                </div>
            </div>

            <div class="col-3">
                <label class="form-label" for="descriptions">Descriptions</label>
                <div class="input-group has-validation">
                    <input class="form-control" id="descriptions" name="descriptions" value="${project.descriptions}" placeholder="${project.descriptions}" required="" type="text">
                </div>
            </div>

            <div class="col-3">
                <label class="form-label" for="cost">Cost</label>
                <div class="input-group has-validation">
                        <input class="form-control" id="cost" name="cost" value="${project.cost}" placeholder="${project.cost}" required="" type="number">
                </div>
            </div>

            <div class="col-3">
                <label class="form-label" for="date">Date</label>
                    <div class="input-group has-validation">
                        <input class="form-control" id="date" name="date" value="${project.date}" placeholder="${project.date}" required="" type="date">
                    </div>
            </div>
            <hr class="my-4">

                <button class="w-100 btn btn-secondary" type="submit"
                <c:if test="${methodName eq 'Update'}"> name="update_data" resource="${pageContext.request.contextPath}/projects" </c:if>>
                    ${methodName} project
                </button>
            <hr class="my-4">
                <a class="w-100 btn btn-secondary"
                   href="${pageContext.request.contextPath}/projects">Go to projects page</a>
        </div>
    </form>
</div>

