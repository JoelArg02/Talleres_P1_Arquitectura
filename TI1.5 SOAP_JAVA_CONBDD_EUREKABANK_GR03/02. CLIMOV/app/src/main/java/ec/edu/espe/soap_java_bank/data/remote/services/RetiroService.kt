package ec.edu.espe.soap_java_bank.data.remote.services

import ec.edu.espe.soap_java_bank.data.remote.base.BaseSoapClient

class RetiroService : BaseSoapClient() {
    
    suspend fun registrarRetiro(cuenta: String, importe: Double): Int {
        return try {
            val soapRequest = """
                <?xml version="1.0" encoding="UTF-8"?>
                <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns2="http://controller.gr03.edu.ec/">
                    <S:Body>
                        <ns2:regRetiro>
                            <arg0>$cuenta</arg0>
                            <arg1>$importe</arg1>
                        </ns2:regRetiro>
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
