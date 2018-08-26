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
</head>
<body>
    <jsp:include page="/pages/header.jsp"/>
    <%--<div>--%>
        <%--<c:out value="${successAddFoodMessage}"/>--%>
        <%--<c:out value="${successDeleteFoodMessage}"/>--%>
    <%--</div>--%>
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
                    <c:if test="${application.status == 'NEW'}">
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
                            <fmt:message var="buttonDecline" key="button.decline"/>
                            <input type="submit" value="${buttonDecline}">
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
