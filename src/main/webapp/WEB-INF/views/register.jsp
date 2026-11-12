<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Create Account | Money Track</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-body">
  <form class="card auth-card" action="${pageContext.request.contextPath}/register" method="post">
    <h1>Create Account</h1>
    <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>
    <input type="text" name="name" placeholder="Full name" value="<c:out value='${name}'/>" required>
    <input type="text" name="username" placeholder="Username" value="<c:out value='${username}'/>" required>
    <input type="password" name="password" placeholder="Password (6+ characters)" required>
    <button type="submit" class="btn">Create Account</button>
    <p class="muted">Have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
  </form>
</body>
</html>
