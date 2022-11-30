<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:readPageGenerator entityName="project">
    <jsp:body>
        <t:projectsTable></t:projectsTable>
    </jsp:body>
</t:readPageGenerator>
