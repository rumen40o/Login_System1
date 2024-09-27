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

    private RegisterServlet registerServlet; // The servlet under test

    @Mock
    private HttpServletRequest request; // Mocked request

    @Mock
    private HttpServletResponse response; // Mocked response

    @Mock
    private HttpSession session; // Mocked session

    @Mock
    private UserService userService; // Mocked UserService

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initializes the mocks
        when(request.getSession()).thenReturn(session); // Setup session mock
        registerServlet = new RegisterServlet(userService); // Inject the mock UserService
    }

    @Test
    public void testDoGet() throws Exception {
        // Mock the RequestDispatcher
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/frontend/pages/register.jsp")).thenReturn(dispatcher);

        // Execute the doGet method
        registerServlet.doGet(request, response);

        // Verify that the CAPTCHA is set in the session
        verify(session).setAttribute(eq("captcha"), any(String.class));

        // Verify that the correct view is forwarded
        verify(dispatcher).forward(request, response); // Ensure forward is called
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
        // Arrange: Set up the necessary mock behaviors
        when(request.getParameter("captcha")).thenReturn("wrongCaptcha");
        when(session.getAttribute("captcha")).thenReturn("abcd12");

        // Mock the RequestDispatcher
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/frontend/pages/register.jsp")).thenReturn(dispatcher);

        // Act: Call the method under test
        registerServlet.doPost(request, response);

        // Assert: Verify the expected behavior
        verify(request).setAttribute("errorMessage", "Incorrect CAPTCHA. Please try again.");
        verify(dispatcher).forward(request, response); // Ensure forward is called
    }
}
