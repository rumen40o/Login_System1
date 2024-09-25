package com.example.controller;

import dao.UserDao;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user input from the registration form
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Attempt to register the user
        boolean registrationSuccess = userService.registerUser(firstName, lastName, email, password);

        // Check if registration was successful
        if (registrationSuccess) {
            response.sendRedirect(request.getContextPath() + "/frontend/pages/login.html");
        } else {
            request.setAttribute("error", "Email already exists or other issues.");
            request.getRequestDispatcher("/frontend/pages/register.html").forward(request, response);
        }
    }

}
