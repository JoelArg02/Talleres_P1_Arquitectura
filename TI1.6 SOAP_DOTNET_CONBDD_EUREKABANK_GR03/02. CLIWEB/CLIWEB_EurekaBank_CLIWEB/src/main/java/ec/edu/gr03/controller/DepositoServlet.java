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
 * Servlet para procesar depósitos
 */
@WebServlet(name = "DepositoServlet", urlPatterns = {"/deposito"})
public class DepositoServlet extends HttpServlet {

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
                request.getRequestDispatcher("deposito.jsp").forward(request, response);
                return;
            }
            
            // Realizar el depósito
            int resultado = EurekaBankClient.regDeposito(cuenta, importe);
            
            if (resultado == 1) {
                request.setAttribute("success", "Depósito realizado con éxito");
            } else {
                request.setAttribute("error", "No se pudo realizar el depósito. Verifique el número de cuenta.");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El importe debe ser un número válido");
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar el depósito: " + e.getMessage());
        }
        
        request.getRequestDispatcher("deposito.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("deposito.jsp");
    }
}
