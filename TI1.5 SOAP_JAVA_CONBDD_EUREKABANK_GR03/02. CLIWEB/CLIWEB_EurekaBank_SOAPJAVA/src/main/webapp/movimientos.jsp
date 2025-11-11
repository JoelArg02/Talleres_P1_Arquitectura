<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ec.edu.gr03.service.EurekaBankClient" %>
<%@ page import="ec.edu.gr03.ws.Movimiento" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    // Verificar sesión
    if (session == null || session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String numeroCuenta = request.getParameter("cuenta");
    List<Movimiento> movimientos = null;
    
    if (numeroCuenta != null && !numeroCuenta.trim().isEmpty()) {
        movimientos = EurekaBankClient.traerMovimientos(numeroCuenta.trim());
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EurekaBank - Movimientos</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <!-- Incluir navbar -->
        <jsp:include page="WEB-INF/navbar.jsp" />
        
        <!-- Contenido Principal -->
        <div class="main-content">
            <% if (movimientos == null) { %>
                <!-- Formulario de consulta -->
                <div class="form-container">
                    <h1 class="form-title">Consultar Movimientos</h1>
                    
                    <form action="movimientos.jsp" method="get" onsubmit="return validarMovimientos()">
                        <div class="form-group">
                            <label for="cuenta" class="form-label">Número de Cuenta</label>
                            <input type="text" 
                                   id="cuenta" 
                                   name="cuenta" 
                                   class="form-input" 
                                   placeholder="Ej: 000123456">
                        </div>
                        
                        <button type="submit" class="form-button">Ver Movimientos</button>
                    </form>
                </div>
            <% } else { %>
                <!-- Tabla de movimientos -->
                <div class="table-container">
                    <h1 class="table-title">Historial de Movimientos</h1>
                    <p style="text-align: center; color: #666; margin-bottom: 20px;">
                        Cuenta: <%= numeroCuenta %>
                    </p>
                    
                    <% if (movimientos.isEmpty()) { %>
                        <div class="alert alert-info">
                            No se encontraron movimientos para la cuenta <%= numeroCuenta %>.
                        </div>
                    <% } else { %>
                        <table class="data-table">
                            <thead>
                                <tr>
                                    <th>N° Movimiento</th>
                                    <th>Fecha</th>
                                    <th>Tipo</th>
                                    <th>Acción</th>
                                    <th style="text-align: right;">Importe</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                for (Movimiento mov : movimientos) { 
                                %>
                                <tr>
                                    <td><%= mov.getNromov() %></td>
                                    <td>
                                        <%= mov.getFecha() != null ? 
                                            sdf.format(mov.getFecha().toGregorianCalendar().getTime()) : 
                                            "N/A" %>
                                    </td>
                                    <td><%= mov.getTipo() %></td>
                                    <td><%= mov.getAccion() %></td>
                                    <td style="text-align: right;">
                                        $<%= String.format("%.2f", mov.getImporte()) %>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    <% } %>
                    
                    <div style="text-align: center; margin-top: 20px;">
                        <a href="movimientos.jsp" class="form-button" style="display: inline-block; text-decoration: none; padding: 12px 30px; width: auto;">
                            Nueva Consulta
                        </a>
                    </div>
                </div>
            <% } %>
        </div>
    </div>
    
    <script src="js/scripts.js"></script>
</body>
</html>
