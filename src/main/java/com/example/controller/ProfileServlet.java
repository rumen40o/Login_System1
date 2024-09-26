package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;

import java.io.IOException;

@WebServlet("/api/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("id") != null) {
            int userId = (int) session.getAttribute("id");
            User user = userService.getUserById(userId); // Fetch user details using ID

            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/frontend/pages/profile.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "User not found.");
                response.sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp"); // Redirect to login if user not found
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // Redirect to login if not logged in
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("id") != null) {
            int userId = (int) session.getAttribute("id");
            String newFirstName = request.getParameter("first_name");
            String newLastName = request.getParameter("last_name");
            String newPassword = request.getParameter("password"); // Accept empty for no change

            // Update user information
            boolean isUpdated = userService.updateUser(userId, newFirstName, newLastName, newPassword);

            if (isUpdated) {
                // Optionally, refresh the user data in the session
                User updatedUser = userService.getUserById(userId);
                session.setAttribute("id", updatedUser.getId()); // Make sure you have the correct ID
                response.sendRedirect(request.getContextPath() + "/api/profile?updateSuccess=true");
            } else {
                request.setAttribute("errorMessage", "Failed to update profile. Please try again.");
                doGet(request, response); // Re-display the profile page with error message
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp"); // Redirect to login if not logged in
        }
    }
}
