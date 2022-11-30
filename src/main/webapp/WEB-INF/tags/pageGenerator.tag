<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<t:header></t:header>
<body>
<t:navigation></t:navigation>
<div class="container">
    <main>
        <jsp:doBody/>
    </main>
</div>
</body>
<t:script></t:script>
</html>
