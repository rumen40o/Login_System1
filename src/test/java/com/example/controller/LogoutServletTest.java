package com.example.controller;

import com.example.controller.LogoutServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutServletTest {

    @InjectMocks
    private LogoutServlet logoutServlet; // The servlet under test

    @Mock
    private HttpServletRequest request; // Mocking HttpServletRequest

    @Mock
    private HttpServletResponse response; // Mocking HttpServletResponse

    @Mock
    private HttpSession session; // Mocking HttpSession

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testDoPost_SessionExists() throws Exception {
        // Arrange
        when(request.getSession(false)).thenReturn(session); // Mocking an existing session

        // Act
        logoutServlet.doPost(request, response);

        // Assert
        verify(session).invalidate(); // Verify that session.invalidate() was called
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp"); // Verify redirection
    }

    @Test
    public void testDoPost_SessionDoesNotExist() throws Exception {
        // Arrange
        when(request.getSession(false)).thenReturn(null); // Mocking no session existing

        // Act
        logoutServlet.doPost(request, response);

        // Assert
        verify(session, never()).invalidate(); // Verify that session.invalidate() was not called
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp"); // Verify redirection
    }

    @Test
    public void testDoGet() throws Exception {
        // Act
        logoutServlet.doGet(request, response);

        // Assert
        // Verify that the doGet method calls doPost internally
        verify(request).getSession(false); // It should trigger the same logic as doPost
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
    }
}

