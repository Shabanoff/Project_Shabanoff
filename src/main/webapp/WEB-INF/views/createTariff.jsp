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
    <div>
    <select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" name="serviceId" id="serviceId" >
      <option selected><fmt:message key="services"/></option>
      <c:forEach items="${requestScope.services}" var="service">

      <option  value=${service.id} >${service.serviceName}</option>
      </c:forEach>
    </select>
    </div>

    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text" id="basic-addon1"><fmt:message key="enter.name"/></span>
      </div>
      <input type="text" class="form-control" name="tariffName" id="tariff.name" placeholder="<fmt:message key="enter.name"/>" aria-label="Username" aria-describedby="basic-addon1">
    </div>

    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text"><fmt:message key="enter.cost"/></span>
      </div>
      <input type="text" class="form-control" name="cost" id="cost" aria-label="Amount (to the nearest dollar)">
      <div class="input-group-append">
        <span class="input-group-text">.00 <fmt:message key="currency"/></span>
      </div>

    </div>
      <c:forEach items="${requestScope.options}" var="option">
      <div class="form-check">
        <input class="form-check-input" name="optionId" type="checkbox" value=${option.id} id=${option.id}>
        <label class="form-check-label" for=${option.id}>
            ${option.definition}
        </label>
      </div>
        </c:forEach>
    </div>

    <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="tariff.create" /></button>
  </form>

</c:if>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
