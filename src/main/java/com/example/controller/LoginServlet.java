package com.example.controller;

import model.User;
import service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/login") // Servlet mapping
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user credentials from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Authenticate user
        User user = userService.loginUser(email, password);

        if (user != null) {
            // If authentication is successful, create session
            HttpSession session = request.getSession();
            session.setAttribute("id", user.getId()); // Store user ID in session
            session.setAttribute("loggedUser", user); // Store entire user object in session

            // Redirect to the ProfileServlet instead of directly to JSP
            response.sendRedirect(request.getContextPath() + "/api/profile");
        } else {
            // If authentication fails, set error message and forward to login page
            request.setAttribute("errorMessage", "Invalid email or password.");
            request.getRequestDispatcher("/frontend/pages/login.jsp").forward(request, response);
        }
    }
}
