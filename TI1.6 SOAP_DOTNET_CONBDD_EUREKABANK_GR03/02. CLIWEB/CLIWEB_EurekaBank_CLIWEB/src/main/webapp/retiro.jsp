<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Verificar sesión
    if (session == null || session.getAttribute("usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EurekaBank - Retiro</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <!-- Incluir navbar -->
        <jsp:include page="WEB-INF/navbar.jsp" />
        
        <!-- Contenido Principal -->
        <div class="main-content">
            <div class="form-container">
                <h1 class="form-title">Realizar Retiro</h1>
                
                <!-- Mostrar mensajes -->
                <% if (request.getAttribute("success") != null) { %>
                    <div class="alert alert-success">
                        <%= request.getAttribute("success") %>
                    </div>
                <% } %>
                
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-error">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                
                <form action="retiro" method="post" onsubmit="return validarRetiro()">
                    <div class="form-group">
                        <label for="cuenta" class="form-label">Número de Cuenta</label>
                        <input type="text" 
                               id="cuenta" 
                               name="cuenta" 
                               class="form-input" 
                               placeholder="Ej: 000123456"
                               required>
                    </div>
                    
                    <div class="form-group">
                        <label for="importe" class="form-label">Importe ($)</label>
                        <input type="number" 
                               id="importe" 
                               name="importe" 
                               class="form-input" 
                               placeholder="Ej: 100.00"
                               step="0.01"
                               min="0.01"
                               required>
                    </div>
                    
                    <button type="submit" class="form-button">Realizar Retiro</button>
                </form>
            </div>
        </div>
    </div>
    
    <script src="js/scripts.js"></script>
</body>
</html>
