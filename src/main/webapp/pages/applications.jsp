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
    <style>
        .button {
            float: left;
        }
    </style>
    <title><fmt:message key="title.applications"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <jsp:include page="/pages/header.jsp"/>
    <div>
        <c:out value="${errorCompleteApplicationMessage}"/>
    </div>
    <%--<div>--%>
        <%--<form name="searchForm" method="POST" action="./search_food">--%>
            <%--<label><fmt:message key="label.search.food"/></label>--%>
            <%--<input type="search" name="searchString" value="${searchString}">--%>
            <%--<fmt:message var="buttonSearch" key="button.search"/>--%>
            <%--<input type="submit" value="${buttonSearch}">--%>
        <%--</form>--%>
        <%--<br>--%>
    <%--</div>--%>
    <%--<div>--%>
        <%--<c:out value="${sorryFoodNotFoundMessage}"/>--%>
    <%--</div>--%>
    <div>
        <h2><fmt:message key="title.applications"/></h2>

        <table>
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.status"/></th>
                <th><fmt:message key="label.product"/></th>
                <th><fmt:message key="label.repair.type"/></th>
                <th><fmt:message key="label.date"/></th>
                <th><fmt:message key="label.price"/></th>
                <th><fmt:message key="label.actions"/></th>
            </tr>
            <c:forEach items="${applications}" var="application">
            <tr>
                <td><c:out value="${application.id}"/></td>
                <td><c:out value="${application.status}"/></td>
                <td><c:out value="${application.product}"/></td>
                <td><c:out value="${application.repairType}"/></td>
                <td><c:out value="${application.createDate}"/></td>
                <td>
                <c:if test="${application.price > 0}">
                    <ctg:price price="${application.price}"/>
                </c:if>
                </td>
                <td>
                    <c:if test="${sessionScope.user.role == 'MANAGER' && application.status == 'NEW'}">
                        <form class="button" name="acceptApplicationForm" method="POST" action="./accept_application">
                            <input type="hidden" name="applicationId" value="${application.id}">
                            <label><fmt:message key="label.price"/></label>
                            <input type="number" name="price" min="1" max="100000000" step="1" value="${application.price}" required="required"/>
                            <fmt:message var="buttonAccept" key="button.accept"/>
                            <input type="submit" value="${buttonAccept}">
                        </form>
                        <br>
                        <form class="button" name="declineApplicationForm" method="POST" action="./decline_application">
                            <input type="hidden" name="applicationId" value="${application.id}">
                            <label><fmt:message key="label.decline.reason"/></label>
                            <input type="textarea" rows="4" cols="50"  name="declineReason" value="${application.declineReason}" required="required">
                            <fmt:message var="buttonDecline" key="button.decline"/>
                            <input type="submit" value="${buttonDecline}">
                        </form>
                    </c:if>
                    <c:if test="${sessionScope.user.role == 'REPAIRER' && application.status == 'ACCEPTED'}">
                        <form class="button" name="completeApplicationForm" method="POST" action="./complete_application">
                            <input type="hidden" name="applicationId" value="${application.id}">
                            <fmt:message var="buttonComplete" key="button.complete"/>
                            <input type="submit" value="${buttonComplete}">
                        </form>
                    </c:if>
                    <c:if test="${sessionScope.user.role == 'USER' && application.status == 'COMPLETED' && application.feedback == null}">
                        <form class="button" name="addFeedbackForm" method="POST" action="./add_feedback">
                            <input type="hidden" name="applicationId" value="${application.id}">
                            <label><fmt:message key="label.feedback.mark"/></label>
                            <input type="number" value="1" name="mark" min="1" max="5" step="1" required="required">
                            <br>
                            <label><fmt:message key="label.feedback.comment"/></label>
                            <input type="textarea" rows="4" cols="50"  name="comment" value="${feedback.comment}" required="required">
                            <fmt:message var="buttonComplete" key="button.complete"/>
                            <input type="submit" value="${buttonComplete}">
                        </form>
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </table>
        <br/>
    </div>
    <div>
        <c:forEach begin="1" end="${pagesQuantity}" varStatus="loop">
            <form class="button" name="applicationPagesForm" method="POST" action="./show_applications">
                <input type="hidden" name="page" value="${loop.count}">
                <input type="submit" value="${loop.count}">
            </form>
        </c:forEach>
    </div>
</body>
</html>
