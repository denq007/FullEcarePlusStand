<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: dennn
  Date: 25.04.2021
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Customer Info</h2>
<br>


<form:form  action="saveCustomer" modelAttribute="customer">
    Name <form:input path="customerName"/>
    <br><br>
    Surname <form:input path="customerSurname"/>
    <br><br>
    Birth date <form:input path="customerBirthDate"/>
    <br><br>
    Passport <form:input path="customerPassportDetails"/>
    <br><br>
    Adress <form:input path="customerAdress"/>
    <br><br>
    <input type="submit" value="Save">
</form:form>
</body>
</html>
