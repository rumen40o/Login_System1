package com.example.controller;

import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.loginUser(email, password);

        if (user != null) {
            // User is authenticated
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);

            // Redirect to the profile page
            response.sendRedirect(request.getContextPath() + "/frontend/pages/profile.html");
        } else {
            // User authentication failed
            request.setAttribute("error", "Invalid email or password.");
            // Forward to the login page, ensuring the context path is used
            request.getRequestDispatcher(request.getContextPath() + "/frontend/pages/login.html").forward(request, response);
        }
    }
}
