/* ===================================
   EUREKA BANK - Scripts JavaScript
   Validaciones y funcionalidades interactivas
   =================================== */

// Validar formulario de login
function validarLogin() {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    
    if (username === '' || password === '') {
        mostrarAlerta('Por favor, ingrese usuario y contraseña.', 'warning');
        return false;
    }
    
    return true;
}

// Validar formulario de depósito
function validarDeposito() {
    const cuenta = document.getElementById('cuenta').value.trim();
    const importe = document.getElementById('importe').value.trim();
    
    if (cuenta === '') {
        mostrarAlerta('Debe ingresar un número de cuenta.', 'warning');
        return false;
    }
    
    if (importe === '' || isNaN(importe) || parseFloat(importe) <= 0) {
        mostrarAlerta('Debe ingresar un importe válido mayor a cero.', 'warning');
        return false;
    }
    
    return confirm(`¿Confirmar depósito de $${parseFloat(importe).toFixed(2)} a la cuenta ${cuenta}?`);
}

// Validar formulario de retiro
function validarRetiro() {
    const cuenta = document.getElementById('cuenta').value.trim();
    const importe = document.getElementById('importe').value.trim();
    
    if (cuenta === '') {
        mostrarAlerta('Debe ingresar un número de cuenta.', 'warning');
        return false;
    }
    
    if (importe === '' || isNaN(importe) || parseFloat(importe) <= 0) {
        mostrarAlerta('Debe ingresar un importe válido mayor a cero.', 'warning');
        return false;
    }
    
    return confirm(`¿Confirmar retiro de $${parseFloat(importe).toFixed(2)} de la cuenta ${cuenta}?`);
}

// Validar formulario de transferencia
function validarTransferencia() {
    const cuentaOrigen = document.getElementById('cuentaOrigen').value.trim();
    const cuentaDestino = document.getElementById('cuentaDestino').value.trim();
    const importe = document.getElementById('importe').value.trim();
    
    if (cuentaOrigen === '' || cuentaDestino === '') {
        mostrarAlerta('Debe ingresar ambas cuentas.', 'warning');
        return false;
    }
    
    if (cuentaOrigen === cuentaDestino) {
        mostrarAlerta('La cuenta origen y destino no pueden ser iguales.', 'error');
        return false;
    }
    
    if (importe === '' || isNaN(importe) || parseFloat(importe) <= 0) {
        mostrarAlerta('Debe ingresar un importe válido mayor a cero.', 'warning');
        return false;
    }
    
    return confirm(`¿Confirmar transferencia de $${parseFloat(importe).toFixed(2)} desde ${cuentaOrigen} hacia ${cuentaDestino}?`);
}

// Validar formulario de movimientos
function validarMovimientos() {
    const cuenta = document.getElementById('cuenta').value.trim();
    
    if (cuenta === '') {
        mostrarAlerta('Debe ingresar un número de cuenta.', 'warning');
        return false;
    }
    
    return true;
}

// Función para mostrar alertas
function mostrarAlerta(mensaje, tipo = 'info') {
    const alertClass = `alert-${tipo}`;
    const alertHTML = `
        <div class="alert ${alertClass}" id="alerta">
            ${mensaje}
        </div>
    `;
    
    // Buscar contenedor de formulario
    const formContainer = document.querySelector('.form-container') || document.querySelector('.table-container');
    if (formContainer) {
        // Eliminar alerta anterior si existe
        const alertaAnterior = document.getElementById('alerta');
        if (alertaAnterior) {
            alertaAnterior.remove();
        }
        
        // Insertar nueva alerta al inicio
        formContainer.insertAdjacentHTML('afterbegin', alertHTML);
        
        // Auto-ocultar después de 5 segundos
        setTimeout(() => {
            const alerta = document.getElementById('alerta');
            if (alerta) {
                alerta.style.transition = 'opacity 0.5s';
                alerta.style.opacity = '0';
                setTimeout(() => alerta.remove(), 500);
            }
        }, 5000);
    }
}

// Formatear números como moneda
function formatearMoneda(valor) {
    return parseFloat(valor).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}

// Limpiar formulario
function limpiarFormulario(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
    }
}

// Confirmar cierre de sesión
function confirmarLogout() {
    return confirm('¿Está seguro que desea cerrar sesión?');
}

// Función para manejar placeholders (si es necesario)
function setupPlaceholders() {
    const inputs = document.querySelectorAll('.form-input');
    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.style.borderBottomColor = '#2980b9';
        });
        
        input.addEventListener('blur', function() {
            this.style.borderBottomColor = '#3498db';
        });
    });
}

// Inicializar cuando cargue el DOM
document.addEventListener('DOMContentLoaded', function() {
    setupPlaceholders();
    
    // Si hay un mensaje en el parámetro URL, mostrarlo
    const urlParams = new URLSearchParams(window.location.search);
    const mensaje = urlParams.get('mensaje');
    const tipo = urlParams.get('tipo') || 'success';
    
    if (mensaje) {
        mostrarAlerta(decodeURIComponent(mensaje), tipo);
        
        // Limpiar los parámetros de la URL sin recargar la página
        const url = new URL(window.location);
        url.searchParams.delete('mensaje');
        url.searchParams.delete('tipo');
        window.history.replaceState({}, document.title, url);
    }
});

// Función para animar entrada de tablas
function animarTabla() {
    const filas = document.querySelectorAll('.data-table tbody tr');
    filas.forEach((fila, index) => {
        setTimeout(() => {
            fila.style.opacity = '0';
            fila.style.transform = 'translateY(10px)';
            fila.style.transition = 'all 0.3s ease';
            
            setTimeout(() => {
                fila.style.opacity = '1';
                fila.style.transform = 'translateY(0)';
            }, 50);
        }, index * 30);
    });
}

// Ejecutar animación de tabla si existe
if (document.querySelector('.data-table')) {
    window.addEventListener('load', animarTabla);
}
