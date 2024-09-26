<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/frontend/css/login_register.css">
</head>
<body>
<div class="container">
    <h2>Login</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <div class="form-container">
        <form class="login-form" action="${pageContext.request.contextPath}/api/login" method="POST">
            <label for="login-email">Email:</label>
            <input type="email" id="login-email" name="email" required>

            <label for="login-password">Password:</label>
            <input type="password" id="login-password" name="password" required>

            <button type="submit">Login</button>
        </form>
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/frontend/pages/register.jsp">Register</a></p>

    </div>
</div>
<% if (request.getAttribute("errorMessage") != null) { %>
    <div class="error"><%= request.getAttribute("errorMessage") %></div>
<% } %>
</body>
</html>
