<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EurekaBank - Login</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="login-container">
        <!-- Panel Izquierdo con Logo -->
        <div class="login-sidebar">
            <div class="logo-container">
                <img src="images/logo.png" alt="EurekaBank Logo" class="logo">
            </div>
        </div>
        
        <!-- Panel Derecho con Formulario -->
        <div class="login-main">
            <div class="form-container">
                <h1 class="form-title">Iniciar Sesión</h1>
                
                <!-- Mostrar errores si existen -->
                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-error">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                
                <form action="login" method="post" onsubmit="return validarLogin()">
                    <div class="form-group">
                        <label for="username" class="form-label">Usuario</label>
                        <input type="text" 
                               id="username" 
                               name="username" 
                               class="form-input" 
                               placeholder="Ingrese su usuario"
                               autocomplete="username">
                    </div>
                    
                    <div class="form-group">
                        <label for="password" class="form-label">Contraseña</label>
                        <input type="password" 
                               id="password" 
                               name="password" 
                               class="form-input" 
                               placeholder="Ingrese su contraseña"
                               autocomplete="current-password">
                    </div>
                    
                    <button type="submit" class="form-button">Iniciar Sesión</button>
                </form>
            </div>
        </div>
    </div>
    
    <script src="js/scripts.js"></script>
</body>
</html>
