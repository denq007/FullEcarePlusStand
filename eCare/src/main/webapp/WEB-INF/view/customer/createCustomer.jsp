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
    <meta charset="utf-8">
    <title>Checkout example</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <script type="text/javascript" src = "/js/date.js"></script>
</header>
<head>



<%-- <style>
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
     </style>--%>


    <!-- Custom styles for this template -->
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
    <main>
<%--        <div class="py-5 text-center">--%>

            <h2>Personal Information</h2>
      <%--  </div>--%>

        <form:form modelAttribute="customer" method="post">
        <div class="row g-5">

            <div class="col-md-7 col-lg-8">
                <h4 class="mb-3">Your data:</h4>
                <form class="needs-validation" novalidate>
                    <div class="row g-3">
                        <div class="col-sm-6">
                            <label for="firstName" class="form-label">First name</label>
                                <%--<form:input path="name" value="${customer.name}" class="form-control" id="firstName" />--%>
                            <input type="text" name="name" class="form-control" id="firstName" placeholder=""
                                   pattern="[a-zA-Z]+"   value="${customer.name}" required >
                             <%--     <div class="invalid-feedback">
                                      Only letters.
                                  </div>--%>
                        </div>

                        <div class="col-sm-6">
                            <label for="lastName" class="form-label">Last name</label>
                                <%-- <form:input path="surname" value="${customer.surname}" class="form-control" id="lastName"/>--%>
                            <input type="text" name="surname" class="form-control" id="lastName" placeholder=""
                                   pattern="[a-zA-Z]+"     value="${customer.surname}" required>
                          <%--  <div class="invalid-feedback">
                                Only letters.
                            </div>--%>
                        </div>

                        <div class="col-12">
                            <label for="address" class="form-label">Address</label>
                                <%--  <form:input path="address" value="${customer.address}" class="form-control" id="address"/>--%>
                            <input type="text" name="address" class="form-control" id="address"
                                   placeholder="1234 Main St" value="${customer.address}" required>
                                <%--      <div class="invalid-feedback">
                                          Please enter your address.
                                      </div>--%>
                        </div>

                        <div class="col-12">
                            <label for="passport" class="form-label">Passport<span class="text-muted"></span></label>
                                <%-- <form:input path="passportDetails" value="${customer.passportDetails}" class="form-control" id="passport"/>--%>
                            <input type="text" name="passportDetails" class="form-control" id="passport"
                                   placeholder="Passport detail" value="${customer.passportDetails}" required>
                                <%--         <div class="invalid-feedback">
                                             Please enter your passport detail.
                                         </div>--%>
                        </div>
                        <div class="col-12">
                            <label for="date" class="form-label">Birth Date<span class="text-muted"></span></label>
                                <%-- <form:input type="date" value="${client.birthday}" path="birthDate" class="form-control" id="date" min="1910-01-01" max="2021-05-18"/>--%>
                            <input type="date" name="birthDate" class="form-control" id="date" placeholder=""
                                   value="${customer.birthDate}" min="1910-01-01">
                                <%--<div class="invalid-feedback">
                                    Incorrect.
                                </div>--%>
                        </div>


                        <form:hidden path="id" value="${customer.id}"/>
                            <%--  <form:hidden path="userDTO" value="${customer.userDTO.userId}"/>--%>

                        <hr class="my-4">
                            <%-- <c:url var="updateButton"  value="/customer/editcustomer">
                              <c:param name="customerID" value="${customer.customerID}"/>
                             </c:url>--%>
                        <button class="w-100 btn btn-primary btn-lg" type="submit"
                                href="/editcontract"<%--'${updateButton}'--%> >Edit data
                        </button>
                    </div>
                    </form:form>
    </main>

    <footer class="my-5 pt-5 text-muted text-center text-small">
        <p class="mb-1">&copy; 2021–2021</p>
        <ul class="list-inline">

        </ul>
    </footer>
</div>
<script>
        $(document).ready(function () {
        const d = new Date();
        let day = d.getDate();
        let month = d.getMonth() + 1;
        const year = d.getFullYear()-14;
        if(day<10)
{
day='0'+day;
}
if(month<10)
{
month='0'+month;
}
let maxDate = year + "-" + month + "-" + day;
$("#date").attr({
"max": maxDate
})
});
</script>

</body>
</html>
