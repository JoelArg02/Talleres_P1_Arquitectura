// app/controllers/OperacionController.js

const endpoint = 'http://192.168.1.15:8080/eurekabank/WSEureka?wsdl';

async function enviarSoap(body) {
  const response = await fetch(endpoint, {
    method: 'POST',
    headers: {
      'Content-Type': 'text/xml;charset=UTF-8',
      'SOAPAction': ''
    },
    body
  });
  return await response.text();
}

export async function regDeposito(cuenta, importe) {
  const xml = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controller.eurekabank.edu.ec/">
      <soapenv:Header/>
      <soapenv:Body>
        <con:regDeposito>
          <cuenta>${cuenta}</cuenta>
          <importe>${importe}</importe>
        </con:regDeposito>
      </soapenv:Body>
    </soapenv:Envelope>`;
  return await enviarSoap(xml);
}

export async function regRetiro(cuenta, importe) {
  const xml = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controller.eurekabank.edu.ec/">
      <soapenv:Header/>
      <soapenv:Body>
        <con:regRetiro>
          <cuenta>${cuenta}</cuenta>
          <importe>${importe}</importe>
        </con:regRetiro>
      </soapenv:Body>
    </soapenv:Envelope>`;
  return await enviarSoap(xml);
}

export async function regTransferencia(cuentaOrigen, cuentaDestino, importe) {
  const xml = `
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:con="http://controller.eurekabank.edu.ec/">
      <soapenv:Header/>
      <soapenv:Body>
        <con:regTransferencia>
          <cuentaOrigen>${cuentaOrigen}</cuentaOrigen>
          <cuentaDestino>${cuentaDestino}</cuentaDestino>
          <importe>${importe}</importe>
        </con:regTransferencia>
      </soapenv:Body>
    </soapenv:Envelope>`;
  return await enviarSoap(xml);
}
