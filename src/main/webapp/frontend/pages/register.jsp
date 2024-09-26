<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/frontend/css/login_register.css"> <!-- Adjusted path -->
</head>
<body>
<div class="container">
    <h2>Register</h2>

    <!-- Check if an error message is passed from the servlet -->
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <div class="form-container">
        <form id="register-form" action="${pageContext.request.contextPath}/api/register" method="POST">
            <label for="register-first-name">First Name:</label>
            <input type="text" id="register-first-name" name="first_name" required>
        
            <label for="register-last-name">Last Name:</label>
            <input type="text" id="register-last-name" name="last_name" required>
        
            <label for="register-email">Email:</label>
            <input type="email" id="register-email" name="email" required>
        
            <label for="register-password">Password:</label>
            <input type="password" id="register-password" name="password" required>

            <label for="captcha">Enter the CAPTCHA shown below:</label>
            <div><p>${captchaText}</p></div> 
            <input type="text" id="captcha" name="captcha" required> 
        
            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="${pageContext.request.contextPath}/frontend/pages/login.jsp">Login</a></p>
    </div>
</div>
</body>
</html>
