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
    <title><fmt:message key="title.registration"/></title>
</head>
<body>

<div>
    <c:out value="${errorRegistrationMessage}"/>
</div>

<div>
    <form name="registrationForm" method="POST" action="./add_user">
        <%--<input type="hidden" name="action" value="add_user"/>--%>

        <label><fmt:message key="label.phone"/></label>
        <br/>
        <input type="text" name="phone" value="${phone}" required="required"/>
        <c:out value="${errorBusyEmailMessage}"/>
        <br/>

        <label><fmt:message key="label.email"/></label>
        <br/>
        <input type="text" name="email" value="${email}" required="required"/>
        <c:out value="${errorBusyEmailMessage}"/>
        <br/>

        <label><fmt:message key="label.password"/></label>
        <br/>
        <input type="password" name="password" value="" required="required"/>
        <br/>

        <label><fmt:message key="label.name"/></label>
        <br/>
        <input type="text" name="name" value="${name}" required="required"/>
        <br/>

        <label><fmt:message key="label.surname"/></label>
        <br/>
        <input type="text" name="surname" value="${surname}" required="required"/>
        <br/>

        <label><fmt:message key="label.birth.date"/></label>
        <br/>
        <input type="date" name="birthDate" value="${birthDate}" required="required"/>
        <br/>

        <br/>
        <fmt:message var="buttonRegister" key="button.register"/>
        <input type="submit" value="${buttonRegister}"/>
    </form>
</div>

</body>
</html>
