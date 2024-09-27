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
    private ProfileServlet profileServlet;
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
        when(request.getRequestDispatcher("/frontend/pages/profile.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet_UserFound() throws Exception {

        int userId = 1;
        User user = new User("John", "Doe", "john@example.com", "password123");

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(user);


        profileServlet.doGet(request, response);


        verify(request).setAttribute("user", user);
        verify(requestDispatcher).forward(request, response);
    }


    @Test
    public void testDoGet_UserNotFound() throws Exception {

        int userId = 1;

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(null);

        profileServlet.doGet(request, response);

        verify(request).setAttribute("errorMessage", "User not found.");
        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
    }

    @Test
    public void testDoGet_NoSession() throws Exception {

        when(request.getSession(false)).thenReturn(null);

        profileServlet.doGet(request, response);

        verify(response).sendRedirect(request.getContextPath() + "/login.jsp");
    }

    @Test
    public void testDoPost_ProfileUpdatedSuccessfully() throws Exception {

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
        when(userService.updateUser(userId, newFirstName, newLastName, newPassword)).thenReturn(true);
        when(userService.getUserById(userId)).thenReturn(updatedUser);

        profileServlet.doPost(request, response);

        verify(session).setAttribute("id", updatedUser.getId());
        verify(response).sendRedirect(request.getContextPath() + "/api/profile?updateSuccess=true");
    }

    @Test
    public void testDoPost_ProfileUpdateFailed() throws Exception {

        int userId = 1;
        String newFirstName = "Jane";
        String newLastName = "Doe";
        String newPassword = "newPassword123";


        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(userId);
        when(request.getParameter("first_name")).thenReturn(newFirstName);
        when(request.getParameter("last_name")).thenReturn(newLastName);
        when(request.getParameter("password")).thenReturn(newPassword);


        when(userService.updateUser(userId, newFirstName, newLastName, newPassword)).thenReturn(false);


        when(request.getRequestDispatcher("/frontend/pages/profile.jsp")).thenReturn(requestDispatcher);


        profileServlet.doPost(request, response);


        verify(request).setAttribute("errorMessage", "Failed to update profile. Please try again.");
        verify(requestDispatcher).forward(request, response);
    }



    @Test
    public void testDoPost_NoSession() throws Exception {

        when(request.getSession(false)).thenReturn(null);

        profileServlet.doPost(request, response);

        verify(response).sendRedirect(request.getContextPath() + "/frontend/pages/login.jsp");
    }
}
