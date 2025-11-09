// app/controllers/AuthController.js

export async function login(username, password) {
  const soapEnvelope = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controller.eurekabank.edu.ec/">
      <soapenv:Header/>
      <soapenv:Body>
        <con:login>
          <username>${username}</username>
          <password>${password}</password>
        </con:login>
      </soapenv:Body>
    </soapenv:Envelope>`;

  try {
    const response = await fetch('http://192.168.1.15:8080/eurekabank/WSEureka?wsdl', {
      method: 'POST',
      headers: {
        'Content-Type': 'text/xml;charset=UTF-8',
        'SOAPAction': '' // SOAPAction puede estar vacío para muchos servicios
      },
      body: soapEnvelope
    });

    const text = await response.text();
    console.log('[SOAP RESPONSE]', text);

    // Aquí deberías parsear la respuesta (XML) si quieres extraer datos
    return text;
  } catch (error) {
    console.error('Login failed:', error);
    throw error;
  }
}
