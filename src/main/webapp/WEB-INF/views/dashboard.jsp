<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="active" value="dashboard"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Dashboard | Money Track</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
</head>
<body>
<div class="layout">
  <%@ include file="_sidebar.jspf" %>

  <main class="main card">
    <h1>All Transactions</h1>

    <div class="grid">
      <section>
        <div class="card chart-card"><canvas id="chart"></canvas></div>

        <div class="totals">
          <div class="card stat">
            <h2>Total Income</h2>
            <p>₦ <fmt:formatNumber value="${totalIncome}" pattern="#,##0.##"/></p>
          </div>
          <div class="card stat">
            <h2>Total Expense</h2>
            <p>₦ <fmt:formatNumber value="${totalExpense}" pattern="#,##0.##"/></p>
          </div>
          <div class="card stat balance">
            <h2>Total Balance</h2>
            <p class="${balance.signum() < 0 ? 'neg' : 'pos'}">₦ <fmt:formatNumber value="${balance}" pattern="#,##0.##"/></p>
          </div>
        </div>
      </section>

      <section>
        <h1 class="h-small">Recent History</h1>
        <c:forEach var="t" items="${recent}">
          <div class="card history-item ${t.income ? 'income' : 'expense'}">
            <span><c:out value="${t.title}"/></span>
            <span>${t.income ? '+' : '-'}<fmt:formatNumber value="${t.amount}" pattern="#,##0.##"/></span>
          </div>
        </c:forEach>
        <c:if test="${empty recent}">
          <p class="muted">No transactions yet. Add one from Incomes or Expenses.</p>
        </c:if>
      </section>
    </div>
  </main>
</div>

<script>
  const labels = [<c:forEach var="d" items="${daily}" varStatus="s">"<fmt:formatDate value="${d.date}" pattern="dd/MM/yyyy"/>"<c:if test="${!s.last}">,</c:if></c:forEach>];
  const incomeData = [<c:forEach var="d" items="${daily}" varStatus="s">${d.income}<c:if test="${!s.last}">,</c:if></c:forEach>];
  const expenseData = [<c:forEach var="d" items="${daily}" varStatus="s">${d.expense}<c:if test="${!s.last}">,</c:if></c:forEach>];

  new Chart(document.getElementById('chart'), {
    type: 'line',
    data: {
      labels: labels,
      datasets: [
        { label: 'Income',   data: incomeData,  borderColor: '#3a9d1f', backgroundColor: '#3a9d1f', tension: 0.4 },
        { label: 'Expenses', data: expenseData, borderColor: '#e5301f', backgroundColor: '#e5301f', tension: 0.4 }
      ]
    },
    options: { responsive: true, maintainAspectRatio: false, scales: { y: { beginAtZero: true } } }
  });
</script>
</body>
</html>
