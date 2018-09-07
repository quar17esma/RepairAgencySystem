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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <br>
        <h2><fmt:message key="title.registration.form"/></h2>
    </div>
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-horizontal" name="registrationForm" method="POST" action="./add_user">
                    <div class="form-group">
                        <label class="control-label col-md-1" for="phone">
                            <fmt:message key="label.phone"/>
                        </label>
                        <div class="col-md-2">
                            <fmt:message var="placeholderPhone" key="placeholder.enter.phone"/>
                            <input class="form-control" type="text" name="phone" value="${phone}" required="required"
                                   placeholder="${placeholderPhone}"
                                   id="phone"/>
                        </div>
                        <div class="col-md-2">
                            <div class="text-danger">
                                <c:out value="${wrongPhoneMessage}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-1" for="email">
                            <fmt:message key="label.email"/>
                        </label>
                        <div class="col-md-2">
                            <fmt:message var="placeholderEmail" key="placeholder.enter.email"/>
                            <input class="form-control" type="email" name="email" value="${email}" required="required"
                                   placeholder="${placeholderEmail}"
                                   id="email"/>
                        </div>
                        <div class="col-md-2">
                            <div class="text-danger">
                                <c:out value="${errorBusyEmailMessage}"/>
                                <c:out value="${wrongEmailMessage}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-1" for="pwd">
                            <fmt:message key="label.password"/>
                        </label>
                        <div class="col-md-2">
                            <fmt:message var="placeholderPassword" key="placeholder.enter.password"/>
                            <input class="form-control" type="password" name="password" value="" required="required"
                                   placeholder="${placeholderPassword}"
                                   id="pwd"/>
                        </div>
                        <div class="col-md-2">
                            <div class="text-danger">
                                <c:out value="${wrongPasswordMessage}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-1" for="name">
                            <fmt:message key="label.name"/>
                        </label>
                        <div class="col-md-2">
                            <fmt:message var="placeholderName" key="placeholder.enter.name"/>
                            <input class="form-control" type="text" name="name" value="${name}" required="required"
                                   placeholder="${placeholderName}"
                                   id="name"/>
                        </div>
                        <div class="col-md-2">
                            <div class="text-danger">
                                <c:out value="${wrongNameMessage}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-1" for="surname">
                            <fmt:message key="label.surname"/>
                        </label>
                        <div class="col-md-2">
                            <fmt:message var="placeholderSurname" key="placeholder.enter.surname"/>
                            <input class="form-control" type="text" name="surname" value="${surname}" required="required"
                                   placeholder="${placeholderSurname}"
                                   id="surname"/>
                        </div>
                        <div class="col-md-2">
                            <div class="text-danger">
                                <c:out value="${wrongSurnameMessage}"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-1" for="birthDate">
                            <fmt:message key="label.birth.date"/>
                        </label>
                        <div class="col-md-2">
                            <input class="form-control" type="date" name="birthDate" value="${birthDate}"
                                   required="required"
                                   id="birthDate"/>
                        </div>
                        <div class="col-md-2">
                            <div class="text-danger">
                                <c:out value="${wrongBirthDateMessage}"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-2 offset-md-1">
                            <fmt:message var="buttonRegister" key="button.register"/>
                            <input class="btn btn-default" type="submit" value="${buttonRegister}"/>
                            <fmt:message var="buttonReset" key="button.reset"/>
                            <input class="btn btn-default" type="reset" value="${buttonReset}">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
