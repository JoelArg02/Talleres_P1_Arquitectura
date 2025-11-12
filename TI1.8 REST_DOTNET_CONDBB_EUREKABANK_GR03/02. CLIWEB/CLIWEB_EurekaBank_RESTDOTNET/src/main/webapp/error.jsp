<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - EurekaBank</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .error-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            text-align: center;
            padding: 20px;
        }
        .error-code {
            font-size: 72px;
            font-weight: bold;
            color: #e74c3c;
            margin-bottom: 20px;
        }
        .error-message {
            font-size: 24px;
            color: #333;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">
            <%= request.getAttribute("jakarta.servlet.error.status_code") != null ? 
                request.getAttribute("jakarta.servlet.error.status_code") : "Error" %>
        </div>
        <div class="error-message">
            Lo sentimos, ha ocurrido un error.
        </div>
        <a href="login.jsp" class="form-button" style="display: inline-block; text-decoration: none; padding: 12px 30px; width: auto;">
            Volver al Inicio
        </a>
    </div>
</body>
</html>
