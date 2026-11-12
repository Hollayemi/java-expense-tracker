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
import java.sql.Date;
import java.time.LocalDate;

/** One servlet serves both the Incomes page and the Expenses page. */
@WebServlet({"/incomes", "/expenses"})
public class TransactionServlet extends HttpServlet {
    private final TransactionDAO dao = new TransactionDAO();

    private TransactionType typeOf(HttpServletRequest req) {
        return "/incomes".equals(req.getServletPath()) ? TransactionType.INCOME : TransactionType.EXPENSE;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        TransactionType type = typeOf(req);

        req.setAttribute("pageTitle", type.getLabel());
        req.setAttribute("active", type.getTable());
        req.setAttribute("path", req.getServletPath());
        req.setAttribute("items", dao.findAll(type, user.getId()));
        req.setAttribute("total", dao.total(type, user.getId()));
        req.setAttribute("today", LocalDate.now().toString());

        req.getRequestDispatcher("/WEB-INF/views/transactions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("user");
        TransactionType type = typeOf(req);
        String self = req.getContextPath() + req.getServletPath();
        String action = req.getParameter("action");

        try {
            if ("delete".equals(action)) {
                dao.delete(type, Integer.parseInt(req.getParameter("id")), user.getId());
            } else {
                String title = req.getParameter("title").trim();
                BigDecimal amount = new BigDecimal(req.getParameter("amount").trim());
                Date date = Date.valueOf(LocalDate.parse(req.getParameter("date")));

                if (title.isEmpty() || title.length() > 100 || amount.signum() <= 0) {
                    throw new IllegalArgumentException("bad input");
                }
                dao.add(type, user.getId(), title, amount, date);
            }
        } catch (RuntimeException e) { // NumberFormat, DateTimeParse, NullPointer, IllegalArgument
            resp.sendRedirect(self + "?error=invalid");
            return;
        }
        resp.sendRedirect(self); // redirect after POST so refresh does not resubmit
    }
}