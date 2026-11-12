package com.learning.servlet;

import com.learning.dao.UserDAO;
import com.learning.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = trim(req.getParameter("name"));
        String username = trim(req.getParameter("username"));
        String password = req.getParameter("password");

        String error = null;
        if (name.isEmpty() || username.isEmpty() || password == null || password.length() < 6) {
            error = "Fill every field. Password needs at least 6 characters.";
        } else if (userDAO.usernameExists(username)) {
            error = "That username is already taken.";
        } else if (!userDAO.register(new User(name, username, 0, null, null, password))) {
            error = "Could not create the account. Try again.";
        }

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("name", name);
            req.setAttribute("username", username);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}