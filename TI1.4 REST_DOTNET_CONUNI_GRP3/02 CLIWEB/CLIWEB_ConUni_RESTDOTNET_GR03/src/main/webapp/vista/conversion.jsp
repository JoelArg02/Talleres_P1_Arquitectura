<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.dto.UnitOption" %>
<%@ page import="ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.dto.ConversionResult" %>
<%
  String usuario = (String) session.getAttribute("usuario");
  if (usuario == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
  }

  List<UnitOption> unidades = (List<UnitOption>) request.getAttribute("units");
  if (unidades == null || unidades.isEmpty()) {
    unidades = UnitOption.longitud();
  }

  String valorActual = request.getAttribute("valor") != null ? request.getAttribute("valor").toString() : "";
  String unidadEntrada = request.getAttribute("inUnit") != null ? request.getAttribute("inUnit").toString() : "meters";
  String unidadSalida = request.getAttribute("outUnit") != null ? request.getAttribute("outUnit").toString() : "kilometers";
  String errorConversion = (String) request.getAttribute("errorConversion");
  ConversionResult conversion = (ConversionResult) request.getAttribute("resultado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Conversion - ConUni REST DOTNET</title>
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
      <h2>Consumiendo backend REST en .NET</h2>
      <form class="conversion-form" method="post" action="${pageContext.request.contextPath}/convertir">
        <div class="field-group">
          <label for="valor">Valor</label>
          <input id="valor" type="number" step="any" name="valor" value="<%= valorActual %>" required>
        </div>
        <div class="field-group">
          <label for="inUnit">Unidad origen</label>
          <select id="inUnit" name="inUnit">
            <%
              for (UnitOption unidad : unidades) {
                String selected = unidad.getSlug().equalsIgnoreCase(unidadEntrada) ? " selected" : "";
            %>
            <option value="<%= unidad.getSlug() %>"<%= selected %>><%= unidad.getEtiqueta() %></option>
            <%
              }
            %>
          </select>
        </div>
        <div class="field-group">
          <label for="outUnit">Unidad destino</label>
          <select id="outUnit" name="outUnit">
            <%
              for (UnitOption unidad : unidades) {
                String selected = unidad.getSlug().equalsIgnoreCase(unidadSalida) ? " selected" : "";
            %>
            <option value="<%= unidad.getSlug() %>"<%= selected %>><%= unidad.getEtiqueta() %></option>
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
        } else if (conversion != null) {
      %>
        <p class="result">
          <%= conversion.originalValue() %> <%= conversion.fromUnit() %>
          = <%= String.format(java.util.Locale.US, "%.4f", conversion.convertedValue()) %> <%= conversion.toUnit() %>
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
      Servicio .NET alojado en http://localhost:5000/api
    </footer>
  </main>
</body>
</html>
