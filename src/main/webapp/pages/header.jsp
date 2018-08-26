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
    <style>
        .headerButton {
            float: left;
        }
    </style>
    <title>Header</title>
</head>
<body>
<div class="header">
    <h1><fmt:message key="repair.agency"/></h1>
<hr/>
<fmt:message key="message.hello"/> ${user.name}!
<br/>
<div>
    <form class="headerButton" name="logoutForm" method="POST" action="./logout">
        <fmt:message var="buttonLogout" key="button.logout"/>
        <input type="submit" value="${buttonLogout}"/>
    </form>
    <form class="headerButton" name="myApplicationsForm" method="POST" action="./show_my_applications">
        <fmt:message var="buttonMyApplications" key="button.my.applications"/>
        <input type="submit" value="${buttonMyApplications}">
    </form>
    <form class="headerButton" name="addNewApplicationForm" method="POST" action="./edit_application">
        <fmt:message var="buttonAddApplication" key="button.add.application"/>
        <input type="submit" value="${buttonAddApplication}">
    </form>
    <%--<form class="headerButton" name="addNewFoodForm" method="POST" action="./edit_food">--%>
        <%--<fmt:message var="buttonAddFood" key="button.add.food"/>--%>
        <%--<input type="submit" value="${buttonAddFood}">--%>
    <%--</form>--%>
    <br/>
    <hr/>
</div>
</div>
</body>
</html>
