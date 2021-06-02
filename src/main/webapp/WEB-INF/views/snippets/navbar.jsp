<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="customTag" uri="/WEB-INF/customTags/selectedPageTag" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}'/>
<fmt:setBundle basename="i18n.lang"/>

<c:set var="loginPage" scope="page" value="/WEB-INF/views/login.jsp"/>
<c:set var="signUpPage" scope="page" value="/WEB-INF/views/signup.jsp"/>
<c:set var="replenishPage" scope="page" value="/WEB-INF/views/replenish.jsp"/>
<c:set var="usersListPage" scope="page" value="/WEB-INF/views/users.jsp"/>

<c:set var="currPage" scope="page">
    <customTag:currPage/>
</c:set>

<nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/site/account">Internet Provider</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav me-auto mb-2 mb-md-0">
                <li class="nav-item">
                    <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/service" role="button"><fmt:message key="service"/></a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/users" role="button"><fmt:message key="manager.users"/></a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/create_user" role="button"><fmt:message key="create.user"/></a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/create_tariff" role="button"><fmt:message key="create.tariff"/></a>
                </li>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbarCollapse2">
        <ul class="navbar-nav ms-auto mb-2 mb-md-0">
            <c:if test="${not empty sessionScope.user}">
                <li>
                    <a class="nav-link" href="#"><fmt:message key="welcome"/> </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="dropdown05" data-bs-toggle="dropdown" aria-expanded="false"><c:out value="${sessionScope.user.getLogin()}"/></a>
                    <ul class="dropdown-menu" aria-labelledby="dropdown05">
                        <li><a class="btn btn-link" href="${pageContext.request.contextPath}/site/replenish" role="button"><fmt:message key="replenish"/></a></li>
                    </ul>
                </li>

            </c:if>
        </ul>
        <ul class="navbar-nav ms-auto mb-2 mb-md-0">
            <c:if test="${empty sessionScope.user}">
                <c:choose>
                    <c:when test="${loginPage.equals(currPage)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/login" role="button"><fmt:message key="login"/></a>
                </li>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <li>
                    <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/site/logout?command=logout" role="button"><fmt:message key="logout"/></a>
                </li>
            </c:if>
        </ul>
        <ul class = "navbar-nav ms-auto mb-2 mb-md-1">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown03" data-bs-toggle="dropdown" aria-expanded="false">${sessionScope.locale.getLanguage().toUpperCase()}</a>
                <ul class="dropdown-menu" aria-labelledby="dropdown03">
                    <c:forEach items="${applicationScope.supportedLocales}" var="lang">
                        <li><a class="dropdown-item" href="?lang=${lang}">${lang.toUpperCase()}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </ul>
        </div>
    </div>
</nav>

