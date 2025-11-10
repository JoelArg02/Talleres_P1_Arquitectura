const BASE_URL = 'http://192.168.1.15:8093/ec.edu.monster.controlador/MovimientoController.svc';

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

    const data = await response.json(); // espera una lista de objetos
    return data;
  } catch (error) {
    console.error('Error en traerMovimientos (REST):', error);
    throw error;
  }
}
