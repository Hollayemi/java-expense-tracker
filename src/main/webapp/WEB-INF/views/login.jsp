<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Sign In | Money Track</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-body">
  <form class="card auth-card" action="${pageContext.request.contextPath}/login" method="post">
    <h1>Sign In</h1>
    <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
    <input type="text" name="username" placeholder="Username" required>
    <input type="password" name="password" placeholder="Password" required>
    <button type="submit" class="btn">Sign In</button>
    <p class="muted">No account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
  </form>
</body>
</html>
