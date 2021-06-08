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

    <table class="table">
        <thead>
        <tr><h1><fmt:message key="manager.users"/></h1></tr>
        <tr class="d-flex">
            <th class="col-5" scope="col"><fmt:message key="user.login"/></th>
            <th class="col-3" scope="col"><fmt:message key="user.balance"/></th>
            <th class="col-1" scope="col"><fmt:message key="user.balance"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${requestScope.users}">
            <c:if test="${user.user}">
                <tr class="d-flex">
                    <td class="col-5"><c:out value="${user.login}"/></td>
                    <td class="col-3"><c:out value="${user.balance}"/><fmt:message key="currency"/></td>
                    <c:if test="${user.active}">
                        <td class="col-1"><form action="${pageContext.request.contextPath}/site/manager/users" method="post" >
                            <input type="hidden" name="command" value="block"/>
                            <input type="hidden" name="userId"
                                   value="${user.id}"/>
                            <button type="submit" class="btn btn-info"><fmt:message
                                    key="user.block"/></button>
                        </form>
                        </td>
                    </c:if>
                    <c:if test="${user.blocked}">
                        <td class="col-1"><form action="${pageContext.request.contextPath}/site/manager/users" method="post" >
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


<%--For displaying Previous link except for the 1st page --%>
<div class="d-flex justify-content-center">
<nav aria-label="Page navigation example">
    <ul class="pagination">
        <c:if test="${requestScope.currentPage != 1}">
        <li class="page-item">
            <a class="page-link" href="${pageContext.request.contextPath}/site/manager/users?page=${requestScope.currentPage - 1}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li></c:if>
        <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
        <c:choose>
        <c:when test="${requestScope.currentPage eq i}">
        <li class="page-item active"><a class="page-link" >${i}</a></li>
        </c:when>
        <c:otherwise>
        <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/site/manager/users?page=${i}">${i}</a></li>
        </c:otherwise>
        </c:choose>
        </c:forEach>
        <c:if test="${requestScope.currentPage lt requestScope.numberOfPages}">
        <li class="page-item">
            <a class="page-link" href="${pageContext.request.contextPath}/site/manager/users?page=${requestScope.currentPage + 1}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
        </c:if>
    </ul>
</nav>
</div>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>