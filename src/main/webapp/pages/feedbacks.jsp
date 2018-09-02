<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="en_US"/>
<c:if test="${pageContext.session.getAttribute('locale') == 'ru_RU'}">
    <fmt:setLocale value="ru_RU"/>
</c:if>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.feedbacks"/></title>
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
        <c:if test="${successAddFeedbackMessage != null}">
            <div class="alert alert-success">
                <c:out value="${successAddFeedbackMessage}"/>
            </div>
        </c:if>
    </div>

    <div class="row">
        <h2><fmt:message key="title.feedbacks"/></h2>

        <div class="panel-group">
            <c:forEach items="${feedbacks}" var="feedback">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <label><fmt:message key="label.author"/></label>
                            <%--<c:out value="${}"/><br>--%>
                        <label><fmt:message key="label.mark"/></label>
                        <c:out value="${feedback.mark}"/><br>
                    </div>
                    <div class="panel-body">
                        <c:out value="${feedback.comment}"/>
                    </div>
                    <div class="panel-footer">
                        <label><fmt:message key="label.date"/></label>
                        <ctg:dateTimeFmt dateTime="${feedback.dateTime}" pattern="HH:mm:ss dd-MM-yyyy"/>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="row">
        <div class="btn-group">
            <c:forEach begin="1" end="${pagesQuantity}" varStatus="loop">
                <form name="feedbacksPagesForm" method="POST" action="./show_feedbacks">
                    <input type="hidden" name="page" value="${loop.count}">
                    <input class="btn btn-default" type="submit" value="${loop.count}">
                </form>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
