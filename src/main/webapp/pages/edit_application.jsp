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
        <c:if test="${errorAddApplication != null}">
            <div class="alert alert-danger">
                <c:out value="${errorAddApplication}"/>
            </div>
        </c:if>
    </div>
    <div class="row">
        <h2><fmt:message key="title.add.application"/></h2>
        <form class="form-horizontal" name="addApplicationForm" method="POST" action="./add_application">
            <c:if test="${application != null}">
                <input type="hidden" name="applicationId" value="${application.id}">
            </c:if>
            <div class="form-group">
                <label class="control-label col-md-2" for="product"><fmt:message key="label.product"/></label>
                <div class="col-md-2">
                    <input id="product" class="form-control" type="text" name="product" value="${application.product}"
                           required="required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-2" for="repairType"><fmt:message key="label.repair.type"/></label>
                <div class="col-md-2">
                    <input id="repairType" class="form-control" type="textarea" rows="4" cols="50"
                           name="repairType" value="${application.repairType}" required="required">
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-2 offset-md-2">
                    <fmt:message var="buttonAddApplication" key="button.add.application"/>
                    <input class="btn btn-default" type="submit" value="${buttonAddApplication}"/>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>
