<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="entityName" required="true" %>
<t:pageGenerator>
    <t:searchPanel entityName="${entityName}"></t:searchPanel>
    <jsp:doBody/>
</t:pageGenerator>
