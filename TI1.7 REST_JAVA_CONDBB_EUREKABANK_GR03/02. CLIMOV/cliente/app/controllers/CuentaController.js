// app/controllers/CuentaController.js

const BASE_URL = 'http://192.168.1.15:8080/eurekabank/api/eureka';

/**
 * Trae la lista de movimientos para una cuenta específica.
 * @param {string} cuenta - Número de cuenta
 * @returns {Promise<Array>} - Lista de movimientos
 */
export async function traerMovimientos(cuenta) {
  try {
    const response = await fetch(`${BASE_URL}/movimientos/${cuenta}`);

    if (!response.ok) {
      throw new Error('Error al consultar movimientos');
    }

    const data = await response.json();
    return data; // ya es un array de objetos JSON
  } catch (error) {
    console.error('Error en traerMovimientos (REST):', error);
    throw error;
  }
}
