// app/controllers/CuentaController.js

const endpoint = 'http://192.168.1.15:8080/eurekabank/WSEureka?wsdl';

export async function traerMovimientos(cuenta) {
  const xml = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controller.eurekabank.edu.ec/">
      <soapenv:Header/>
      <soapenv:Body>
        <con:traerMovimientos>
          <cuenta>${cuenta}</cuenta>
        </con:traerMovimientos>
      </soapenv:Body>
    </soapenv:Envelope>`;

  const res = await fetch(endpoint, {
    method: 'POST',
    headers: {
      'Content-Type': 'text/xml;charset=UTF-8',
      'SOAPAction': ''
    },
    body: xml
  });

  return await res.text();
}
