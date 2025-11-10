const BASE_URL = 'http://192.168.1.15:8080/eurekabank/api/eureka';

/**
 * Realiza una solicitud POST y devuelve el texto de respuesta.
 * Lanza error si el servidor responde con error.
 */
async function postConRespuesta(url) {
  try {
    const response = await fetch(url, { method: 'POST' });
    const text = await response.text();

    if (!response.ok) {
      throw new Error(text || 'Error en la operación');
    }

    return text;
  } catch (error) {
    console.error(`Error en ${url}:`, error);
    throw error;
  }
}

/**
 * Registra un depósito usando REST.
 * @param {string} cuenta - Número de cuenta
 * @param {string|number} importe - Monto a depositar
 * @returns {Promise<string>} - Mensaje del servidor
 */
export async function regDeposito(cuenta, importe) {
  const url = `${BASE_URL}/deposito?cuenta=${cuenta}&importe=${importe}`;
  return await postConRespuesta(url);
}

/**
 * Registra un retiro usando REST.
 * @param {string} cuenta - Número de cuenta
 * @param {string|number} importe - Monto a retirar
 * @returns {Promise<string>} - Mensaje del servidor
 */
export async function regRetiro(cuenta, importe) {
  const url = `${BASE_URL}/retiro?cuenta=${cuenta}&importe=${importe}`;
  return await postConRespuesta(url);
}

/**
 * Registra una transferencia usando REST.
 * @param {string} cuentaOrigen - Cuenta origen
 * @param {string} cuentaDestino - Cuenta destino
 * @param {string|number} importe - Monto a transferir
 * @returns {Promise<string>} - Mensaje del servidor
 */
export async function regTransferencia(cuentaOrigen, cuentaDestino, importe) {
  const url = `${BASE_URL}/transferencia?cuentaOrigen=${cuentaOrigen}&cuentaDestino=${cuentaDestino}&importe=${importe}`;
  return await postConRespuesta(url);
}
