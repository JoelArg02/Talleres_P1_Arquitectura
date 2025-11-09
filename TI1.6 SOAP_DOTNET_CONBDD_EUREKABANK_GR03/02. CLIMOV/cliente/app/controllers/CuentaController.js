// controllers/CuentaController.js
import { XMLParser } from 'fast-xml-parser';

const endpoint = 'http://192.168.1.15:8094/ec.edu.monster.controlador/MovimientoController.svc';
const soapAction = 'http://tempuri.org/IMovimientoController/ObtenerPorCuenta';

export async function traerMovimientos(cuenta) {
  const body = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
      <soapenv:Header/>
      <soapenv:Body>
        <tem:ObtenerPorCuenta>
          <tem:cuenta>${cuenta}</tem:cuenta>
        </tem:ObtenerPorCuenta>
      </soapenv:Body>
    </soapenv:Envelope>
  `;

  try {
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: {
        'Content-Type': 'text/xml;charset=UTF-8',
        'SOAPAction': soapAction
      },
      body
    });

    const text = await response.text();
    const parser = new XMLParser({
      ignoreAttributes: false,
      attributeNamePrefix: '',
    });

    const parsed = parser.parse(text);
    const movimientos = parsed['s:Envelope']['s:Body']['ObtenerPorCuentaResponse']['ObtenerPorCuentaResult']['a:Movimiento'];

    // Asegura que siempre sea array
    return Array.isArray(movimientos) ? movimientos : [movimientos];
  } catch (error) {
    console.error('Error al consultar movimientos SOAP:', error);
    return [];
  }
}
