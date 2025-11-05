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
      <h2>Ingresa los valores para convertir</h2>
      <form class="conversion-form" id="conversionForm" onsubmit="return convertir(event)">
        <div class="field-group">
          <label for="masa">Masa (kg)</label>
          <input id="masa" type="number" step="any" name="masa" placeholder="Ingrese kilogramos">
        </div>
        <div class="field-group">
          <label for="temperatura">Temperatura (°C)</label>
          <input id="temperatura" type="number" step="any" name="temperatura" placeholder="Ingrese grados Celsius">
        </div>
        <div class="field-group">
          <label for="longitud2">Longitud 2 (decimal)</label>
          <input id="longitud2" type="number" step="any" name="longitud2" placeholder="Ingrese grados decimales">
        </div>
        <div class="actions">
          <button type="submit">Convertir</button>
        </div>
      </form>

      <div id="resultados" class="results-card" style="display: none;">
        <div class="results-header">
          <span>Resultados de Conversión</span>
          <div class="results-bar"></div>
        </div>
        
        <div id="masaResults" class="result-section" style="display: none;">
          <h3>MASA</h3>
          <div class="result-row">
            <span class="result-label">Kilogramos:</span>
            <span class="result-value" id="kg">-</span>
          </div>
          <div class="result-row">
            <span class="result-label">Libras:</span>
            <span class="result-value" id="lb">-</span>
          </div>
          <div class="result-row">
            <span class="result-label">Gramos:</span>
            <span class="result-value" id="g">-</span>
          </div>
        </div>

        <div id="tempResults" class="result-section" style="display: none;">
          <h3>TEMPERATURA</h3>
          <div class="result-row">
            <span class="result-label">Celsius:</span>
            <span class="result-value" id="celsius">-</span>
          </div>
          <div class="result-row">
            <span class="result-label">Fahrenheit:</span>
            <span class="result-value" id="fahrenheit">-</span>
          </div>
          <div class="result-row">
            <span class="result-label">Kelvin:</span>
            <span class="result-value" id="kelvin">-</span>
          </div>
        </div>

        <div id="long2Results" class="result-section" style="display: none;">
          <h3>COORDENADAS</h3>
          <div class="result-row">
            <span class="result-label">Long2 Decimal:</span>
            <span class="result-value" id="decimal">-</span>
          </div>
          <div class="result-row">
            <span class="result-label">Long2 Radianes:</span>
            <span class="result-value" id="radianes">-</span>
          </div>
        </div>
      </div>
    </section>

    <footer>
      Conversiones calculadas en el cliente
    </footer>
  </main>

  <script>
    function convertir(event) {
      event.preventDefault();
      
      const masa = document.getElementById('masa').value;
      const temperatura = document.getElementById('temperatura').value;
      const longitud2 = document.getElementById('longitud2').value;
      
      let hasResults = false;
      
      // Convertir masa
      if (masa && masa.trim() !== '') {
        const kg = parseFloat(masa);
        const lb = kg * 2.20462;
        const g = kg * 1000;
        
        document.getElementById('kg').textContent = kg.toFixed(4);
        document.getElementById('lb').textContent = lb.toFixed(4);
        document.getElementById('g').textContent = g.toFixed(4);
        document.getElementById('masaResults').style.display = 'block';
        hasResults = true;
      } else {
        document.getElementById('masaResults').style.display = 'none';
      }
      
      // Convertir temperatura
      if (temperatura && temperatura.trim() !== '') {
        const celsius = parseFloat(temperatura);
        const fahrenheit = (celsius * 9/5) + 32;
        const kelvin = celsius + 273.15;
        
        document.getElementById('celsius').textContent = celsius.toFixed(4);
        document.getElementById('fahrenheit').textContent = fahrenheit.toFixed(4);
        document.getElementById('kelvin').textContent = kelvin.toFixed(4);
        document.getElementById('tempResults').style.display = 'block';
        hasResults = true;
      } else {
        document.getElementById('tempResults').style.display = 'none';
      }
      
      // Convertir longitud2
      if (longitud2 && longitud2.trim() !== '') {
        const decimal = parseFloat(longitud2);
        const radianes = decimal * (Math.PI / 180);
        
        document.getElementById('decimal').textContent = decimal.toFixed(4);
        document.getElementById('radianes').textContent = radianes.toFixed(6);
        document.getElementById('long2Results').style.display = 'block';
        hasResults = true;
      } else {
        document.getElementById('long2Results').style.display = 'none';
      }
      
      // Mostrar u ocultar el contenedor de resultados
      document.getElementById('resultados').style.display = hasResults ? 'block' : 'none';
      
      return false;
    }
  </script>
</body>
</html>
