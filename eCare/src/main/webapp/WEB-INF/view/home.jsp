<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.82.0">
    <title>Pricing example · Bootstrap v5.0</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/pricing/">

    <!-- Bootstrap core CSS -->
    <%-- <link href="<c:url value="../static/css/bootstrap.min.css"/>" rel="stylesheet">--%>

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
    <link href="<c:url value="../static/css/pricing.css"/>" rel="stylesheet">
</head>
<body>

<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="check" viewBox="0 0 16 16">
        <title>Check</title>
        <path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
    </symbol>
</svg>

<div class="container py-3">
    <header class="d-flex flex-column flex-md-row align-items-center pb-3 mb-4 border-bottom">
        <a href="/" class="d-flex align-items-center text-dark text-decoration-none">
            <img src="/resources/image/img.png" width="40" height="32" class="me-2" viewBox="0 0 118 94" role="img">


            <span class="fs-4">EcareTelecom</span>
        </a>

        <nav class="d-inline-flex mt-2 mt-md-0 ms-md-auto">
            <%--   <c:if test="${pageContext.request.isUserInRole('Customer')}">
                   <p>This will be displayed only if the user has the role "admin".</p>
               </c:if>--%>
            <%--  <%if(roles.equals("admin")){%>
              <jsp:include page="../menu/admin_menu.jsp" />
              <%}%>
              <%if(userRole.equals("user")){%>
              <jsp:include page="../menu/user_menu.jsp" />
              <%}%>--%>

            <sec:authorize access="!hasRole('EMPLOYEE')">
            <a class="me-3 py-2 text-dark text-decoration-none" href="/customer/showcustomerinformation">Cabinet</a>
            </sec:authorize>
            <sec:authorize access="hasRole('EMPLOYEE')">
                <a class="me-3 py-2 text-dark text-decoration-none" href="/employee/employeecabinet">Cabinet</a>
            </sec:authorize>
            <a class="me-3 py-2 text-dark text-decoration-none" href="/login">Sign-in</a>
            <jsp:include page="header.jsp"/>
            <%--  <a class="me-3 py-2 text-dark text-decoration-none" href="">Support</a>
              <a class="py-2 text-dark text-decoration-none" href="#">Pricing</a>--%>
        </nav>
    </header>

    <div class="pricing-header p-3 pb-md-4 mx-auto text-center">
        <h1 class="display-4 fw-normal">Welcome</h1>
        <p class="fs-5 text-muted">What will you choose?</p>
    </div>

    <main>
        <div class="row row-cols-1 row-cols-md-3 mb-3 text-center">
            <div class="col">
                <div class="card mb-4 rounded-3 shadow-sm">
                    <div class="card-header py-3">
                        <h4 class="my-0 fw-normal">HotRed</h4>
                    </div>
                    <div class="card-body">
                        <h1 class="card-title pricing-card-title">$12,56<small class="text-muted fw-light">/month</small></h1>
                        <ul class="list-unstyled mt-3 mb-4">
                            <li>200 GB</li>
                            <li>500 minutes</li>
                            <li>3G, 4G, 5G, AON FREE</li>
                        </ul>
                        <span class="pull-right"><a href="show-tariff?name=HotRed" class="w-100 btn btn-lg btn-primary" role="button"> Show </a></span>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card mb-4 rounded-3 shadow-sm">
                    <div class="card-header py-3">
                        <h4 class="my-0 fw-normal">HotBlack</h4>
                    </div>
                    <div class="card-body">
                        <h1 class="card-title pricing-card-title">$5.5<small class="text-muted fw-light">/month</small></h1>
                        <ul class="list-unstyled mt-3 mb-4">
                            <li>100 GB</li>
                            <li>3G, 4G, 5G Free</li>
                        </ul>
                        <span class="pull-right"><a href="show-tariff?name=HotBlack" class="w-100 btn btn-lg btn-primary" role="button"> Show </a></span>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card mb-4 rounded-3 shadow-sm border-primary">
                    <div class="card-header py-3 text-white bg-primary border-primary">
                        <h4 class="my-0 fw-normal">HotBlue</h4>
                    </div>
                    <div class="card-body">
                        <h1 class="card-title pricing-card-title">$0<small class="text-muted fw-light">/month</small></h1>
                        <ul class="list-unstyled mt-3 mb-4">
                            <li>Choose only what you need</li>
                        </ul>
                        <span class="pull-right"><a href="show-tariff?name=HotBlue" class="w-100 btn btn-lg btn-primary" role="button"> Show </a></span>
                    </div>
                </div>
            </div>
        </div>


    </main>

    <footer class="pt-4 my-md-5 pt-md-5 border-top">
        <div class="row">
            <div class="col-12 col-md">
                <small class="d-block mb-3 text-muted">&copy; 2021</small>
            </div>
            </div>

    </footer>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>
</body>
</html>
