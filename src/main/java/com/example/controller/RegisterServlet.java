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
        System.out.println("RegisterServlet doPost called.");

        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            boolean registrationSuccess = userService.registerUser(firstName, lastName, email, password);
            if (registrationSuccess) {
                response.setStatus(HttpServletResponse.SC_OK); // Set status 200
                response.sendRedirect(request.getContextPath() + "/frontend/pages/login.html");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Set status 400
                request.setAttribute("error", "Email already exists or other issues.");
                request.getRequestDispatcher(request.getContextPath() + "/frontend/pages/register.html").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error stack trace for debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set status 500
            response.getWriter().write("{\"message\":\"Internal Server Error\"}"); // Send JSON response
        }
    }
}

