package com.example.controller;

import service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean registrationSuccess = userService.registerUser(firstName, lastName, email, password);

        if (registrationSuccess) {
            // Redirect to login page after successful registration
            response.sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
        } else {
            // Set error message and forward back to the register.jsp page
            request.setAttribute("errorMessage", "Email already exists or other issues.");
            request.getRequestDispatcher("/frontend/pages/register.jsp").forward(request, response);
        }
    }
}
