
<!DOCTYPE html> 
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <div class="form-container">
        <h2>User Profile</h2>
<form action="${pageContext.request.contextPath}/api/profile" method="post">
    <label>First Name:</label>
    <input type="text" name="first_name" value="${user.firstName}" required />

    <label>Last Name:</label>
    <input type="text" name="last_name" value="${user.lastName}" required />

    <label>Password:</label>
    <input type="password" name="password" required />

    <button type="submit">Save Changes</button>
</form>
    </div>
</div>
</body>
</html>
