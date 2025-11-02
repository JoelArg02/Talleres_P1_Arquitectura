<%-- 
    Document   : conversion
    Created on : Nov 2, 2025, 3:29:58�?_PM
    Author     : pinza
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%
  String usuario = (String) session.getAttribute("usuario");
  if (usuario == null) {
    response.sendRedirect(request.getContextPath() + "/vista/login.jsp");
    return;
  }

  String valorActual = request.getAttribute("valor") != null ? String.valueOf(request.getAttribute("valor")) : "";
  String unidadEntrada = request.getAttribute("inUnit") != null ? request.getAttribute("inUnit").toString() : "";
  String unidadSalida = request.getAttribute("outUnit") != null ? request.getAttribute("outUnit").toString() : "";
  Object resultadoAttr = request.getAttribute("resultado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Conversion</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
  <main class="conversion-container">
    <header class="conversion-header">
      <div class="user-info">
        <img src="${pageContext.request.contextPath}/assets/img/user.png" alt="Icono usuario">
        <span><%= usuario %></span>
      </div>
      <a class="logout-link" href="${pageContext.request.contextPath}/logout">
        <img src="${pageContext.request.contextPath}/assets/img/return.png" alt="Cerrar sesion">
        Cerrar sesion
      </a>
    </header>

    <section class="conversion-content">
      <div class="login-avatar">
        <img src="${pageContext.request.contextPath}/assets/img/exchange.png" alt="Icono conversion">
      </div>
      <h1>Conversion de Unidades</h1>
      <h2>Selecciona los datos para transformar tu valor</h2>
      <form class="conversion-form" method="post" action="${pageContext.request.contextPath}/convertir">
        <div class="field-group">
          <label for="valor">Valor</label>
          <input id="valor" type="number" step="any" name="valor" value="<%= valorActual %>" required>
        </div>
        <div class="field-group">
          <label for="inUnit">Unidad origen</label>
          <select id="inUnit" name="inUnit">
            <option value="meters"<%= "meters".equals(unidadEntrada) ? " selected" : "" %>>Metros</option>
            <option value="kilometers"<%= "kilometers".equals(unidadEntrada) ? " selected" : "" %>>Kilometros</option>
            <option value="centimeters"<%= "centimeters".equals(unidadEntrada) ? " selected" : "" %>>Centimetros</option>
          </select>
        </div>
        <div class="field-group">
          <label for="outUnit">Unidad destino</label>
          <select id="outUnit" name="outUnit">
            <option value="meters"<%= "meters".equals(unidadSalida) ? " selected" : "" %>>Metros</option>
            <option value="kilometers"<%= "kilometers".equals(unidadSalida) ? " selected" : "" %>>Kilometros</option>
            <option value="centimeters"<%= "centimeters".equals(unidadSalida) ? " selected" : "" %>>Centimetros</option>
          </select>
        </div>
        <div class="actions">
          <button type="submit">Convertir</button>
        </div>
      </form>

      <%
        if (resultadoAttr != null) {
      %>
        <p class="result">
          <%= valorActual %> <%= unidadEntrada %> = <%= resultadoAttr %> <%= unidadSalida %>
        </p>
      <%
        }
      %>
    </section>

    <footer>
      Resultado calculado mediante el servicio SOAP ConUni
    </footer>
  </main>
</body>
</html>
