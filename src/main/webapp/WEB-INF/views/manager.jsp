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

  <div class="jumbotron">
    <div class="container">
      <h1><fmt:message key="users"/></h1>
    </div>
  </div>
  <table class="table">
    <thead>
    <tr><h1><fmt:message key="users"/></h1></tr>
    <tr>
      <th scope="col"><fmt:message key="user.login"/></th>
      <th scope="col"><fmt:message key="user.balance"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
      <c:if test="${user.user}">
      <tr>
        <td><c:out value="${user.login}"/></td>
        <td><c:out value="${user.balance}"/><fmt:message key="currency"/></td>
      <c:if test="${user.active}">
        <td><form action="${pageContext.request.contextPath}/site/manager/users" method="post" >
          <input type="hidden" name="command" value="block"/>
          <input type="hidden" name="userId"
                 value="${user.id}"/>
          <button type="submit" class="btn btn-info"><fmt:message
                  key="user.block"/></button>
        </form>
        </td>
      </c:if>
        <c:if test="${user.blocked}">
          <td><form action="${pageContext.request.contextPath}/site/manager/users" method="post" >
            <input type="hidden" name="command" value="unblock"/>
            <input type="hidden" name="userId"
                   value="${user.id}"/>
            <button type="submit" class="btn btn-info"><fmt:message
                    key="user.unblock"/></button>
          </form>
          </td>
        </c:if>
      </tr>
      </c:if>
    </c:forEach>
    </tbody>
  </table>

</c:if>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
