package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.UserService;

import java.io.IOException;
import java.util.Random;

@WebServlet("/api/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String captchaText = generateCaptchaText();
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaText);
        request.setAttribute("captchaText", captchaText);

        request.getRequestDispatcher("/frontend/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String captchaInput = request.getParameter("captcha");
        String captchaSession = (String) session.getAttribute("captcha");

        if (captchaSession != null && captchaSession.equals(captchaInput)) {

            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            boolean registrationSuccess = userService.registerUser(firstName, lastName, email, password);

            if (registrationSuccess) {
                response.sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
            } else {
                request.setAttribute("errorMessage", "Registration failed. Email might already be taken.");
                doGet(request, response);
            }
        } else {
            // CAPTCHA did not match
            request.setAttribute("errorMessage", "Incorrect CAPTCHA. Please try again.");
            doGet(request, response);
        }
    }

    private String generateCaptchaText() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 6;
        Random random = new Random();

        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}

