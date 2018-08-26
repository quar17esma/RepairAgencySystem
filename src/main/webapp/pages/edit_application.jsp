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
    <title><fmt:message key="title.edit.application"/></title>
</head>
<body>
<jsp:include page="/pages/header.jsp"/>
<div>
    <c:out value="${errorAddApplication}"/>
</div>
<div>
    <form name="addApplicationForm" method="POST" action="./add_application">
        <c:if test="${application != null}">
            <input type="hidden" name="applicationId" value="${application.id}">
        </c:if>

        <label><fmt:message key="label.product"/></label>
        <br/>
        <input type="text" name="product" value="${application.product}" required="required"/>
        <br/>

        <label><fmt:message key="label.repair.type"/></label>
        <br/>
        <input type="textarea" rows="4" cols="50"  name="repairType" value="${application.repairType}"
               required="required">
        <br/>

        <br/>
        <fmt:message var="buttonAddApplication" key="button.add.application"/>
        <input type="submit" value="${buttonAddApplication}"/>
    </form>
</div>

</body>
</html>
