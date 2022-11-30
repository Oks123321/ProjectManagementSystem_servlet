<%@tag description="Company Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="methodName" required="true" type="java.lang.String" %>
<div class="col-md-3 col-lg-3">
    <h4 class="lg-3">${methodName} skill</h4>
    <form class="needs-validation" action="${pageContext.request.contextPath}/skills"
          method="post">
        <div class="row g-3">

            <div class="col-3">
                <label class="form-label" for="id">id</label>
                <input class="form-control" disabled id="id" name="id" value="${skill.id}" placeholder="${skill.id}"
                       type="number">
            </div>

            <div class="col-3">
                <label class="form-label" for="language">Language</label>
                <div class="input-group has-validation">
                    <input class="form-control" id="language" name="language" value="${skill.language}"
                           placeholder="${skill.language}" required="" type="text">
                </div>
            </div>

            <div class="col-3">
                <label class="form-label" for="level">Level </label>
                <select class="form-select" id="level" name="level" required="">
                    <option value="Junior" <c:if test="${skill.level eq 'Junior'}">selected</c:if>>Junior</option>
                    <option value="Middle" <c:if test="${skill.level eq 'Middle'}">selected</c:if>>Middle</option>
                    <option value="Senior" <c:if test="${skill.level eq 'Senior'}">selected</c:if>>Senior</option>
                </select>
            </div>
            <hr class="my-4">

            <button class="btn btn-secondary " type="submit"
                <c:if test="${methodName eq 'Update'}"> name="update_data" resource="${pageContext.request.contextPath}/skills" </c:if>>
                    ${methodName} skill
            </button>
            <hr class="my-4">
            <button type="button" class="btn btn-dark">
              <a href="${pageContext.request.contextPath}/skills">Go to skills page</a>
            </button>
        </div>
    </form>
</div>

