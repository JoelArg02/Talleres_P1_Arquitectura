<%@ page contentType="text/html;charset=UTF-8" %>
<%!
  private String capitalizar(String valor) {
    if (valor == null || valor.isBlank()) {
      return "";
    }
    return Character.toUpperCase(valor.charAt(0)) + valor.substring(1);
  }
%>
<%
  String usuario = (String) session.getAttribute("usuario");
  if (usuario == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
  }

  java.util.List<String> unidades = (java.util.List<String>) request.getAttribute("units");
  if (unidades == null || unidades.isEmpty()) {
    unidades = java.util.Arrays.asList("metros", "kilometros", "centimetros");
  }

  String valorActual = request.getAttribute("valor") != null ? request.getAttribute("valor").toString() : "";
  String unidadEntrada = request.getAttribute("inUnit") != null ? request.getAttribute("inUnit").toString() : "";
  String unidadSalida = request.getAttribute("outUnit") != null ? request.getAttribute("outUnit").toString() : "";
  Object resultadoAttr = request.getAttribute("resultado");
  String errorConversion = (String) request.getAttribute("errorConversion");
  String tipo = request.getAttribute("tipo") != null ? request.getAttribute("tipo").toString() : "longitud";
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
        <input type="hidden" name="tipo" value="<%= tipo %>">
        <div class="field-group">
          <label for="valor">Valor</label>
          <input id="valor" type="number" step="any" name="valor" value="<%= valorActual %>" required>
        </div>
        <div class="field-group">
          <label for="inUnit">Unidad origen</label>
          <select id="inUnit" name="inUnit">
            <%
              for (String unidad : unidades) {
                String selected = unidad.equalsIgnoreCase(unidadEntrada) ? " selected" : "";
            %>
            <option value="<%= unidad %>"<%= selected %>><%= capitalizar(unidad) %></option>
            <%
              }
            %>
          </select>
        </div>
        <div class="field-group">
          <label for="outUnit">Unidad destino</label>
          <select id="outUnit" name="outUnit">
            <%
              for (String unidad : unidades) {
                String selected = unidad.equalsIgnoreCase(unidadSalida) ? " selected" : "";
            %>
            <option value="<%= unidad %>"<%= selected %>><%= capitalizar(unidad) %></option>
            <%
              }
            %>
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
        } else if (resultadoAttr != null) {
      %>
        <p class="result">
          <%= valorActual %> <%= unidadEntrada %> = <%= resultadoAttr %> <%= unidadSalida %>
        </p>
      <%
        } else {
      %>
        <p class="result">&nbsp;</p>
      <%
        }
      %>
    </section>

    <footer>
      Servicio de conversion consumido via REST
    </footer>
  </main>
</body>
</html>

