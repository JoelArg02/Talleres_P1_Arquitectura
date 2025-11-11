package ec.edu.espe.soap_java_bank.data.remote.services

import ec.edu.espe.soap_java_bank.data.remote.base.BaseSoapClient

class TransferenciaService : BaseSoapClient() {
    
    suspend fun registrarTransferencia(cuentaOrigen: String, cuentaDestino: String, importe: Double): Int {
        return try {
            val soapRequest = """
                <?xml version="1.0" encoding="UTF-8"?>
                <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns2="http://controller.gr03.edu.ec/">
                    <S:Body>
                        <ns2:regTransferencia>
                            <arg0>$cuentaOrigen</arg0>
                            <arg1>$cuentaDestino</arg1>
                            <arg2>$importe</arg2>
                        </ns2:regTransferencia>
                    </S:Body>
                </S:Envelope>
            """.trimIndent()
            
            val response = executeRequest(soapRequest)
            if (response.contains("<return>1</return>")) 1 else 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}
