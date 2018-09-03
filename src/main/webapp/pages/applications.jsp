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
    <title><fmt:message key="title.applications"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<div class="container">
    <div class="row">
        <jsp:include page="/pages/header.jsp"/>
    </div>
    <div class="row">
        <c:if test="${errorCompleteApplicationMessage != null}">
            <div class="alert alert-danger">
                <c:out value="${errorCompleteApplicationMessage}"/>
            </div>
        </c:if>
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
    <div class="row">
        <h2><fmt:message key="title.applications"/></h2>

        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="label.id"/></th>
                <th><fmt:message key="label.status"/></th>
                <th><fmt:message key="label.product"/></th>
                <th><fmt:message key="label.repair.type"/></th>
                <th><fmt:message key="label.date"/></th>
                <th><fmt:message key="label.price"/></th>
                <th><fmt:message key="label.actions"/></th>
            </tr>
            </thead>
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
                            <a href="#acceptApplication${application.id}" data-toggle="collapse">
                                <fmt:message key="button.accept"/>
                            </a>
                            <div id="acceptApplication${application.id}" class="collapse">
                                <form class="form-inline" name="acceptApplicationForm" method="POST"
                                      action="./accept_application">
                                    <div class="form-group">
                                        <input type="hidden" name="applicationId" value="${application.id}">
                                        <label for="price${application.id}">
                                            <fmt:message key="label.price"/>
                                        </label>
                                        <input id="price${application.id}" class="form-control" type="number"
                                               name="price" min="1" max="100000000" step="1"
                                               value="${application.price}" required="required"/>
                                        <fmt:message var="buttonAccept" key="button.accept"/>
                                        <input class="btn btn-default" type="submit" value="${buttonAccept}">
                                    </div>
                                </form>
                            </div>
                            <a href="#declineApplication${application.id}" data-toggle="collapse">
                                <fmt:message key="button.decline"/>
                            </a>
                            <div id="declineApplication${application.id}" class="collapse">
                                <form class="form-horizontal" name="declineApplicationForm"
                                      method="POST" action="./decline_application">
                                        <input type="hidden" name="applicationId" value="${application.id}">
                                    <div class="form-group">
                                        <label for="declineReason${application.id}">
                                            <fmt:message key="label.decline.reason"/>
                                        </label>
                                        <textarea id="declineReason${application.id}" class="form-control" rows="4"
                                                  name="declineReason"
                                                  value="${application.declineReason}" required="required">
                                        </textarea>
                                    </div>
                                    <div class="form-group">
                                        <fmt:message var="buttonDecline" key="button.decline"/>
                                        <input class="btn btn-default" type="submit" value="${buttonDecline}">
                                    </div>
                                </form>
                            </div>
                        </c:if>
                        <c:if test="${sessionScope.user.role == 'REPAIRER' && application.status == 'ACCEPTED'}">
                            <form class="form-inline"
                                  name="completeApplicationForm" method="POST" action="./complete_application">
                                <input type="hidden" name="applicationId" value="${application.id}">
                                <fmt:message var="buttonComplete" key="button.complete"/>
                                <input class="btn btn-default" type="submit" value="${buttonComplete}">
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.user.role == 'USER' && application.status == 'COMPLETED' && application.feedback == null}">
                            <a href="#addFeedback${application.id}" data-toggle="collapse">
                                <fmt:message key="button.leave.feedback"/>
                            </a>
                            <div id="addFeedback${application.id}" class="collapse">
                                <form class="form-horizontal" name="addFeedbackForm" method="POST"
                                      action="./add_feedback">
                                    <input type="hidden" name="applicationId" value="${application.id}"/>
                                    <label for="mark${application.id}">
                                        <fmt:message key="label.feedback.mark"/>
                                    </label>
                                    <input id="mark${application.id}" class="form-control" type="number" value="1"
                                           name="mark" min="1" max="5" step="1"
                                           required="required"/>
                                    <label for="comment${application.id}"/>
                                    <fmt:message key="label.feedback.comment"/>
                                    </label>
                                    <textarea id="comment${application.id}" class="form-control"
                                              rows="4" name="comment" value="${feedback.comment}"
                                              required="required">
                                    </textarea>
                                    <fmt:message var="buttonLeaveFeedback" key="button.leave.feedback"/>
                                    <input class="btn btn-default" type="submit" value="${buttonLeaveFeedback}"/>
                                </form>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br/>
    </div>

    <div class="row">
        <ul class="pagination">
            <c:forEach begin="1" end="${pagesQuantity}" varStatus="loop">
                <li>
                    <a href="?page=${loop.count}">${loop.count}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>
