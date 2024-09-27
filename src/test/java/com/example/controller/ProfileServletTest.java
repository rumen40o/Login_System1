package com.example.controller;

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

import static org.mockito.Mockito.*;

public class ProfileServletTest {

    @InjectMocks
    private ProfileServlet profileServlet; // The servlet under test

    @Mock
    private UserService userService; // Mocking UserService

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
        when(request.getRequestDispatcher("/frontend/pages/profile.jsp")).thenReturn(requestDispatcher); // Moved here to avoid repetition
    }

    // Test for doGet when the session exists and the user is found
    @Test
    public void testDoGet_UserFound() throws Exception {
        // Arrange
        int userId = 1;
        User user = new User("John", "Doe", "john@example.com", "password123");

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        profileServlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("user", user);
        verify(requestDispatcher).forward(request, response);
    }

    // Test for doGet when the user is not found
    @Test
    public void testDoGet_UserNotFound() throws Exception {
        // Arrange
        int userId = 1;

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(null); // Simulate user not found

        // Act
        profileServlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("errorMessage", "User not found.");
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
    }

    // Test for doGet when the session does not exist
    @Test
    public void testDoGet_NoSession() throws Exception {
        // Arrange
        when(request.getSession(false)).thenReturn(null); // Simulate no session

        // Act
        profileServlet.doGet(request, response);

        // Assert
        verify(response).sendRedirect(request.getContextPath() + "/login.jsp");
    }

    // Test for doPost when the profile is updated successfully
    @Test
    public void testDoPost_ProfileUpdatedSuccessfully() throws Exception {
        // Arrange
        int userId = 1;
        String newFirstName = "Jane";
        String newLastName = "Doe";
        String newPassword = "newPassword123";

        User updatedUser = new User(newFirstName, newLastName, "jane@example.com", newPassword);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(userId);
        when(request.getParameter("first_name")).thenReturn(newFirstName);
        when(request.getParameter("last_name")).thenReturn(newLastName);
        when(request.getParameter("password")).thenReturn(newPassword);
        when(userService.updateUser(userId, newFirstName, newLastName, newPassword)).thenReturn(true); // Simulate success
        when(userService.getUserById(userId)).thenReturn(updatedUser);

        // Act
        profileServlet.doPost(request, response);

        // Assert
        verify(session).setAttribute("id", updatedUser.getId());
        verify(response).sendRedirect(request.getContextPath() + "/api/profile?updateSuccess=true");
    }

    // Test for doPost when the profile update fails
    @Test
    public void testDoPost_ProfileUpdateFailed() throws Exception {
        // Arrange
        int userId = 1;
        String newFirstName = "Jane";
        String newLastName = "Doe";
        String newPassword = "newPassword123";

        // Simulate the session and form parameters
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(userId);
        when(request.getParameter("first_name")).thenReturn(newFirstName);
        when(request.getParameter("last_name")).thenReturn(newLastName);
        when(request.getParameter("password")).thenReturn(newPassword);

        // Simulate the update failure
        when(userService.updateUser(userId, newFirstName, newLastName, newPassword)).thenReturn(false);

        // Simulate forwarding to profile.jsp
        when(request.getRequestDispatcher("/frontend/pages/profile.jsp")).thenReturn(requestDispatcher);

        // Act
        profileServlet.doPost(request, response);

        // Assert
        verify(request).setAttribute("errorMessage", "Failed to update profile. Please try again.");
        verify(requestDispatcher).forward(request, response); // Ensure the forward call is made
    }


    // Test for doPost when the session does not exist
    @Test
    public void testDoPost_NoSession() throws Exception {
        // Arrange
        when(request.getSession(false)).thenReturn(null); // Simulate no session

        // Act
        profileServlet.doPost(request, response);

        // Assert
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
    }
}
