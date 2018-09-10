<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<c:if test="${pageContext.session.getAttribute('locale') == 'ru_RU'}">
    <fmt:setLocale value="ru_RU"/>
</c:if>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.welcome"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <jsp:include page="/pages/header.jsp"/>
    </div>
    <div class="row">
        <c:if test="${successAddApplicationMessage != null}">
            <div class="alert alert-success">
                <c:out value="${successAddApplicationMessage}"/>
            </div>
        </c:if>
        <c:if test="${successCompleteApplicationMessage != null}">
            <div class="alert alert-success">
                <c:out value="${successCompleteApplicationMessage}"/>
            </div>
        </c:if>
        <c:if test="${successDeclineApplicationMessage != null}">
            <div class="alert alert-success">
                <c:out value="${successDeclineApplicationMessage}"/>
            </div>
        </c:if>
        <c:if test="${errorDeclineApplicationMessage != null}">
            <div class="alert alert-danger">
                <c:out value="${errorDeclineApplicationMessage}"/>
            </div>
        </c:if>
        <c:if test="${successAcceptApplicationMessage != null}">
            <div class="alert alert-success">
                <c:out value="${successAcceptApplicationMessage}"/>
            </div>
        </c:if>
        <c:if test="${errorAcceptApplicationMessage != null}">
            <div class="alert alert-danger">
                <c:out value="${errorAcceptApplicationMessage}"/>
            </div>
        </c:if>
    </div>
    <div class="row">
        <fmt:message key="message.welcome"/>
    </div>
</div>

</body>
</html>
