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
<form method="POST" action="meals">
    <label>DateTime :</label>
    <label>
        <input type="datetime-local" name="dateTime" required>
    </label>
    <br/>

    <label>Description :</label>
    <label>
        <input type="text" name="description" required>
    </label>
    <br/>

    <label>Calories :</label>
    <label>
        <input type="number" name="calories" min="0" required>
    </label>
    <br/>

    <input type="submit" value="Submit" />
</form>
</body>
</html>
