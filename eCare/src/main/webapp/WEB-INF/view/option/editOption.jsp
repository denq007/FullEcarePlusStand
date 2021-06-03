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
    <jsp:include page="../header.jsp" />
</head>
<body class="bg-light">
<sec:authorize access="hasRole('EMPLOYEE')">
    <span class="pull-right"><a href="/employee/employeecabinet" class="btn btn-info" role="button">Back</a></span>
</sec:authorize>
<sec:authorize access="!hasRole('EMPLOYEE')">
    <span class="pull-right"><a href="/customer/showcustomerinformation" class="btn btn-info" role="button">Back</a></span>
</sec:authorize>
<div class="container">
    <main>
        <div class="py-5 text-center">
            <c:if test="${not empty message}">
                <div id="error">${message}</div>
            </c:if>
            <h2>Create new option</h2>
        </div>

        <form:form  modelAttribute="option" method="post" >
        <div class="row g-5">

            <div class="col-md-7 col-lg-8">
                <form class="needs-validation" novalidate>
                    <div class="row g-3">
                        <div class="col-sm-6">
                            <label for="optionName" class="form-label">Name</label>
                            <%--<form:input path="optionName" value="${option.optionName}" class="form-control" required=""/>--%>
                                <input type="text" name="optionName"  class="form-control" id="optionName" placeholder="" value="${option.optionName}" required>
                            <div class="invalid-feedback">
                                Valid first name is required.
                            </div>
                        </div>
                        <div class="col-12">
                            <label for="optionGroupNumber" class="form-label">Group number (0 - The option is compatible with all, 1 - Internet,  2 -Call)</label>
                                <%-- <form:input path="optionGroupNumber" value="${option.optionGroupNumber}" class="form-control" id="optionGroupNumber" required=""/>--%>
                            <input type="text" name="optionGroupNumber" class="form-control" id="optionGroupNumber" value="${option.optionGroupNumber}" placeholder="1 or 2 or 3" required>
                            <div class="invalid-feedback">
                                Please enter your shipping address.
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <label for="Connection cost" class="form-label">Connection cost</label>
                           <%-- <form:input path="optionConnectionCost" value="${option.optionConnectionCost}" class="form-control" id="Connection cost" required=""/>--%>
                       <input type="text" name="optionConnectionCost" class="form-control" id="Connection cost" placeholder="" value="${option.optionConnectionCost}" required>
                            <div class="invalid-feedback">
                                Valid last name is required.
                            </div>
                        </div>

                        <div class="col-12">
                            <label for="optionPrice" class="form-label">Price <span class="text-muted"></span></label>
                            <%--<form:input path="optionPrice" value="${option.optionPrice}" class="form-control" id="optionPrice" required=""/>--%>
                            <input type="text" name="optionPrice"  class="form-control" id="optionPrice" value="${option.optionPrice}" placeholder="">
                        </div>

                        <form:hidden path="optionId" value="${option.optionId}"/>

                        <hr class="my-4">
                        <button class="w-100 btn btn-primary btn-lg" type="submit" >Save</button>
                    </div>
                    </form:form>
    </main>

    <footer class="my-5 pt-5 text-muted text-center text-small">
        <p class="mb-1">&copy; 2021–2021</p>
        <ul class="list-inline">
            <%--            <li class="list-inline-item"><a href="#">Privacy</a></li>
                        <li class="list-inline-item"><a href="#">Terms</a></li>
                        <li class="list-inline-item"><a href="#">Support</a></li>--%>
        </ul>
    </footer>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>

<script src="form-validation.js"></script>
</body>
</html>
