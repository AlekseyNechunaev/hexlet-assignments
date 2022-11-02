<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>All users</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
    <p>Удалить пользователя с данными?</p>
        <c:forEach var="user" items="${user}">
            <tr>
            <td>${user.key}: </td>
            <td>${user.value}</td>
            </tr>
            <br>
        </c:forEach>
        <a href='/users/delete?id=${user.get("id")}'>Удалить</a>
    </body>
</html>
