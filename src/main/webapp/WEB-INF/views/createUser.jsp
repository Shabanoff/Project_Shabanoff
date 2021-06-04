<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp"/>
<c:if test="${not empty requestScope.errors}">
    <div class="alert alert-danger">
        <c:forEach items="${requestScope.errors}" var="error">
            <strong><fmt:message key="error"/></strong> <fmt:message key="${error}"/><br>
        </c:forEach>
    </div>
</c:if>
<c:if test="${not empty sessionScope.user }">

    <form class="form-signin" method="post">
        <input type="hidden" name="command" value="post"/>
        <div class="form-floating">
            <input  class="form-control" name="login" id="login" placeholder="<fmt:message key="enter.login"/>">
            <label for="login"><fmt:message key="enter.login"/></label>
        </div>

        <div class="form-floating">
            <input type="password" class="form-control" name="password" id="password" placeholder="<fmt:message key="enter.password"/>" required>
            <label for="password"><fmt:message key="enter.password"/></label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="create" /></button>
    </form>

</c:if>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
