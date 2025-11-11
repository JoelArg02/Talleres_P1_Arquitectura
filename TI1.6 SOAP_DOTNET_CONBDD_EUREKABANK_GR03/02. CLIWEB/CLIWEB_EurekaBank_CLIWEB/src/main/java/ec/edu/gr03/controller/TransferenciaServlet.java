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
 * Servlet para procesar transferencias
 */
@WebServlet(name = "TransferenciaServlet", urlPatterns = {"/transferencia"})
public class TransferenciaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String cuentaOrigen = request.getParameter("cuentaOrigen");
        String cuentaDestino = request.getParameter("cuentaDestino");
        String importeStr = request.getParameter("importe");
        
        try {
            // Validar que no sean la misma cuenta
            if (cuentaOrigen.equals(cuentaDestino)) {
                request.setAttribute("error", "La cuenta origen y destino no pueden ser iguales");
                request.getRequestDispatcher("transferencia.jsp").forward(request, response);
                return;
            }
            
            double importe = Double.parseDouble(importeStr);
            
            if (importe <= 0) {
                request.setAttribute("error", "El importe debe ser mayor que cero");
                request.getRequestDispatcher("transferencia.jsp").forward(request, response);
                return;
            }
            
            // Realizar la transferencia
            int resultado = EurekaBankClient.regTransferencia(cuentaOrigen, cuentaDestino, importe);
            
            if (resultado == 1) {
                request.setAttribute("success", "Transferencia realizada con éxito");
            } else {
                request.setAttribute("error", "No se pudo realizar la transferencia. Verifique las cuentas y el saldo disponible.");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El importe debe ser un número válido");
        } catch (Exception e) {
            request.setAttribute("error", "Error al procesar la transferencia: " + e.getMessage());
        }
        
        request.getRequestDispatcher("transferencia.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("transferencia.jsp");
    }
}
