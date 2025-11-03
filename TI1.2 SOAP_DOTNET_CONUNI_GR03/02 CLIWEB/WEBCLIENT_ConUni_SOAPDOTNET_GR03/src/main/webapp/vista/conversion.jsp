<%@ page contentType="text/html;charset=UTF-8" %>
<%
  String usuario = (String) session.getAttribute("usuario");
  if (usuario == null) {
    response.sendRedirect(request.getContextPath() + "/vista/login.jsp");
    return;
  }

  Double valorActual = null;
  Object valorAttr = request.getAttribute("valor");
  if (valorAttr instanceof Double) {
    valorActual = (Double) valorAttr;
  } else if (valorAttr instanceof String) {
    try {
      valorActual = Double.parseDouble((String) valorAttr);
    } catch (NumberFormatException ignored) {
      valorActual = null;
    }
  }

  String unidadEntrada = (String) request.getAttribute("inUnit");
  if (unidadEntrada == null) {
    unidadEntrada = "kg";
  }
  String unidadSalida = (String) request.getAttribute("outUnit");
  if (unidadSalida == null) {
    unidadSalida = "lb";
  }

  String resultadoTexto = (String) request.getAttribute("resultadoTexto");
  String errorConversion = (String) request.getAttribute("errorConversion");
  String mensajeLogin = (String) request.getAttribute("loginMessage");
  if (mensajeLogin == null || mensajeLogin.isBlank()) {
    mensajeLogin = (String) session.getAttribute("loginMessage");
    if (mensajeLogin != null) {
      session.removeAttribute("loginMessage");
    }
  }
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
      <h1>Conversion de Masa</h1>
      <h2>Selecciona el tipo de conversion que necesites</h2>

      <%
        if (mensajeLogin != null && !mensajeLogin.isBlank()) {
      %>
        <p class="success-message"><%= mensajeLogin %></p>
      <%
        }
      %>

      <form class="conversion-form" method="post" action="${pageContext.request.contextPath}/convertir">
        <div class="field-group">
          <label for="valor">Valor</label>
          <input id="valor" type="number" step="any" name="valor"
                 value="<%= valorActual != null ? valorActual : "" %>" required>
        </div>
        <div class="field-group">
          <label for="inUnit">Unidad origen</label>
          <select id="inUnit" name="inUnit">
            <option value="kg"<%= "kg".equals(unidadEntrada) ? " selected" : "" %>>Kilogramos</option>
            <option value="lb"<%= "lb".equals(unidadEntrada) ? " selected" : "" %>>Libras</option>
            <option value="g"<%= "g".equals(unidadEntrada) ? " selected" : "" %>>Gramos</option>
          </select>
        </div>
        <div class="field-group">
          <label for="outUnit">Unidad destino</label>
          <select id="outUnit" name="outUnit">
            <option value="kg"<%= "kg".equals(unidadSalida) ? " selected" : "" %>>Kilogramos</option>
            <option value="lb"<%= "lb".equals(unidadSalida) ? " selected" : "" %>>Libras</option>
            <option value="g"<%= "g".equals(unidadSalida) ? " selected" : "" %>>Gramos</option>
          </select>
        </div>
        <div class="actions">
          <button type="submit">Convertir</button>
        </div>
      </form>

      <%
        if (errorConversion != null && !errorConversion.isBlank()) {
      %>
        <p class="error-message"><%= errorConversion %></p>
      <%
        } else {
      %>
        <p class="error-message">&nbsp;</p>
      <%
        }
      %>

      <%
        if (resultadoTexto != null) {
      %>
        <p class="result">
          <%= valorActual != null ? valorActual : "" %> <%= unidadEntrada %> = <%= resultadoTexto %> <%= unidadSalida %>
        </p>
      <%
        }
      %>
    </section>

    <footer>
      Resultado calculado mediante el servicio SOAP .NET
    </footer>
  </main>
</body>
</html>
