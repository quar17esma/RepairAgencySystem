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
        .button {
            float: left;
        }
    </style>
    <title><fmt:message key="title.feedbacks"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="/pages/header.jsp"/>
<div>
<c:out value="${successAddFeedbackMessage}"/>
</div>
<div>
    <h2><fmt:message key="title.feedbacks"/></h2>

    <c:forEach items="${feedbacks}" var="feedback">
        <div>
            <label><fmt:message key="label.author"/></label>
            <%--<c:out value="${}"/><br>--%>
            <label><fmt:message key="label.mark"/></label>
            <c:out value="${feedback.mark}"/><br>
            <div>
                <c:out value="${feedback.comment}"/><br>
            </div>
            <label><fmt:message key="label.date"/></label>
            <%--<fmt:formatDate value="${feedback.dateTime}" type="both" dateStyle="full"/> <br/>--%>
            <c:out value="${feedback.dateTime}"/><br>
        </div>
        <br>
    </c:forEach>
    <br/>
</div>
<div>
    <c:forEach begin="1" end="${pagesQuantity}" varStatus="loop">
        <form class="button" name="feedbacksPagesForm" method="POST" action="./show_feedbacks">
            <input type="hidden" name="page" value="${loop.count}">
            <input type="submit" value="${loop.count}">
        </form>
    </c:forEach>
</div>
</body>
</html>
