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
<div class="container">
    <form class="form-horizontal" role="form" method="post">
        <input type="hidden" name="command" value="signup.post"/>
        <h2><fmt:message key="create.new.profile"/></h2>
        <div class="form-group">
            <label for="login" class="col-sm-3 control-label"><fmt:message key="user.login"/></label>
            <div class="col-sm-9">
                <input type="text" id="login" name="login"
                       placeholder="<fmt:message key="enter.login"/>" class="form-control" autofocus
                       value="<c:out value="${requestScope.user.getLogin()}"/>"/>
            </div>
        </div>

        <div class="form-group">
            <label for="password" class="col-sm-3 control-label"><fmt:message key="password"/></label>
            <div class="col-sm-9">
                <input type="password" id="password" name="password"
                       placeholder="<fmt:message key="enter.password"/>" class="form-control">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-9 col-sm-offset-3">
                <button type="submit" class="btn btn-primary btn-block"><fmt:message key="signup"/></button>
            </div>
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>

