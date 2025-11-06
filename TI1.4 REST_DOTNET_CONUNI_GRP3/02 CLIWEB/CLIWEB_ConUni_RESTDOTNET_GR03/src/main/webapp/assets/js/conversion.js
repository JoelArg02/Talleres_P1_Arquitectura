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

function actualizarUnidades() {
  const tipo = document.getElementById('tipoConversion').value;
  const selectOrigen = document.getElementById('unidadOrigen');
  const selectDestino = document.getElementById('unidadDestino');
  
  const unidades = UnitsData[tipo] || UnitsData.masa;
  
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

async function convertir(event) {
  event.preventDefault();
  
  const valor = parseFloat(document.getElementById('valor').value);
  const unidadOrigen = document.getElementById('unidadOrigen').value;
  const unidadDestino = document.getElementById('unidadDestino').value;
  const tipo = document.getElementById('tipoConversion').value;
  
  console.log('=== Iniciando conversion ===');
  console.log('Valor:', valor);
  console.log('Unidad origen:', unidadOrigen);
  console.log('Unidad destino:', unidadDestino);
  console.log('Tipo:', tipo);
  
  if (isNaN(valor)) {
    console.error('Valor invalido:', document.getElementById('valor').value);
    mostrarError('Por favor ingrese un valor valido');
    return false;
  }
  
  const contextPath = document.querySelector('meta[name="context-path"]')?.content || '';
  const url = contextPath + '/convertir';
  const body = `valor=${valor}&inUnit=${unidadOrigen}&outUnit=${unidadDestino}&tipo=${tipo}`;
  
  console.log('URL:', url);
  console.log('Body:', body);
  
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: body
    });
    
    console.log('Response status:', response.status);
    console.log('Response statusText:', response.statusText);
    console.log('Response ok:', response.ok);
    
    if (!response.ok) {
      const errorText = await response.text();
      console.error('Error del servidor - Status:', response.status);
      console.error('Error del servidor - Response:', errorText);
      mostrarError(`Error del servidor: ${response.status} - ${response.statusText}`);
      return false;
    }
    
    const text = await response.text();
    console.log('Respuesta recibida (primeros 500 chars):', text.substring(0, 500));
    
    const parser = new DOMParser();
    const doc = parser.parseFromString(text, 'text/html');
    
    const errorMsg = doc.querySelector('.error-message')?.textContent;
    if (errorMsg) {
      console.error('Error en respuesta JSP:', errorMsg);
      mostrarError(errorMsg);
      return false;
    }
    
    const originalValue = parseFloat(doc.getElementById('hiddenOriginalValue')?.value);
    const convertedValue = parseFloat(doc.getElementById('hiddenConvertedValue')?.value);
    
    console.log('Original value parseado:', originalValue);
    console.log('Converted value parseado:', convertedValue);
    
    if (!isNaN(originalValue) && !isNaN(convertedValue)) {
      console.log('Conversion exitosa');
      mostrarResultado(originalValue, convertedValue);
    } else {
      console.error('Valores invalidos en respuesta');
      console.error('hiddenOriginalValue element:', doc.getElementById('hiddenOriginalValue'));
      console.error('hiddenConvertedValue element:', doc.getElementById('hiddenConvertedValue'));
      mostrarError('Respuesta invalida del servidor. Verifique que el servicio este activo en http://localhost:5003/api');
    }
  } catch (error) {
    console.error('=== Error en conversion ===');
    console.error('Tipo:', error.name);
    console.error('Mensaje:', error.message);
    console.error('Stack:', error.stack);
    mostrarError(`Error de conexion: ${error.message}. Verifique que el servicio REST este activo en http://localhost:5003/api`);
  }
  
  return false;
}

function mostrarResultado(originalValue, convertedValue) {
  const resultadosDiv = document.getElementById('resultados');
  let resultLabel = document.getElementById('resultLabel');
  let resultValue = document.getElementById('resultValue');
  
  if (!resultLabel || !resultValue) {
    const resultSection = resultadosDiv.querySelector('.result-section');
    if (resultSection) {
      resultSection.innerHTML = '<div class="result-row"><span class="result-label" id="resultLabel">-</span><span class="result-value" id="resultValue">-</span></div>';
      resultLabel = document.getElementById('resultLabel');
      resultValue = document.getElementById('resultValue');
    }
  }
  
  if (resultLabel && resultValue) {
    const unidadOrigenText = document.getElementById('unidadOrigen').options[document.getElementById('unidadOrigen').selectedIndex].text;
    const unidadDestinoText = document.getElementById('unidadDestino').options[document.getElementById('unidadDestino').selectedIndex].text;
    
    resultLabel.textContent = formatearNumero(originalValue) + ' ' + unidadOrigenText + ' =';
    resultValue.textContent = formatearNumero(convertedValue) + ' ' + unidadDestinoText;
    resultValue.classList.remove('error-message');
    resultadosDiv.style.display = 'block';
  }
}

function mostrarError(mensaje) {
  const resultadosDiv = document.getElementById('resultados');
  let resultLabel = document.getElementById('resultLabel');
  let resultValue = document.getElementById('resultValue');
  
  if (!resultLabel || !resultValue) {
    const resultSection = resultadosDiv.querySelector('.result-section');
    if (resultSection) {
      resultSection.innerHTML = '<div class="result-row"><span class="result-label" id="resultLabel">-</span><span class="result-value error-message" id="resultValue">-</span></div>';
      resultLabel = document.getElementById('resultLabel');
      resultValue = document.getElementById('resultValue');
    }
  }
  
  if (resultLabel && resultValue) {
    resultLabel.textContent = 'Error:';
    resultValue.textContent = mensaje;
    resultValue.classList.add('error-message');
    resultadosDiv.style.display = 'block';
  }
}

window.onload = function() {
  const hiddenOriginal = document.getElementById('hiddenOriginalValue');
  const hiddenConverted = document.getElementById('hiddenConvertedValue');
  
  if (hiddenOriginal && hiddenConverted) {
    const originalValue = parseFloat(hiddenOriginal.value);
    const convertedValue = parseFloat(hiddenConverted.value);
    const fromUnit = document.getElementById('hiddenFromUnit')?.value;
    const toUnit = document.getElementById('hiddenToUnit')?.value;
    
    if (!isNaN(originalValue) && !isNaN(convertedValue)) {
      document.getElementById('resultLabel').textContent = formatearNumero(originalValue) + ' ' + fromUnit + ' =';
      document.getElementById('resultValue').textContent = formatearNumero(convertedValue) + ' ' + toUnit;
    }
  }
};
