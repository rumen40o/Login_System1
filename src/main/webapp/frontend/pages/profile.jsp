<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/frontend/css/login_register.css"> 
</head>

<body>
    <div class="container">
        <h2>User Profile</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <div class="form-container">
            <form action="${pageContext.request.contextPath}/api/profile" method="post">
                <label for="first_name">First Name:</label>
                <input type="text" id="first_name" name="first_name" value="${user.firstName}" required />

                <label for="last_name">Last Name:</label>
                <input type="text" id="last_name" name="last_name" value="${user.lastName}" required />

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required />

                <button type="submit">Save Changes</button>
            </form>
        </div>
        <form action="${pageContext.request.contextPath}/api/logout" method="POST">
            <button type="submit">Logout</button>
        </form>
    </div>
</body>

</html>
