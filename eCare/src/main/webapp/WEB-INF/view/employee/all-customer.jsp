<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>
<span class="pull-right"><a href="/employee/employeecabinet" class="btn btn-info" role="button">Back</a></span>
<h2>All Customer</h2>
<br>

<table>
    <tr>
        <th>Id</th>
        <th>Name</th>  
        <th>Surname</th>
        <th>BirthDate</th>
        <th>Passport</th>
        <th>Address</th>

    </tr>
  <%--  <jsp:useBean id="allCustomer" scope="request" type="java.util.List"/>--%>
    <c:forEach var="customerDTO" items="${allCustomer}">
        <tr>
            <td>${customerDTO.id}</td>
            <td>${customerDTO.name}</td>
            <td>${customerDTO.surname}</td>
            <td>${customerDTO.birthDate}</td>
            <td>${customerDTO.passportDetails}</td>
            <td>${customerDTO.address}</td>
        </tr>
    </c:forEach>
</table>

</body>

</html>
