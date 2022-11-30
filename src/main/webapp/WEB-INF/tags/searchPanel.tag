<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="entityName" required="true" type="java.lang.String" %>
<div class="row gy-3">
    <div class="col-md-3">
        <button type="button" class="btn btn-secondary">
           <a href="${pageContext.request.contextPath}/${entityName}Create">Create ${entityName}
           </a>
           </button>
    </div>
        <div class="col-md-6">
        <form class="needs-validation" method="GET">
            <div class="col-md-3">
                <label class="form-label" for="id">Id for search</label>
                <input class="form-control" name="id" id="id" placeholder="0" required type="number">
            </div>
            <div class="col-md-3">
                <button class="btn btn-dark" type="submit">Find ${entityName} by id</button>
            </div>
        </form>
    </div>
</div>
<hr class="my-4">
<hr class="my-4">
