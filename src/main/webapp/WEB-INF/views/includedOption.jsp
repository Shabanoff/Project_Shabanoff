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
        <thead>
        <tr>
            <th scope="col"><fmt:message key="definition"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="option" items="${requestScope.options}">
            <tr>
                <td><c:out value="${option.definition}"/></td>

            <td><form action="${pageContext.request.contextPath}/site/manager/included_option" method="post" >
                <input type="hidden" name="command" value="post"/>
                <input type="hidden" name="optionId"
                       value="${option.id}"/>
                <button type="submit" class="btn btn-info"><fmt:message
                        key="delete"/></button>
            </form>
            </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>