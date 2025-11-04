<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Ingreso - ConUni REST DOTNET</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
  <main class="login-container">
    <div class="login-avatar">
      <img src="${pageContext.request.contextPath}/assets/img/userlogin2.png" alt="Icono login">
    </div>
    <h1>Bienvenido</h1>
    <h2>Cliente Web REST DOTNET</h2>
    <form method="post" action="${pageContext.request.contextPath}/login">
      <div class="input-group">
        <img src="${pageContext.request.contextPath}/assets/img/userlogin2.png" alt="Icono usuario">
        <input type="text" name="usuario" placeholder="Usuario" autofocus required>
      </div>
      <div class="input-group">
        <img src="${pageContext.request.contextPath}/assets/img/password.png" alt="Icono contrasena">
        <input type="password" name="contrasena" placeholder="Contrasena" required>
      </div>
      <button type="submit">Iniciar sesion</button>
    </form>
    <p class="error-message">
      <%
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isBlank()) {
          out.print(error);
        } else {
          out.print("&nbsp;");
        }
      %>
    </p>
    <p class="error-message" style="color: var(--text-light-gray); font-size: 12px;">
      Credenciales demo: MONSTER / MONSTER9
    </p>
  </main>
</body>
</html>
