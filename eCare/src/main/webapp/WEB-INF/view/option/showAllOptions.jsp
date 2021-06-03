<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<body>
<sec:authorize access="hasRole('EMPLOYEE')">
<span class="pull-right"><a href="/employee/employeecabinet" class="btn btn-info" role="button">Back</a></span>
</sec:authorize>
<sec:authorize access="!hasRole('EMPLOYEE')">
    <span class="pull-right"><a href="/customer/showcustomerinformation" class="btn btn-info" role="button">Back</a></span>
</sec:authorize>
<h2>All Options</h2>
<br>

<table>
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
