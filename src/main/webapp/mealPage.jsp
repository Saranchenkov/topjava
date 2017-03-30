<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 28.03.2017
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal Page</title>
</head>
<body>
<h1>${param.action == 'new' ? 'New meal' : 'Update meal'}</h1>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="POST" action="meals">
    <label>
        <input hidden type="number" name="id" value="${meal.id}">
    </label>
    <label>DateTime :</label>
    <label>
        <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
    </label>
    <br/>

    <label>Description :</label>
    <label>
        <input type="text" name="description" value="${meal.description}">
    </label>
    <br/>

    <label>Calories :</label>
    <label>
        <input type="number" name="calories" min="0" value="${meal.calories}">
    </label>
    <br/>

    <input type="submit" value="Submit" />
</form>
</body>
</html>
