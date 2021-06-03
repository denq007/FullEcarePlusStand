<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<body>

<h2>All Customer</h2>
<br>

<table>
    <tr>
        <th>Id</th>
        <th>Tariff name</th>
        <th>Phone Number</th>
        <th>Block</th>


    </tr>
  <%--  <jsp:useBean id="allCustomer" scope="request" type="java.util.List"/>--%>
    <c:forEach var="allContracts" items="${allContracts}">
        <tr>
            <td>${allContracts.id}</td>
            <td>${allContracts.tariffName}</td>
            <td>${allContracts.number}</td>
            <td>${allContracts.blockedByAdmin}</td>

        </tr>
    </c:forEach>
</table>

</body>

</html>
