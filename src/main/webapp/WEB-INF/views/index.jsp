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
<div class="jumbotron">
    <div class="container">
        <h1>Internet Provider</h1>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-4">
            <h2>Internet</h2>
            <p>The Internet (or internet) is the global system of interconnected computer networks that uses the Internet protocol suite (TCP/IP) to communicate between networks and devices. It is a network of networks that consists of private, public, academic, business, and government networks of local to global scope, linked by a broad array of electronic, wireless, and optical networking technologies.</p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
            <h2>Phone</h2>
            <p>A telephone is a telecommunications device that permits two or more users to conduct a conversation when they are too far apart to be heard directly. A telephone converts sound, typically and most efficiently the human voice, into electronic signals that are transmitted via cables  </p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-md-4">
            <h2>TV</h2>
            <p>Television (TV), sometimes shortened to tele or telly, is a telecommunication medium used for transmitting moving images in monochrome (black and white), or in color, and in two or three dimensions and sound. The term can refer to a television set, a television show, or the medium of television transmission. Television is a mass medium for advertising, entertainment, news, and sports.</p>
            <p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        </div>
    </div>

    <hr>
</div>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
