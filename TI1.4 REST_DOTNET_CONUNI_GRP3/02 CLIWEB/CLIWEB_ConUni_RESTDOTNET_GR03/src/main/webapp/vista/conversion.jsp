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
    unidades = UnitOption.masa();
  }

  String tipoConversion = (String) request.getAttribute("tipoConversion");
  if (tipoConversion == null || tipoConversion.isBlank()) {
    tipoConversion = "masa";
  }

  String valorActual = request.getAttribute("valor") != null ? request.getAttribute("valor").toString() : "";
  String unidadEntrada = request.getAttribute("inUnit") != null ? request.getAttribute("inUnit").toString() : "";
  String unidadSalida = request.getAttribute("outUnit") != null ? request.getAttribute("outUnit").toString() : "";
  String errorConversion = (String) request.getAttribute("errorConversion");
  ConversionResult conversion = (ConversionResult) request.getAttribute("resultado");
  
  if (unidadEntrada == null || unidadEntrada.isBlank()) {
    unidadEntrada = unidades.isEmpty() ? "" : unidades.get(0).getSlug();
  }
  if (unidadSalida == null || unidadSalida.isBlank()) {
    unidadSalida = unidades.size() > 1 ? unidades.get(1).getSlug() : unidadEntrada;
  }
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="context-path" content="${pageContext.request.contextPath}">
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
      <h2>Selecciona el tipo y realiza la conversion</h2>
      <form class="conversion-form" id="conversionForm" onsubmit="return convertir(event)">
        <div class="field-group">
          <label for="tipoConversion">Tipo de Conversion</label>
          <select id="tipoConversion" name="tipo" onchange="actualizarUnidades()">
            <option value="masa" <%= "masa".equals(tipoConversion) ? "selected" : "" %>>Masa</option>
            <option value="temperatura" <%= "temperatura".equals(tipoConversion) ? "selected" : "" %>>Temperatura</option>
            <option value="longitud" <%= "longitud".equals(tipoConversion) ? "selected" : "" %>>Longitud</option>
          </select>
        </div>
        <div class="field-group">
          <label for="valor">Valor</label>
          <input id="valor" type="number" step="any" name="valor" placeholder="Ingrese el valor" value="<%= valorActual %>" required>
        </div>
        <div class="field-group">
          <label for="unidadOrigen">De</label>
          <select id="unidadOrigen" name="inUnit">
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
          <label for="unidadDestino">A</label>
          <select id="unidadDestino" name="outUnit">
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

      <div id="resultados" class="results-card" style="display: <%= ((errorConversion != null && !errorConversion.isBlank()) || conversion != null) ? "block" : "none" %>;">
        <div class="results-header">
          <span>Resultado de Conversion</span>
          <div class="results-bar"></div>
        </div>
        
        <div class="result-section">
          <%
            if (errorConversion != null && !errorConversion.isBlank()) {
          %>
            <div class="result-row">
              <span class="result-label">Error:</span>
              <span class="result-value error-message"><%= errorConversion %></span>
            </div>
          <%
            } else if (conversion != null) {
          %>
            <div class="result-row">
              <span class="result-label" id="resultLabel">-</span>
              <span class="result-value" id="resultValue">-</span>
            </div>
            <input type="hidden" id="hiddenOriginalValue" value="<%= conversion.originalValue() %>">
            <input type="hidden" id="hiddenConvertedValue" value="<%= conversion.convertedValue() %>">
            <input type="hidden" id="hiddenFromUnit" value="<%= conversion.fromUnit() %>">
            <input type="hidden" id="hiddenToUnit" value="<%= conversion.toUnit() %>">
          <%
            }
          %>
        </div>
      </div>
    </section>

    <footer>
      Servicio REST .NET alojado en http://localhost:5003/api
    </footer>
  </main>

  <script src="${pageContext.request.contextPath}/assets/js/units-data.js"></script>
  <script src="${pageContext.request.contextPath}/assets/js/conversion.js"></script>
</body>
</html>
