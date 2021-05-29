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

<div class="panel-title text-center row col-md-12">
    <h1 class="title"><fmt:message key="user.replenish"/></h1>
    <hr/>
</div>

<div class="container">
    <div class="row col-md-12">
        <c:if test="${not empty requestScope.messages}">
            <div class="alert alert-success">
                <c:forEach items="${requestScope.messages}" var="message">
                    <strong><fmt:message key="info"/></strong> <fmt:message key="${message}"/><br>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${not empty requestScope.warning and not empty requestScope.amount}">
            <div class="alert alert-warning">
                <fmt:message key="${requestScope.warning}"/>&nbsp${requestScope.amount}<fmt:message key="currency"/><br>
            </div>
        </c:if>


        <c:if test="${not empty requestScope.errors}">
            <div class="alert alert-danger">
                <c:forEach items="${requestScope.errors}" var="error">
                    <strong><fmt:message key="error"/></strong> <fmt:message key="${error}"/><br>
                </c:forEach>
            </div>
        </c:if>

    </div>
</div>


<div class="container">
    <div class="row col-md-5">
        <c:if test="${empty requestScope.messages}">
        <c:choose>
        <c:when test="${not empty sessionScope.user and sessionScope.user.isManager()}">
        <form class="form-inline" action="${pageContext.request.contextPath}/site/manager/replenish" method="post">
            </c:when>
            <c:when test="${not empty sessionScope.user and sessionScope.user.isUser()}">
            <form class="form-inline" action="${pageContext.request.contextPath}/site/user/replenish" method="post">
                </c:when>
                </c:choose>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <label for="refillableUser">
                            <fmt:message key="refillable.user"/>:
                        </label>
                        <select name="refillableUser" class="form-control" id="refillableUser">
                            <c:forEach var="refillableUser" items="${requestScope.refillableAccounts}">
                                <c:if test="${refillableUser.isNotClosed()}">
                                    <option>${refillableUser.getAccountNumber()}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </li>
                    <li class="list-group-item">
                        <label for="senderUser">
                            <fmt:message key="select.user"/>:
                        </label>
                        <select name="senderUser" class="form-control" id="senderUser">
                            <c:forEach var="senderUser" items="${requestScope.senderUser}">

                                <c:if test="${senderUser.isActive()}">
                                    <option>${senderUser.getUserNumber()}&nbsp
                                        <c:choose>
                                            <c:otherwise>
                                                (${senderUser.getBalance()}<fmt:message key="currency"/>)
                                            </c:otherwise>
                                        </c:choose>
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </li>
                    <li class="list-group-item"><b><fmt:message key="amount"/></b>
                        <input name="amount" type="number" class="form-control" id="amount" step="0.0001"
                               placeholder="<fmt:message key="enter.amount" />">
                        <fmt:message key="currency"/>
                    </li>
                    <li class="list-group-item">
                        <input type="hidden" name="command" value="<c:out value="${requestScope.command}"/>"/>
                        <button type="submit" class="btn btn-danger">
                            <fmt:message key="replenish"/>
                        </button>
                    </li>
                </ul>
            </form>
            </c:if>
    </div>
</div>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
