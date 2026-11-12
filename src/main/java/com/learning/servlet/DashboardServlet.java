package com.learning.servlet;

import com.learning.dao.TransactionDAO;
import com.learning.model.TransactionType;
import com.learning.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final TransactionDAO dao = new TransactionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        BigDecimal income = dao.total(TransactionType.INCOME, user.getId());
        BigDecimal expense = dao.total(TransactionType.EXPENSE, user.getId());

        req.setAttribute("totalIncome", income);
        req.setAttribute("totalExpense", expense);
        req.setAttribute("balance", income.subtract(expense));
        req.setAttribute("recent", dao.recent(user.getId(), 8));
        req.setAttribute("daily", dao.dailyTotals(user.getId()));

        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}