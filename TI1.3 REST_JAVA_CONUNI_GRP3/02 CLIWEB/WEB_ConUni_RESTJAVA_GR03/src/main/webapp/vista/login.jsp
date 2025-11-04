<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  String errorMessage = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Login REST</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
  <main class="login-container">
    <div class="login-avatar">
      <img src="${pageContext.request.contextPath}/assets/img/userlogin.png" alt="Avatar de usuario">
    </div>
    <h1>Bienvenido</h1>
    <h2>Ingresa tus credenciales para continuar</h2>
    <form method="post" action="${pageContext.request.contextPath}/login">
      <div class="input-group">
        <img src="${pageContext.request.contextPath}/assets/img/user.png" alt="Icono usuario">
        <input type="text" name="usuario" placeholder="Usuario" required>
      </div>
      <div class="input-group">
        <img src="${pageContext.request.contextPath}/assets/img/password.png" alt="Icono contrasena">
        <input type="password" name="contrasena" placeholder="Contrasena" required>
      </div>
      <button type="submit">Ingresar</button>
    </form>
    <%
      if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
      <p class="error-message"><%= errorMessage %></p>
    <%
      } else {
    %>
      <p class="error-message">&nbsp;</p>
    <%
      }
    %>
    <p class="helper-message">Autenticaci&oacute;n contra el servicio REST ConUni</p>
  </main>
</body>
</html>

