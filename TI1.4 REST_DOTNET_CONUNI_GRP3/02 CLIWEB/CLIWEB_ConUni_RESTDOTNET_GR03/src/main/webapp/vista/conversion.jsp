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

  <script>
    const unidadesMasa = [
      {value: 'milligrams', text: 'Miligramo (mg)'},
      {value: 'grams', text: 'Gramo (g)'},
      {value: 'kilograms', text: 'Kilogramo (kg)'},
      {value: 'pounds', text: 'Libra (lb)'},
      {value: 'ounces', text: 'Onza (oz)'},
      {value: 'tons', text: 'Tonelada (t)'}
    ];
    
    const unidadesTemperatura = [
      {value: 'celsius', text: 'Celsius (°C)'},
      {value: 'fahrenheit', text: 'Fahrenheit (°F)'},
      {value: 'kelvin', text: 'Kelvin (K)'},
      {value: 'rankine', text: 'Rankine (°R)'}
    ];
    
    const unidadesLongitud = [
      {value: 'millimeters', text: 'Milímetro (mm)'},
      {value: 'centimeters', text: 'Centímetro (cm)'},
      {value: 'meters', text: 'Metro (m)'},
      {value: 'kilometers', text: 'Kilómetro (km)'},
      {value: 'inches', text: 'Pulgada (in)'},
      {value: 'feet', text: 'Pie (ft)'}
    ];
    
    function actualizarUnidades() {
      const tipo = document.getElementById('tipoConversion').value;
      const selectOrigen = document.getElementById('unidadOrigen');
      const selectDestino = document.getElementById('unidadDestino');
      
      let unidades;
      if (tipo === 'masa') {
        unidades = unidadesMasa;
      } else if (tipo === 'temperatura') {
        unidades = unidadesTemperatura;
      } else {
        unidades = unidadesLongitud;
      }
      
      selectOrigen.innerHTML = '';
      selectDestino.innerHTML = '';
      
      unidades.forEach(u => {
        selectOrigen.add(new Option(u.text, u.value));
        selectDestino.add(new Option(u.text, u.value));
      });
      
      if (unidades.length > 1) {
        selectDestino.selectedIndex = 1;
      }
      
      document.getElementById('resultados').style.display = 'none';
    }
    
    function formatearNumero(value) {
      const absValue = Math.abs(value);
      
      if ((absValue > 0 && absValue < 0.001) || absValue >= 1000000) {
        return value.toExponential(6);
      } else if (absValue >= 1000) {
        return value.toLocaleString('en-US', {minimumFractionDigits: 3, maximumFractionDigits: 3});
      } else {
        return value.toFixed(3);
      }
    }
    
    async function convertir(event) {
      event.preventDefault();
      
      const valor = parseFloat(document.getElementById('valor').value);
      const unidadOrigen = document.getElementById('unidadOrigen').value;
      const unidadDestino = document.getElementById('unidadDestino').value;
      const tipo = document.getElementById('tipoConversion').value;
      
      if (isNaN(valor)) {
        alert('Por favor ingrese un valor valido');
        return false;
      }
      
      try {
        const response = await fetch('${pageContext.request.contextPath}/convertir', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          body: 'valor=' + valor + '&inUnit=' + unidadOrigen + '&outUnit=' + unidadDestino + '&tipo=' + tipo
        });
        
        const text = await response.text();
        const parser = new DOMParser();
        const doc = parser.parseFromString(text, 'text/html');
        
        const originalValue = parseFloat(doc.getElementById('hiddenOriginalValue')?.value);
        const convertedValue = parseFloat(doc.getElementById('hiddenConvertedValue')?.value);
        const fromUnit = doc.getElementById('hiddenFromUnit')?.value;
        const toUnit = doc.getElementById('hiddenToUnit')?.value;
        
        if (!isNaN(originalValue) && !isNaN(convertedValue)) {
          const unidadOrigenText = document.getElementById('unidadOrigen').options[document.getElementById('unidadOrigen').selectedIndex].text;
          const unidadDestinoText = document.getElementById('unidadDestino').options[document.getElementById('unidadDestino').selectedIndex].text;
          
          document.getElementById('resultLabel').textContent = formatearNumero(originalValue) + ' ' + unidadOrigenText + ' =';
          document.getElementById('resultValue').textContent = formatearNumero(convertedValue) + ' ' + unidadDestinoText;
          document.getElementById('resultados').style.display = 'block';
        } else {
          document.getElementById('resultLabel').textContent = 'Error:';
          document.getElementById('resultValue').textContent = 'No se pudo realizar la conversion';
          document.getElementById('resultados').style.display = 'block';
        }
      } catch (error) {
        console.error('Error:', error);
        document.getElementById('resultLabel').textContent = 'Error:';
        document.getElementById('resultValue').textContent = 'No se pudo conectar al servidor';
        document.getElementById('resultados').style.display = 'block';
      }
      
      return false;
    }
    
    window.onload = function() {
      const hiddenOriginal = document.getElementById('hiddenOriginalValue');
      const hiddenConverted = document.getElementById('hiddenConvertedValue');
      
      if (hiddenOriginal && hiddenConverted) {
        const originalValue = parseFloat(hiddenOriginal.value);
        const convertedValue = parseFloat(hiddenConverted.value);
        const fromUnit = document.getElementById('hiddenFromUnit').value;
        const toUnit = document.getElementById('hiddenToUnit').value;
        
        if (!isNaN(originalValue) && !isNaN(convertedValue)) {
          document.getElementById('resultLabel').textContent = formatearNumero(originalValue) + ' ' + fromUnit + ' =';
          document.getElementById('resultValue').textContent = formatearNumero(convertedValue) + ' ' + toUnit;
        }
      }
    };
  </script>
</body>
</html>
