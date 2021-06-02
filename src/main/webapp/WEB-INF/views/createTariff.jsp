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
    <div class="dropdown-menu" aria-labelledby="dropdown03">
      <c:forEach items="${requestScope.services}" var="service">
        <li><a class="dropdown-item" href="?service=${service}">${service.serviceName}</a></li>
      </c:forEach>
    </div>
    <div class="form-floating">
      <input type="email" class="form-control" name="login" id="tariff.name" placeholder="<fmt:message key="enter.name"/>">
      <label for="tariff.name"><fmt:message key="enter.name"/></label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" name="password" id="cost" placeholder="<fmt:message key="enter.password"/>" >
      <label for="cost"><fmt:message key="enter.description"/></label>
    </div>

    <div class="form-floating">
      <input type="password" class="form-control" name="password" id="description" placeholder="<fmt:message key="enter.password"/>" >
      <label for="description"><fmt:message key="enter.description"/></label>
    </div>


    <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="tariff.create" /></button>
  </form>

</c:if>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
