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
        <%--For MANAGER--%>
        <c:if test="${sessionScope.user.role == 'USER'}">
            <form class="headerButton" name="myApplicationsForm" method="POST" action="./show_my_applications">
                <fmt:message var="buttonMyApplications" key="button.my.applications"/>
                <input type="submit" value="${buttonMyApplications}">
            </form>
        </c:if>
        <form class="headerButton" name="addNewApplicationForm" method="POST" action="./edit_application">
            <fmt:message var="buttonAddApplication" key="button.add.application"/>
            <input type="submit" value="${buttonAddApplication}">
        </form>
        <%--For MANAGER--%>
        <c:if test="${sessionScope.user.role == 'MANAGER'}">
            <form class="headerButton" name="showApplicationsForm" method="POST" action="./show_applications">
                <fmt:message var="buttonApplications" key="button.applications"/>
                <input type="submit" value="${buttonApplications}">
            </form>
        </c:if>
        <%--For REPAIRER--%>
        <c:if test="${sessionScope.user.role == 'REPAIRER'}">
            <form class="headerButton" name="showAcceptedApplicationsForm" method="POST"
                  action="./show_accepted_applications">
                <fmt:message var="buttonApplications" key="button.applications"/>
                <input type="submit" value="${buttonApplications}">
            </form>
        </c:if>
        <br/>
        <hr/>
    </div>
</div>
</body>
</html>
