<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: dennn
  Date: 05.05.2021
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.82.0">
    <title>Checkout example · Bootstrap v5.0</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/checkout/">

    <!-- Bootstrap core CSS -->
    <link href="../assets/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>


    <!-- Custom styles for this template -->
    <link href="form-validation.css" rel="stylesheet">
    <jsp:include page="header.jsp" />
</head>
<body class="bg-light">
<c:if test="${not empty message}">
    <div id="error">${message}</div>
</c:if>
<div class="container">
    <sec:authorize access="!hasRole('EMPLOYEE')">
        <span class="pull-right"><a href="/customer/showcustomerinformation" class="btn btn-info" role="button">Back</a></span>
    </sec:authorize>
    <h3>Contract details</h3>
    <table class="table table-striped">
        <thead>
        <th style="width:20%"></th>
        <th style="width:80%"></th>
        </thead>
        <tbody>
        <tr>
            <td hidden>Id:</td>
            <td hidden>${basket.tariffId} </td>
        </tr>
        <tr>
            <td>Tariff name</td>
            <td>${basket.tariffName} </td>
        </tr>
        <tr>
            <td>Extra option's name:</td>
            <c:forEach var="option" items="${basket.addNameOptions}">
        <tr>
            <td>${option}</td>
        </tr>
            </c:forEach>
        </tr>

        </tbody>
    </table>
    <form action="/contract/savecontract" method="post">
        <input type="hidden" name="id" value=${basket.id}>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button id="button" class="w-100 btn btn-primary btn-lg" type="submit">Save</button>
    </form>
</div>
</body>
</html>