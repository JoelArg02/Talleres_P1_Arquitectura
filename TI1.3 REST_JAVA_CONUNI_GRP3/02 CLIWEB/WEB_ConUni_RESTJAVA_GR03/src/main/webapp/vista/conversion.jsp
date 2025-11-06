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
      <h2>Selecciona el tipo y realiza la conversion</h2>
      <form class="conversion-form" id="conversionForm" onsubmit="return convertir(event)">
        <div class="field-group">
          <label for="tipoConversion">Tipo de Conversion</label>
          <select id="tipoConversion" name="tipoConversion" onchange="actualizarUnidades()">
            <option value="masa">Masa</option>
            <option value="temperatura">Temperatura</option>
            <option value="longitud">Longitud</option>
          </select>
        </div>
        <div class="field-group">
          <label for="valor">Valor</label>
          <input id="valor" type="number" step="any" name="valor" placeholder="Ingrese el valor" required>
        </div>
        <div class="field-group">
          <label for="unidadOrigen">De</label>
          <select id="unidadOrigen" name="unidadOrigen">
            <option value="mg">Miligramo (mg)</option>
            <option value="g">Gramo (g)</option>
            <option value="kg">Kilogramo (kg)</option>
            <option value="lb">Libra (lb)</option>
            <option value="oz">Onza (oz)</option>
            <option value="t">Tonelada (t)</option>
          </select>
        </div>
        <div class="field-group">
          <label for="unidadDestino">A</label>
          <select id="unidadDestino" name="unidadDestino">
            <option value="mg">Miligramo (mg)</option>
            <option value="g">Gramo (g)</option>
            <option value="kg">Kilogramo (kg)</option>
            <option value="lb">Libra (lb)</option>
            <option value="oz">Onza (oz)</option>
            <option value="t">Tonelada (t)</option>
          </select>
        </div>
        <div class="actions">
          <button type="submit">Convertir</button>
        </div>
      </form>

      <div id="resultados" class="results-card" style="display: none;">
        <div class="results-header">
          <span>Resultado de Conversion</span>
          <div class="results-bar"></div>
        </div>
        
        <div class="result-section">
          <div class="result-row">
            <span class="result-label" id="resultLabel">Resultado:</span>
            <span class="result-value" id="resultValue">-</span>
          </div>
        </div>
      </div>
    </section>

  </main>

  <script>
    const unidadesMasa = [
      {value: 'mg', text: 'Miligramo (mg)'},
      {value: 'g', text: 'Gramo (g)'},
      {value: 'kg', text: 'Kilogramo (kg)'},
      {value: 'lb', text: 'Libra (lb)'},
      {value: 'oz', text: 'Onza (oz)'},
      {value: 't', text: 'Tonelada (t)'}
    ];
    
    const unidadesTemperatura = [
      {value: 'c', text: 'Celsius (°C)'},
      {value: 'f', text: 'Fahrenheit (°F)'},
      {value: 'k', text: 'Kelvin (K)'},
      {value: 'r', text: 'Rankine (°R)'}
    ];
    
    const unidadesLongitud = [
      {value: 'mm', text: 'Milímetro (mm)'},
      {value: 'cm', text: 'Centímetro (cm)'},
      {value: 'm', text: 'Metro (m)'},
      {value: 'km', text: 'Kilómetro (km)'},
      {value: 'in', text: 'Pulgada (in)'},
      {value: 'ft', text: 'Pie (ft)'}
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
      
      const tipo = document.getElementById('tipoConversion').value;
      const valor = parseFloat(document.getElementById('valor').value);
      const unidadOrigen = document.getElementById('unidadOrigen').value;
      const unidadDestino = document.getElementById('unidadDestino').value;
      
      if (isNaN(valor)) {
        alert('Por favor ingrese un valor válido');
        return false;
      }
      
      try {
        const requestBody = {
          type: tipo,
          value: valor,
          inUnit: unidadOrigen,
          outUnit: unidadDestino
        };
        
        const response = await fetch('http://localhost:8080/WS_ConUni_RESTJAVA_GR03/api/ConUni/convertUnit', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(requestBody)
        });
        
        if (!response.ok) {
          throw new Error('Error en la respuesta del servidor');
        }
        
        const resultado = await response.json();
        
        const unidadOrigenText = document.getElementById('unidadOrigen').options[document.getElementById('unidadOrigen').selectedIndex].text;
        const unidadDestinoText = document.getElementById('unidadDestino').options[document.getElementById('unidadDestino').selectedIndex].text;
        
        document.getElementById('resultLabel').textContent = formatearNumero(valor) + ' ' + unidadOrigenText + ' =';
        document.getElementById('resultValue').textContent = formatearNumero(resultado) + ' ' + unidadDestinoText;
        document.getElementById('resultados').style.display = 'block';
        
      } catch (error) {
        console.error('Error:', error);
        document.getElementById('resultLabel').textContent = 'Error:';
        document.getElementById('resultValue').textContent = 'No se pudo conectar al servidor';
        document.getElementById('resultados').style.display = 'block';
      }
      
      return false;
    }
    
    window.onload = function() {
      actualizarUnidades();
    };
  </script>
</body>
</html>

