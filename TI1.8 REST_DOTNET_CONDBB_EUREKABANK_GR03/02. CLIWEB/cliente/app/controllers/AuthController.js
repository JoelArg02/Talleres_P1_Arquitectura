const BASE_URL = 'http://192.168.1.15:8093/ec.edu.monster.controlador/MovimientoController.svc';

/**
 * Inicia sesión con usuario y contraseña
 * @param {string} username
 * @param {string} password
 * @returns {Promise<boolean>} true si el login es correcto, false si no
 */
export async function login(username, password) {
  try {
    const url = `${BASE_URL}/login?username=${encodeURIComponent(username.trim())}&password=${encodeURIComponent(password)}`;
    
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      return false;
    }

    const text = await response.text();
    return text.trim() === 'true';
  } catch (error) {
    console.error('Error en login REST:', error);
    throw error;
  }
}
