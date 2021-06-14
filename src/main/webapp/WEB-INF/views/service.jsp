<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

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
<div class="position-relative">
  <div class="position-absolute top-0 end-0">
    <table>
      <thead>
      <th><h4><p class="text-primary"><fmt:message
              key="sort"/></p></h4></th>
      <th><div class="row">
        <div class="col-md-4 col-md-offset-4">
          <form action="${pageContext.request.contextPath}/site/service" method="post" >
            <input type="hidden" name="command" value="asc"/>
            <button type="submit" class="btn btn-info"><fmt:message
                    key="asc.sort"/></button>
          </form>
        </div>
      </div></th>
      <th><div class="row">
        <div class="col-md-4 col-md-offset-4">
          <form action="${pageContext.request.contextPath}/site/service" method="post" >
            <input type="hidden" name="command" value="desc"/>
            <button type="submit" class="btn btn-info"><fmt:message
                    key="desc.sort"/></button>
          </form>
        </div>
      </div></th>
      </thead>
    </table>
  </div>
</div>
<table class="table">
  <thead>
  <tr>
    <th class="col-2" scope="col"><fmt:message key="tariff.name"/></th>
    <th class="col-4" scope="col"><fmt:message key="tariff.description"/></th>
    <th scope="col-2"><fmt:message key="tariff.cost"/></th>
    <c:if test="${sessionScope.user.manager}">
      <th class="col-1"scope="col"><fmt:message key="change.cost"/></th>
    </c:if>
  </tr>
  </thead>
  <c:forEach var="service" items="${requestScope.services}">
    <tbody>
    <tr><div class="container">
      <th class="text-center"><h1>${service.serviceName}</h1></th>
      <c:if test="${sessionScope.user.manager}">
      <th><form action="${pageContext.request.contextPath}/site/service" method="post" >
        <input type="hidden" name="command" value="delete.service"/>
        <input type="hidden" name="serviceId"
               value="${service.id}"/>
        <button type="submit" class="btn btn-danger"><fmt:message
                key="service.delete"/></button>
      </form></th>
      </c:if>

      <c:forEach var="tariff" items="${service.tariffs}">
    <tr>
      <td class="col-2"><c:out value="${tariff.tariffName}"/></td>
      <td class="col-4">
        <ul>
          <c:forEach var="option" items="${tariff.includedOptions}">
            <li><c:out value="${option.definition}"/></li>
          </c:forEach>
        </ul>
      </td>
      <td><c:out value="${tariff.cost}"/></td>
      <c:if test="${sessionScope.user.manager}">
        <td class="col-2">
          <table>
            <tr>
              <form class="form-inline" action="${pageContext.request.contextPath}/site/service" method="post">
                <input type="hidden" name="command" value="change.cost"/>
                <div class="form-group mx-sm-3 mb-2">
                  <td><label for="newCost" class="sr-only"><fmt:message key="amount"/></label></td>
                  <td><input  class="form-control" name="newCost" id="newCost" placeholder=<fmt:message key="amount"/>>
                    <input type="hidden" name="tariffId"
                           value="${tariff.id}"/></td>
                </div>
                <td><button type="submit" class="btn btn-primary mb-2"><fmt:message key="change"/></button></td>
              </form>
            </tr>
          </table>
        </td>
      </c:if>
      <c:if test="${not empty sessionScope.user and sessionScope.user.user and sessionScope.user.active}">
        <td class="col-1"><form action="${pageContext.request.contextPath}/site/service" method="post" >
          <input type="hidden" name="command" value="plug"/>
          <input type="hidden" name="tariffId"
                 value="${tariff.id}"/>
          <button type="submit" class="btn btn-info"><fmt:message
                  key="tariff.plug"/></button>
        </form>
        </td>
      </c:if>
      <c:if test="${sessionScope.user.manager}">
        <td><form action="${pageContext.request.contextPath}/site/service" method="post" >
          <input type="hidden" name="command" value="delete.tariff"/>
          <input type="hidden" name="tariffId"
                 value="${tariff.id}"/>
          <button type="submit" class="btn btn-warning"><fmt:message
                  key="tariff.delete"/></button>
        </form>
        </td>
      </c:if>
    </tr>
    </c:forEach>
    </tbody>
  </c:forEach>
</table>
<div class="d-grid gap-2">
  <form action="${pageContext.request.contextPath}/site/service" method="post" >
    <input type="hidden" name="command" value="print"/>
    <button class="btn btn-primary" type="submit"><fmt:message key="save"/></button>
  </form>
</div>


<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>