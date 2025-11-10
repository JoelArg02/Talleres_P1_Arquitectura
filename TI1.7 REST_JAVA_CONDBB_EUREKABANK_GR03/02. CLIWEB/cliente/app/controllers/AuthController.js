const BASE_URL = 'http://192.168.1.15:8080/eurekabank/api/eureka';

export async function login(username, password) {
  try {
    const response = await fetch(`${BASE_URL}/login?username=${username}&password=${password}`, {
      method: 'POST'
    });

    if (response.ok) {
      const result = await response.json(); // true o false
      return result === true;
    } else {
      return false;
    }
  } catch (error) {
    console.error('Error en login REST:', error);
    throw error;
  }
}
