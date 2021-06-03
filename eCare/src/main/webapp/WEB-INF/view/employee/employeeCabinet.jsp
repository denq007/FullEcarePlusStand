<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
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
    <jsp:include page="../header.jsp"/>
</head>
<body class="bg-light">

<div class="container">
    <main>
        <c:if test="${not empty message}">
            <div id="error">${message}</div>
        </c:if>
        <div class="py-5 text-center">

            <h2>Personal Information</h2>
        </div>
        <form action="/user/registration" method="get">
            <button class="w-100 btn btn-primary btn-lg" type="submit"> Create user and customer</button>
        </form>

        <form class="needs-validation" action="/customer/showcustomer" method="get">
            <input type="text" name="id" id="lastName"
                   placeholder=""  value="${customer.id}"  required>
            <button class="w-100 btn btn-primary btn-lg" type="submit">Show customer by id</button>
        </form>




        <form action="/employee/findClientByPhoneNumber" method="get">
            <input type="text" name="number"  placeholder=""  value="${contract.number}" pattern="[9][2][1][0-9][0-9][0-9][0-9][0-9][0-9][0-9]"required >
            <button class="w-100 btn btn-primary btn-lg" type="submit">Show customer by phone number</button>
        </form>

        <form action="/employee/showallcustomer" method="get">
            <button class="w-100 btn btn-primary btn-lg" type="submit">Show all customers</button>
        </form>
        <form action="/contract/showallcontracts" method="get">
        <button class="w-100 btn btn-primary btn-lg" type="submit" >Show all contracts</button>
        </form>
        <form action="/employee/create-tariff" method="get">
            <button class="w-100 btn btn-primary btn-lg" type="submit" >Create new tariff</button>
        </form>
        <form action="/show-tariff" method="get">
            <input name="name" placeholder=""  value="${tariff.name}" required>
            <button class="w-100 btn btn-primary btn-lg" type="submit">Show Tariff</button>
        </form>
        <form action="/show-all-tariffs" method="get">
            <button class="w-100 btn btn-primary btn-lg" type="submit">Show all tariffs</button>
        </form>
        <form action="/show-all-options" method="get">
            <button class="w-100 btn btn-primary btn-lg" type="submit">Show all options</button>
        </form>

          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


</main>
</div>
<footer class="my-5 pt-5 text-muted text-center text-small">
    <p class="mb-1">&copy; 2021–2021</p>
    <ul class="list-inline">
        <%--            <li class="list-inline-item"><a href="#">Privacy</a></li>
                    <li class="list-inline-item"><a href="#">Terms</a></li>
                    <li class="list-inline-item"><a href="#">Support</a></li>--%>
    </ul>
</footer>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>

<script src="form-validation.js"></script>
</body>
</html>
