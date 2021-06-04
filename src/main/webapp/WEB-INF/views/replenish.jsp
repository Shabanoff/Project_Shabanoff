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

<main class="form-signin">
    <c:if test="${not empty requestScope.errors}">
        <div class="alert alert-danger">
            <c:forEach items="${requestScope.errors}" var="error">
                <strong><fmt:message key="error"/></strong> <fmt:message key="${error}"/><br>
            </c:forEach>
        </div>
    </c:if>
        <c:if test="${not empty sessionScope.user}">
            <h1 class="title"><fmt:message key="replenish"/></h1>
            <form class="form-signin" method="post">
                <input type="hidden" name="command" value="replenish.post"/>
                <div class="form-floating">
                    <input type="increase" class="form-control" name="amount" id="amount" placeholder="<fmt:message key="enter.amount"/>">
                    <label for="amount"><fmt:message key="amount"/></label>
                </div>
                <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="replenish" /></button>
            </form>
        </c:if>
</main>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
