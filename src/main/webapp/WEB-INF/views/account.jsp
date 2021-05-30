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

<c:if test="${not empty sessionScope.user and sessionScope.user.isUser}">
    <c:forEach var="includedPackage" items="${requestScope.includedPackages}">
        <div class="jumbotron">
            <div class="container">
                <h1>${includedPackage.service.serviceName}</h1>
            </div>
        </div>
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="tariff.name"/></th>
            <th scope="col"><fmt:message key="tariff.description"/></th>
            <th scope="col"><fmt:message key="tariff.cost"/></th>
            <th scope="col"><fmt:message key="subscription.date"/></th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td><c:out value="${includedPackage.tariff.tariffName}"/></td>
                <td>
                    <ul>
                    <c:forEach var="option" items="${includedPackage.tariff.includedOptions}">
                        <li><c:out value="${option.definition}"/></li>
                    </c:forEach>
                    </ul>
                </td>
                <td><c:out value="${includedPackage.tariff.cost}"/></td>
                <td><c:out value="${includedPackage.subscriptionDate}"/></td>
            </tr>
        </tbody>
    </table>
    </c:forEach>
</c:if>
<c:if test="${ sessionScope.user.isManager()}">

        <div class="jumbotron">
            <div class="container">
                <h1><fmt:message key="users"/></h1>
            </div>
        </div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="user.login"/></th>
                <th scope="col"><fmt:message key="user.balance"/></th>
                <th scope="col"><fmt:message key="user.status"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.balance}"/></td>
                <td><c:out value="${user.staus}"/></td>
            </tr>
            </c:forEach>
            </tbody>
        </table>

</c:if>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
