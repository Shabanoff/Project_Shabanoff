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
    <table class="table">
      <c:forEach var="service" items="${requestScope.services}">
        <tbody>
      <tr><div class="container">
        <th><h1>${service.serviceName}</h1></th>

        <c:if test="${sessionScope.user.manager}">
        <th><form action="${pageContext.request.contextPath}/site/service" method="post" >
          <input type="hidden" name="command" value="delete.service"/>
          <input type="hidden" name="serviceId"
                 value="${service.id}"/>
          <button type="submit" class="btn btn-danger"><fmt:message
                  key="service.delete"/></button>
        </form></th>
        </c:if>
      </div></tr>
      <tr>
        <th scope="col"><fmt:message key="tariff.name"/></th>
        <th scope="col"><fmt:message key="tariff.description"/></th>
        <th scope="col"><fmt:message key="tariff.cost"/></th>
        <c:if test="${sessionScope.user.manager}">
        <th scope="col"><fmt:message key="change.cost"/></th>
                </c:if>
      </tr>

    <c:forEach var="tariff" items="${service.tariffs}">
      <tr>
        <td><c:out value="${tariff.tariffName}"/></td>
        <td>
          <ul>
            <c:forEach var="option" items="${tariff.includedOptions}">
              <li><c:out value="${option.definition}"/></li>
            </c:forEach>
          </ul>
        </td>
        <td><c:out value="${tariff.cost}"/></td>
        <c:if test="${sessionScope.user.manager}">
        <td>
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
        <td><form action="${pageContext.request.contextPath}/site/service" method="post" >
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



<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>