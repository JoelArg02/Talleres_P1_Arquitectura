import { XMLParser } from 'fast-xml-parser';

const endpoint = 'http://192.168.1.15:8094/ec.edu.monster.controlador/MovimientoController.svc';

export async function login(username, password) {
  const soapEnvelope = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
      <soapenv:Header/>
      <soapenv:Body>
        <tem:Login>
          <tem:username>${username}</tem:username>
          <tem:password>${password}</tem:password>
        </tem:Login>
      </soapenv:Body>
    </soapenv:Envelope>`;

  try {
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: {
        'Content-Type': 'text/xml;charset=UTF-8',
        'SOAPAction': '"http://tempuri.org/IMovimientoController/Login"'
      },
      body: soapEnvelope
    });

    const xmlText = await response.text();
    const parser = new XMLParser({ ignoreAttributes: false });
    const result = parser.parse(xmlText);

    const loginResult = result['s:Envelope']?.['s:Body']?.['LoginResponse']?.['LoginResult'];

    return loginResult === 'true' || loginResult === true;
  } catch (error) {
    console.error('Login failed:', error);
    return false;
  }
}
