package com.example.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServletTest {

    private RegisterServlet registerServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        registerServlet = new RegisterServlet(userService);
    }

    @Test
    public void testDoGet() throws Exception {

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/frontend/pages/register.jsp")).thenReturn(dispatcher);

        registerServlet.doGet(request, response);

        verify(session).setAttribute(eq("captcha"), any(String.class));

        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_ValidCaptcha() throws Exception {
        String captchaValue = "abcd12";
        when(request.getParameter("captcha")).thenReturn(captchaValue);
        when(session.getAttribute("captcha")).thenReturn(captchaValue);
        when(request.getParameter("first_name")).thenReturn("John");
        when(request.getParameter("last_name")).thenReturn("Doe");
        when(request.getParameter("email")).thenReturn("john.doe@example.com");
        when(request.getParameter("password")).thenReturn("password123");

        when(userService.registerUser(anyString(), anyString(), anyString(), anyString())).thenReturn(true);

        registerServlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPost_InvalidCaptcha() throws Exception {

        when(request.getParameter("captcha")).thenReturn("wrongCaptcha");
        when(session.getAttribute("captcha")).thenReturn("abcd12");

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/frontend/pages/register.jsp")).thenReturn(dispatcher);

        registerServlet.doPost(request, response);

        verify(request).setAttribute("errorMessage", "Incorrect CAPTCHA. Please try again.");
        verify(dispatcher).forward(request, response);
    }
}
