<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<span class="pull-right"><a href="/" class="btn btn-info" role="button">Back</a></span>
<h2>All Tarifs</h2>
<br>
<c:if test="${not empty message}">
    <div id="error">${message}</div>
</c:if>
<table class="table table-striped">
    <tr>
        <th>Id</th>
        <th>Tariff name</th>
        <th>Price</th>
        <th>Enabled</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="allTarifs" items="${allTarifs}">
    <tr>
        <td>${allTarifs.id}</td>
        <td>${allTarifs.name}</td>
        <td>${allTarifs.price}</td>
        <td>${allTarifs.old}</td>
   <%--     <td>
            <c:forEach var="option" items="${allTarifs.optionName}">
        <td>${option}</td>
        </c:forEach>
        </td>--%>
        <td>
            <form action="/employee/delete-tariff" method="get">
                <input hidden name="name" value=${allTarifs.name}>
                <button class="w-100 btn btn-primary btn-lg" type="submit">Delete</button>
            </form>
        </td>
        <td>
            <form action="/employee/edit-tariff" method="get">
                <input hidden name="id" value=${allTarifs.id}>
                <button class="w-100 btn btn-primary btn-lg" type="submit">Edit tariff</button>
            </form>
        </td>
        </c:forEach>
    </tr>

</table>

</body>

</html>
