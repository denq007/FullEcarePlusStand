<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
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
</head>
<body class="bg-light">
<sec:authorize access="hasRole('EMPLOYEE')">
<span class="pull-right"><a href="/employee/employeecabinet" class="btn btn-info" role="button">Back</a></span>
</sec:authorize>
<sec:authorize access="!hasRole('EMPLOYEE')">
    <span class="pull-right"><a href="/customer/showcustomerinformation" class="btn btn-info" role="button">Back</a></span>
</sec:authorize>
<h2>All Options</h2>
<br>

<table class="table table-striped">
    <tr>
        <th hidden>Id</th>
        <th>Name</th>
        <th>Connection cost</th>
        <th>Price</th>
<sec:authorize access="hasRole('EMPLOYEE')">
        <th>Group number</th>
</sec:authorize>


    </tr>
    <%--  <jsp:useBean id="allCustomer" scope="request" type="java.util.List"/>--%>
    <c:forEach var="option" items="${allOptions}">
        <tr>
            <td hidden>${option.optionId}</td>
            <td>${option.optionName}</td>
            <td>${option.optionConnectionCost}</td>
            <td>${option.optionPrice}</td>
            <td>${option.optionGroupNumber}</td>
            <td>
            <sec:authorize access="hasRole('EMPLOYEE')">
                <form action="employee/editoption" method="get">
                    <input type="hidden" name="optionId" value=${option.optionId}>
                    <input type="submit" value="Edit option" class="btn btn-warning"></form>
            </sec:authorize>
            </td>
        </tr>
    </c:forEach>
    <sec:authorize access="hasRole('EMPLOYEE')">
        <form action="/employee/create-option" method="get">
            <input type="submit" value="Create option" class="btn btn-warning"></form>
    </sec:authorize>
</table>

</body>

</html>
