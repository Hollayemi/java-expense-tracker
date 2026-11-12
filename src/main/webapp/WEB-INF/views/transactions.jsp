<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><c:out value="${pageTitle}"/> | Money Track</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <%@ include file="_sidebar.jspf" %>

  <main class="main card">
    <h1><c:out value="${pageTitle}"/></h1>
    <div class="card stat inline-stat">
      <h2>Total <c:out value="${pageTitle}"/></h2>
      <p>₦ <fmt:formatNumber value="${total}" pattern="#,##0.##"/></p>
    </div>

    <c:if test="${param.error == 'invalid'}">
      <p class="error">Check your input: title is required and amount must be above 0.</p>
    </c:if>

    <form class="card add-form" action="${pageContext.request.contextPath}${path}" method="post">
      <input type="hidden" name="action" value="add">
      <input type="text" name="title" placeholder="Title" maxlength="100" required>
      <input type="number" name="amount" placeholder="Amount" step="0.01" min="0.01" required>
      <input type="date" name="date" value="${today}" required>
      <button type="submit" class="btn">Add</button>
    </form>

    <c:forEach var="t" items="${items}">
      <div class="card history-item ${t.income ? 'income' : 'expense'}">
        <span><c:out value="${t.title}"/> <small class="muted"><fmt:formatDate value="${t.date}" pattern="dd/MM/yyyy"/></small></span>
        <span class="row-right">
          ${t.income ? '+' : '-'}<fmt:formatNumber value="${t.amount}" pattern="#,##0.##"/>
          <form action="${pageContext.request.contextPath}${path}" method="post" onsubmit="return confirm('Delete this entry?')">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="${t.id}">
            <button type="submit" class="del" title="Delete">✕</button>
          </form>
        </span>
      </div>
    </c:forEach>
    <c:if test="${empty items}"><p class="muted">Nothing here yet.</p></c:if>
  </main>
</div>
</body>
</html>
