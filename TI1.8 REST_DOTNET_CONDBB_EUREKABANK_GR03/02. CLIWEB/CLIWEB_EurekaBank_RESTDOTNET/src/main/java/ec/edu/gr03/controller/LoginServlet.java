package ec.edu.gr03.controller;

import ec.edu.gr03.service.EurekaBankClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet para manejar el inicio de sesión
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validar que los campos no estén vacíos
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Por favor, ingrese usuario y contraseña");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        // Intentar login usando el servicio REST
        boolean loginExitoso = EurekaBankClient.login(username.trim(), password.trim());
        
        if (loginExitoso) {
            // Crear sesión
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", username);
            session.setMaxInactiveInterval(30 * 60); // 30 minutos
            
            // Redirigir a la página de movimientos
            response.sendRedirect("movimientos.jsp");
        } else {
            // Login fallido
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si acceden por GET, redirigir al login
        response.sendRedirect("login.jsp");
    }
}
