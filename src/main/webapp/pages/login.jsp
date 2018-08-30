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
    <title><fmt:message key="title.login"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <br>
    <div class="row">
        <div class="col-md-1">
            <form name="engForm" method="POST" action="${pageContext.request.contextPath}/rest/change_locale">
                <input type="hidden" name="locale" value="en_US">
                <fmt:message var="buttonEng" key="button.english"/>
                <input type="submit" value="${buttonEng}">
            </form>
        </div>
        <div class="col-md-1">
            <form name="rusForm" method="POST" action="${pageContext.request.contextPath}/rest/change_locale">
                <input type="hidden" name="locale" value="ru_RU">
                <fmt:message var="buttonRus" key="button.russian"/>
                <input type="submit" value="${buttonRus}">
            </form>
        </div>
    </div>

    <div class="row">
        <c:out value="${successRegistrationMessage}"/>
        <c:out value="${errorLoginPassMessage}"/>
    </div>
    <div class="row">
        <h2><fmt:message key="title.login"/></h2>
        <div class="panel panel-default">
            <div class="panel-body">
                <form name="loginForm" method="POST" action="${pageContext.request.contextPath}/rest/login">
                    <div class="row">
                        <div class="col-md-1">
                            <label><fmt:message key="label.email"/></label>
                        </div>
                        <div class="col-md-1">
                            <input type="text" name="email" value=""/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-1">
                            <label><fmt:message key="label.password"/></label>
                        </div>
                        <div class="col-md-1">
                            <input type="password" name="password" value=""/>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-2 offset-md-1">
                            <fmt:message var="buttonLogin" key="button.login"/>
                            <input type="submit" value="${buttonLogin}"/>
                            <fmt:message var="buttonReset" key="button.reset"/>
                            <input type="reset" value="${buttonReset}">
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <hr/>
        <form name="registrationForm" method="POST" action="${pageContext.request.contextPath}/rest/edit_user">
            <fmt:message var="buttonRegistration" key="button.registration"/>
            <input type="submit" value="${buttonRegistration}">
        </form>
    </div>
</div>
</body>
</html>
