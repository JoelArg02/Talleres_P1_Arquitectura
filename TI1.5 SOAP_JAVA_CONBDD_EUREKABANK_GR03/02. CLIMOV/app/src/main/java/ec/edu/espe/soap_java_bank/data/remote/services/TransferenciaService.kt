package ec.edu.espe.soap_java_bank.data.remote.services

import ec.edu.espe.soap_java_bank.data.remote.base.BaseSoapClient

class TransferenciaService : BaseSoapClient() {

    suspend fun registrarTransferencia(cuentaOrigen: String, cuentaDestino: String, importe: Double): Int {
        return try {
            val soapRequest = """
                <?xml version="1.0" encoding="UTF-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:con="http://controller.gr03.edu.ec/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <con:regTransferencia>
                         <cuentaOrigen>$cuentaOrigen</cuentaOrigen>
                         <cuentaDestino>$cuentaDestino</cuentaDestino>
                         <importe>$importe</importe>
                      </con:regTransferencia>
                   </soapenv:Body>
                </soapenv:Envelope>
            """.trimIndent()

            val response = executeRequest(soapRequest)
            if (response.contains("<estado>1</estado>")) 1 else 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}
