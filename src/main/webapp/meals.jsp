<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 27.03.2017
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://javawebinar.ru/functions" prefix="fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            border: 1px solid rgba(0, 0, 0, 0.64);
        }

        th, td {
            text-align: left;
            padding: 8px;
            border: 1px solid rgba(0, 0, 0, 0.64);
        }

        th {
            background-color: #4CAF50;
            color: white;
            border: 1px solid rgba(0, 0, 0, 0.64);
        }
    </style>
</head>
<body>
<h1>Meals</h1>
<table>
    <tr>
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan="2">Действие</th>
    </tr>
    <jsp:useBean id="mealList" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
    <c:forEach items="${mealList}" var="meal">
        <tr style="${meal.exceed == true ? 'background-color:#FF614E' : 'background-color:#8DFF00'}">
            <td>${fmt:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd hh:mm')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>

<br/>

<a href="meals?action=new">Add new meal</a>
</body>
</html>
