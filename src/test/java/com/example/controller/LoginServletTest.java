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
    private LoginServlet loginServlet;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoPost_LoginSuccessful() throws Exception {

        String email = "test@example.com";
        String password = "password123";
        User user = new User("John", "Doe", email, password);

        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userService.loginUser(email, password)).thenReturn(user);
        when(request.getSession()).thenReturn(session);


        loginServlet.doPost(request, response);

        verify(session).setAttribute("id", user.getId());
        verify(session).setAttribute("loggedUser", user);
        verify(response).sendRedirect(request.getContextPath() + "/api/profile");
    }

    @Test
    public void testDoPost_LoginFailed() throws Exception {

        String email = "test@example.com";
        String password = "wrongpassword";

        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userService.loginUser(email, password)).thenReturn(null);
        when(request.getRequestDispatcher("/frontend/pages/login.jsp")).thenReturn(requestDispatcher);


        loginServlet.doPost(request, response);


        verify(request).setAttribute("errorMessage", "Invalid email or password.");
        verify(requestDispatcher).forward(request, response);
    }
}
