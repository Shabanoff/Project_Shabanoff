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
  <c:forEach var="service" items="${requestScope.services}">
    <div class="jumbotron">
      <div class="container">
        <h1>${service.serviceName}</h1>
      </div>
    </div>
    <table class="table">
      <thead>
      <tr>
        <th scope="col"><fmt:message key="tariff.name"/></th>
        <th scope="col"><fmt:message key="tariff.description"/></th>
        <th scope="col"><fmt:message key="tariff.cost"/></th>
      </tr>
      </thead>
      <tbody>
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
        <c:if test="${not empty sessionScope.user}">
        <td><form action="${pageContext.request.contextPath}/site/service" method="post" >
          <input type="hidden" name="command" value="plug"/>
          <input type="hidden" name="tariffId"
                 value="${tariff.id}"/>
          <button type="submit" class="btn btn-info"><fmt:message
                  key="tariff.plug"/></button>
        </form>
        </td>
        </c:if>
      </tr>
    </c:forEach>
      </tbody>
    </table>
  </c:forEach>


<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>