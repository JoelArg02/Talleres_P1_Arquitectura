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
 * Servlet para procesar retiros
 */
@WebServlet(name = "RetiroServlet", urlPatterns = {"/retiro"})
public class RetiroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String cuenta = request.getParameter("cuenta");
        String importeStr = request.getParameter("importe");
        
        try {
            double importe = Double.parseDouble(importeStr);
            
            if (importe <= 0) {
                request.setAttribute("error", "El importe debe ser mayor que cero");
                request.getRequestDispatcher("retiro.jsp").forward(request, response);
                return;
            }
            
            // Realizar el retiro
            int resultado = EurekaBankClient.regRetiro(cuenta, importe);
            
            if (resultado == 1) {
                request.setAttribute("success", "Retiro realizado con éxito");
            } else {
                request.setAttribute("error", "No se pudo realizar el retiro. Verifique el número de cuenta y el saldo disponible.");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El importe debe ser un número válido");
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar el retiro: " + e.getMessage());
        }
        
        request.getRequestDispatcher("retiro.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("retiro.jsp");
    }
}
