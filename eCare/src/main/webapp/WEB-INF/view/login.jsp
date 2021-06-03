
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.82.0">
    <title>Signin Template · Bootstrap v5.0</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sign-in/">



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
    <link href="signin.css" rel="stylesheet">
</head>
<body class="text-center">
<%--<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="hasRole('CUSTOMER')">
                    <li><a href="customer/showcontract">CUSTOMER</a></li>
                </sec:authorize>
  &lt;%&ndash;              <sec:authorize access="hasRole('CLIENT')">
                    <li><a href="user/cabinet">Cabinet</a></li>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                    <li><a href="login">SIGN IN<span class="glyphicon glyphicon-log-in"></span></a></li>
                </sec:authorize>

                <sec:authorize access="isAnonymous()">
                    <li><a href="register">SIGN UP<span class="glyphicon glyphicon-user"></span></a></li>
                </sec:authorize>&ndash;%&gt;
              &lt;%&ndash;  <sec:authorize access="isAuthenticated()">
                    <li>
                        <a href="javascript:document.getElementById('logout').submit()">LOG OUT <span
                                class="glyphicon glyphicon-log-out"></span></a>
                        <form id="logout" action="logout" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </li>
                </sec:authorize>&ndash;%&gt;
                <sec:authorize access="isAuthenticated()">
                    <% response.sendRedirect("/"); %>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>--%>



<main class="form-signin" style="width:100%; max-width: 330px; padding: 15px; margin: auto">
    <c:if test="${not empty message}">
        <div id="error">${message}</div>
    </c:if>
    <form role="form" action="<c:url value='/login' />" method='POST'>
        <fieldset>
        <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
        <div class="form-floating">
            <input id="login" class="form-control" placeholder="9062107057" name="username"
                   type="text"
                   autofocus>
            <label for="login">Email address</label>
        </div>
        <div class="form-floating">
            <input id="password" class="form-control" placeholder="password" name="password"
                   type="password"
                   value="">
            <label for="password">Password</label>
        </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="w-100 btn btn-lg btn-primary" name="submit" type="submit">Sign in</button>
        </fieldset>
    </form>
    <sec:authorize access="hasRole('EMPLOYEE')">
    <form action="/user/registration" method="get">
        <button class="w-100 btn btn-lg btn-primary" type="submit">Registration</button>
        <p class="mt-5 mb-3 text-muted">&copy; 2021–2021</p>
    </form>
    </sec:authorize>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

</body>
</html>