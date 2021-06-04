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

<c:if test="${not empty sessionScope.user }">
    <c:if test="${sessionScope.user.user }">
    <div class="jumbotron">
        <div class="container">
            <h1><fmt:message key="user.balance"/>${sessionScope.user.balance}<fmt:message key="currency"/></h1>
        </div>
    </div>
    </c:if>
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
                <td>
                    <form action="${pageContext.request.contextPath}/site/account" method="post" >
                <input type="hidden" name="command" value="delete"/>
                <input type="hidden" name="includedPackageId"
                       value="${includedPackage.id}"/>
                <button type="submit" class="btn btn-info"><fmt:message
                        key="disable"/></button>
                     </form>
                </td>
            </tr>
        </tbody>
    </table>
    </c:forEach>
</c:if>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
