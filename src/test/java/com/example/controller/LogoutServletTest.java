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
    private LogoutServlet logoutServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoPost_SessionExists() throws Exception {

        when(request.getSession(false)).thenReturn(session);


        logoutServlet.doPost(request, response);


        verify(session).invalidate();
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
    }

    @Test
    public void testDoPost_SessionDoesNotExist() throws Exception {

        when(request.getSession(false)).thenReturn(null);


        logoutServlet.doPost(request, response);


        verify(session, never()).invalidate();
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
    }

    @Test
    public void testDoGet() throws Exception {

        logoutServlet.doGet(request, response);

        verify(request).getSession(false);
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
    }
}

