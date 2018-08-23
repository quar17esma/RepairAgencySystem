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
</head>
<body>

<div>
    <form name="engForm" method="POST" action="${pageContext.request.contextPath}/rest/change_locale">
        <input type="hidden" name="locale" value="en_US">
        <fmt:message var="buttonEng" key="button.english"/>
        <input type="submit" value="${buttonEng}">
    </form>
    <form name="rusForm" method="POST" action="${pageContext.request.contextPath}/rest/change_locale">
        <input type="hidden" name="locale" value="ru_RU">
        <fmt:message var="buttonRus" key="button.russian"/>
        <input type="submit" value="${buttonRus}">
    </form>
    <br>
</div>

<div>
    <c:out value="${successRegistrationMessage}"/>
    <c:out value="${errorLoginPassMessage}"/>
</div>
<div>
    <form name="loginForm" method="POST" action="${pageContext.request.contextPath}/rest/login">
        <label><fmt:message key="label.email"/></label>
        <br/>
        <input type="text" name="email" value=""/>
        <br/>
        <label><fmt:message key="label.password"/></label>
        <br/>
        <input type="password" name="password" value=""/>
        <br/>
        <fmt:message var="buttonLogin" key="button.login"/>
        <input type="submit" value="${buttonLogin}"/>
        <fmt:message var="buttonReset" key="button.reset"/>
        <input type="reset" value="${buttonReset}">
    </form>
    <hr/>
    <form name="registrationForm" method="POST" action="${pageContext.request.contextPath}/rest/edit_user">
        <fmt:message var="buttonRegistration" key="button.registration"/>
        <input type="submit" value="${buttonRegistration}">
    </form>
</div>

</body>
</html>
