<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ec.edu.gr03.service.EurekaBankClient" %>
<%@ page import="ec.edu.gr03.model.Cuenta" %>
<%@ page import="java.util.List" %>
<%
    // Verificar sesión
    if (session == null || session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Obtener balances
    List<Cuenta> balances = EurekaBankClient.traerBalances();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EurekaBank - Balances</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <!-- Incluir navbar -->
        <jsp:include page="WEB-INF/navbar.jsp" />
        
        <!-- Contenido Principal -->
        <div class="main-content">
            <div class="table-container">
                <h1 class="table-title">Balances de Cuentas</h1>
                
                <% if (balances == null || balances.isEmpty()) { %>
                    <div class="alert alert-info">
                        No hay cuentas activas para mostrar.
                    </div>
                <% } else { %>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th style="text-align: center;">N° Cuenta</th>
                                <th>Cliente</th>
                                <th style="text-align: right;">Saldo</th>
                                <th style="text-align: center;">Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Cuenta cuenta : balances) { 
                                String numCuenta = cuenta.getNumeroCuenta() != null ? cuenta.getNumeroCuenta() : "N/A";
                                String nombreCliente = cuenta.getNombreCliente() != null ? cuenta.getNombreCliente() : "N/A";
                                Double saldo = cuenta.getSaldo() != null ? cuenta.getSaldo() : 0.0;
                                String estado = cuenta.getEstado() != null ? cuenta.getEstado() : "N/A";
                            %>
                            <tr>
                                <td style="text-align: center;"><%= numCuenta %></td>
                                <td><%= nombreCliente %></td>
                                <td style="text-align: right;">
                                    $<%= String.format("%,.2f", saldo) %>
                                </td>
                                <td style="text-align: center;">
                                    <% String colorEstado = estado.equals("ACTIVO") ? "#27ae60" : "#e74c3c"; %>
                                    <span style="color: <%= colorEstado %>; font-weight: bold;">
                                        <%= estado %>
                                    </span>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    
                    <div style="text-align: center; margin-top: 20px; color: #666;">
                        <p>Total de cuentas: <%= balances.size() %></p>
                    </div>
                <% } %>
            </div>
        </div>
    </div>
    
    <script src="js/scripts.js"></script>
</body>
</html>
