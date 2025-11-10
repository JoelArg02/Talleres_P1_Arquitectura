import { XMLParser } from 'fast-xml-parser';

const endpoint = 'http://192.168.1.15:8094/ec.edu.monster.controlador/MovimientoController.svc';

/**
 * Envia una solicitud SOAP y retorna el resultado como string.
 * @param {string} xmlBody - Cuerpo del mensaje SOAP.
 * @param {string} soapAction - Acción SOAP (nombre del método).
 * @returns {Promise<string>}
 */
async function enviarSoap(xmlBody, soapAction) {
  try {
    const response = await fetch(endpoint, {
      method: 'POST',
      headers: {
        'Content-Type': 'text/xml;charset=UTF-8',
        'SOAPAction': `"http://tempuri.org/IMovimientoController/${soapAction}"`
      },
      body: xmlBody
    });

    const text = await response.text();
    const parser = new XMLParser({ ignoreAttributes: false });
    const parsed = parser.parse(text);

    const result = parsed['s:Envelope']?.['s:Body']?.[`${soapAction}Response`]?.[`${soapAction}Result`];
    return result || '0';
  } catch (error) {
    console.error(`Error en ${soapAction}:`, error);
    return '0';
  }
}

export async function registrarDeposito(cuenta, importe) {
  const xml = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
      <soapenv:Header/>
      <soapenv:Body>
        <tem:RegistrarDeposito>
          <tem:cuenta>${cuenta}</tem:cuenta>
          <tem:importe>${importe}</tem:importe>
          <tem:codigoAgencia>0001</tem:codigoAgencia>
        </tem:RegistrarDeposito>
      </soapenv:Body>
    </soapenv:Envelope>`;
  return await enviarSoap(xml, 'RegistrarDeposito');
}

export async function registrarRetiro(cuenta, importe) {
  const xml = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
      <soapenv:Header/>
      <soapenv:Body>
        <tem:RegistrarRetiro>
          <tem:cuenta>${cuenta}</tem:cuenta>
          <tem:importe>${importe}</tem:importe>
          <tem:codigoAgencia>0001</tem:codigoAgencia>
        </tem:RegistrarRetiro>
      </soapenv:Body>
    </soapenv:Envelope>`;
  return await enviarSoap(xml, 'RegistrarRetiro');
}

export async function registrarTransferencia(cuentaOrigen, cuentaDestino, importe) {
  const xml = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
      <soapenv:Header/>
      <soapenv:Body>
        <tem:RegistrarTransferencia>
          <tem:cuentaOrigen>${cuentaOrigen}</tem:cuentaOrigen>
          <tem:cuentaDestino>${cuentaDestino}</tem:cuentaDestino>
          <tem:importe>${importe}</tem:importe>
          <tem:codigoAgencia>0001</tem:codigoAgencia>
        </tem:RegistrarTransferencia>
      </soapenv:Body>
    </soapenv:Envelope>`;
  return await enviarSoap(xml, 'RegistrarTransferencia');
}
