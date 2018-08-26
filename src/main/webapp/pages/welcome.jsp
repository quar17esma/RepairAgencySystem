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
</head>
<body>

<jsp:include page="/pages/header.jsp"/>

<div>
    <c:out value="${successAddApplicationMessage}"/>
    <c:out value="${successCompleteApplicationMessage}"/>
    <c:out value="${successDeclineApplicationMessage}"/>
    <c:out value="${successAcceptApplicationMessage}"/>
    <br>
</div>

<div>
    <br>
    <fmt:message key="message.welcome"/>
</div>

</body>
</html>
