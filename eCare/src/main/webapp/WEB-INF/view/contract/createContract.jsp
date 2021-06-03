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
<header>


</header>
<head>
   <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.82.0">
    <title>Checkout example · Bootstrap v5.0</title>
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/checkout/">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>

    <script src="<c:url value="/resources/js/jquery.bootstrap-duallistbox.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-duallistbox.css"/>">

    <%--   <script src="/WEB-INF/static/js/jquery.bootstrap-duallistbox.js"></script>
    <link rel="stylesheet" type="text/css" href="/WEB-INF/static/css/bootstrap-duallistbox.css"/>--%>

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

<div class="container">
    <main>
        <div class="py-5 text-center">

            <h2>Your Contract</h2>
        </div>
       <%-- <c:if test="${not empty message}"><p class="bg-danger">${message}</p></c:if>--%>
        <c:if test="${not empty message}">
            <div id="error">${message}</div>
        </c:if>
        <form:form  modelAttribute="contract" method="post" >
        <div class="row g-5">

            <div class="col-md-7 col-lg-8">
                <h4 class="mb-3">Contract:</h4>
                <form class="needs-validation" novalidate>
                    <div class="row g-3">

                        <div class="col-sm-6">
                            <label for="lastName" class="form-label">Id Custumer</label>
                            <form:input path="customerId" value="${contract.customerId}" class="form-control" id="lastName" readonly="true"/>
                         <%--     <input type="text" name="customerId" class="form-control" id="lastName" placeholder="" value="${contract.customerId}" readonly>  --%>
                        </div>
                        <sec:authorize access="hasRole('EMPLOYEE')">
                        <div class="col-12">
                            <label for="address" class="form-label">Blocked by Admin</label>
                            <form:input path="blockedByAdmin" value="${contract.blockedByAdmin}" class="form-control" id="address" required="" />
                                <%-- <input type="text" class="form-control" id="address" placeholder="1234 Main St" required>--%>
                            <div class="invalid-feedback">
                                Please enter your shipping address.
                            </div>
                        </div>
                        </sec:authorize>
                        <div class="col-12">
                            <label for="passport" class="form-label">User blocked <span class="text-muted"></span></label>
                            <form:input path="blockedByUser" value="${contract.blockedByUser}" class="form-control" id="passport" required=""/>
                                <%-- <input type="text" class="form-control" id="address2" placeholder="Apartment or suite">--%>
                        </div>

                        <div class="col-12">
                            <label for="numbor" class="form-label">Phone Number <span class="text-muted"></span></label>
                            <form:input path="number" value="${contract.number}" class="form-control" id="numbor" readonly="true" />
                                <%-- <input type="text" class="form-control" id="address2" placeholder="Apartment or suite">--%>
                        </div>

                        <div class="form-group">
                            <label for="tr" class="form-label" >Tariffs <span class="text-muted"></span></label>
                                <form:select path="tariffId" id="tr" class="selectpicker"
                                             data-live-search="true" data-size="5" data-actions-box="true" data-width="75%">
                                    <c:forEach items="${contract.allTariffs}" var="item">
                                        <form:option label="${item.key}" value="${item.value}"/>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>




                    <div class="form-group">
                        <label for="opt">Set options:</label>
                        <form:select path="optionsIds" multiple="multiple" id="opt">
                            <c:forEach items="${contract.allOptions}" var="item">
                                <form:option label="${item.key}" value="${item.value}"/>
                            </c:forEach>
                        </form:select>
                    </div>



                        <form:hidden path="id" value="${contract.id}"/>

                        <hr class="my-4">
                            <%-- <c:url var="updateButton"  value="/customer/editcustomer">
                              <c:param name="customerID" value="${customer.customerID}"/>
                             </c:url>--%>
                       <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />--%>
                        <button class="w-100 btn btn-primary btn-lg" type="submit"  >Save</button>

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

<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>

<script src="form-validation.js"></script>--%>
<script>
    $("#opt").bootstrapDualListbox();
</script>
</body>
</html>
