package com.example.controller;

import com.example.controller.LoginServlet;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    @InjectMocks
    private LoginServlet loginServlet; // The servlet under test

    @Mock
    private UserService userService; // Mocking the UserService

    @Mock
    private HttpServletRequest request; // Mocking HttpServletRequest

    @Mock
    private HttpServletResponse response; // Mocking HttpServletResponse

    @Mock
    private HttpSession session; // Mocking HttpSession

    @Mock
    private RequestDispatcher requestDispatcher; // Mocking RequestDispatcher for forwarding

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testDoPost_LoginSuccessful() throws Exception {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        User user = new User("John", "Doe", email, password); // Using the correct User constructor

        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userService.loginUser(email, password)).thenReturn(user); // Mocking login success
        when(request.getSession()).thenReturn(session);

        // Act
        loginServlet.doPost(request, response);

        // Assert
        verify(session).setAttribute("id", user.getId());  // Assuming getId() method exists
        verify(session).setAttribute("loggedUser", user);
        verify(response).sendRedirect(request.getContextPath() + "/api/profile");
    }

    @Test
    public void testDoPost_LoginFailed() throws Exception {
        // Arrange
        String email = "test@example.com";
        String password = "wrongpassword";

        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userService.loginUser(email, password)).thenReturn(null); // Mocking login failure
        when(request.getRequestDispatcher("/frontend/pages/login.jsp")).thenReturn(requestDispatcher);

        // Act
        loginServlet.doPost(request, response);

        // Assert
        verify(request).setAttribute("errorMessage", "Invalid email or password.");
        verify(requestDispatcher).forward(request, response);
    }
}
