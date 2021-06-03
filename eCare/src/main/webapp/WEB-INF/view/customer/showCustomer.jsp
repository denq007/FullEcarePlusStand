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
<sec:authorize access="hasRole('EMPLOYEE')">
    <span class="pull-right"><a href="/employee/employeecabinet" class="btn btn-info" role="button">Back</a></span>
</sec:authorize>
<sec:authorize access="!hasRole('EMPLOYEE')">
    <span class="pull-right"><a href="/customer/showcustomerinformation" class="btn btn-info" role="button">Back</a></span>
</sec:authorize>
<div class="container">
    <span class="pull-right"><a href="/" class="btn btn-info" role="button">Back</a></span>
    <h3>Client details</h3>
    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>
    <table class="table table-striped">
        <thead>
        <th style="width:20%"></th>
        <th style="width:80%"></th>
        </thead>
        <tbody>
        <tr>
            <sec:authorize access="hasRole('EMPLOYEE')">
            <td>Id:</td>
            <td>${customer.id} </td>
            </sec:authorize>
        </tr>
        <tr>
            <td>Name:</td>
            <td>${customer.name} </td>
        </tr>
        <tr>
            <td>Surname:</td>
            <td>${customer.surname} </td>
        </tr>
        <tr>
            <td>Birthday:</td>
            <td>${customer.birthDate} </td>
        </tr>

        <tr>
            <td>Address:</td>
            <td>${customer.address} </td>
        </tr>
        <tr>
            <td>Passport:</td>
            <td>${customer.passportDetails} </td>
        </tr>
        </tbody>
    </table>
   <%-- <sec:authorize access="hasRole('EMPLOYEE')">
    <form action="/employee/editcustomer" method="get">
        </sec:authorize>
    <sec:authorize access="hasRole('CUSTOMER')">--%>
    <form action="/customer/editcustomer" method="get"><%--</sec:authorize>--%>

        <input type="hidden" name="customerID" value=${customer.id}>
        <%--  <input type="hidden" name="userDTO.userId" value=${customer.userDTO.userId}>--%>
        <input type="submit" value="Edit info" class="btn btn-warning">
            </form>
    <h3>Client contracts</h3>
    <td></td>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Phone number</th>
            <th>Name of the tariff</th>
            <th>Blocked Number</th>
            <th>Blocked Number by Admin</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${customer.contractIdList}" var="contract">
            <tr>
                <td>${contract.number} </td>
                <td>${contract.tariffId.name}</td>
                <td>${contract.blockedByUser}</td>
                <td>${contract.blockedByAdmin}</td>
                <td>
                    <c:if test="${contract.blockedByUser==false && contract.blockedByAdmin==false}">
                    <form action="/contract/editcontract" method="get">
                        <input type="hidden" name="id" value=${contract.id}>
                        <input type="submit" value="Edit" class="btn btn-warning"></form>
                    </c:if>
                </td>
                <c:if test="${contract.blockedByUser && !contract.blockedByAdmin}">
                    <sec:authorize access="!hasRole('EMPLOYEE')">
                <form action="/customer/user/unblock" method="get">
                    <input type="hidden" name="id" value=${contract.id}>
                    <input type="submit" value="Unblock number" class="btn btn-warning"></form>
                    </sec:authorize>
                </c:if>
                <td>
                    <form action="/contract/showcontract" method="get">
                        <input type="hidden" name="id" value=${contract.id}>
                        <input type="submit" value="Show details" class="btn btn-info"></form>
                </td>
                <c:if test="${!contract.blockedByUser && !contract.blockedByAdmin}">
                    <sec:authorize access="!hasRole('EMPLOYEE')">
                <td>
                    <form action="/customer/user/block" method="get">
                        <input type="hidden" name="id" value=${contract.id}>
                        <input type="submit" value="Block number" class="btn btn-info"></form>
                </td>
                    </sec:authorize>
                    <sec:authorize access="hasRole('EMPLOYEE')">
                        <td>
                            <form action="/employee/user/block" method="get">
                                <input type="hidden" name="id" value=${contract.id}>
                                <input type="submit" value="Block number by admin" class="btn btn-info"></form>
                        </td>
                    </sec:authorize>
                </c:if>
                <c:if test="${contract.blockedByAdmin}">
                    <sec:authorize access="hasRole('EMPLOYEE')">
                    <form action="/employee/user/unblock" method="get">
                        <input type="hidden" name="id" value=${contract.id}>
                        <input type="submit" value="Unblock number" class="btn btn-warning"></form>
                    </sec:authorize>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <sec:authorize access="hasRole('EMPLOYEE')">
    <form action="/contract/createcontract" method="get">
            <input type="hidden" name="customerID" value=${customer.id}>
            <input type="submit" value="Create contract" class="btn btn-warning"></form>
        <br>
    </sec:authorize>
</div>
</body>
</html>