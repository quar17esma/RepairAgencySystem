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
    <title>Header</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="page-header">
    <fmt:message key="message.hello"/> ${user.name}!
    <br/>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="./"><fmt:message key="repair.agency"/></a>
            </div>
            <ul class="nav navbar-nav">
                <li>
                    <a href="./show_feedbacks"><fmt:message key="button.feedbacks"/></a>
                </li>
                <%--For USER--%>
                <c:if test="${sessionScope.user.role == 'USER'}">
                    <li>
                        <a href="./show_my_applications"><fmt:message key="button.my.applications"/></a>
                    </li>
                    <li>
                        <a href="./edit_application"><fmt:message key="button.add.application"/></a>
                    </li>
                </c:if>
                <%--For MANAGER--%>
                <c:if test="${sessionScope.user.role == 'MANAGER'}">
                    <li>
                        <a href="./show_all_applications"><fmt:message key="button.applications"/></a>
                    </li>
                </c:if>
                <%--For REPAIRER--%>
                <c:if test="${sessionScope.user.role == 'REPAIRER'}">
                    <li>
                        <a href="./show_accepted_applications"><fmt:message key="button.applications"/></a>
                    </li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="./logout">
                        <span class="glyphicon glyphicon-log-out"></span>
                        <fmt:message key="button.logout"/>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</div>
</body>
</html>
