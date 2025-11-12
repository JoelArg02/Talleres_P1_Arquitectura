package ec.edu.espe.soap_dotnet_bank.data.remote.services

import ec.edu.espe.soap_dotnet_bank.data.models.OperacionResultData
import ec.edu.espe.soap_dotnet_bank.data.remote.base.BaseSoapClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class DepositoService : BaseSoapClient() {
    
    suspend fun registrarDeposito(cuenta: String, importe: Double): OperacionResultData = withContext(Dispatchers.IO) {
        val soapEnvelope = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
                <soapenv:Header/>
                <soapenv:Body>
                    <tem:RegistrarDeposito>
                        <tem:cuenta>$cuenta</tem:cuenta>
                        <tem:importe>$importe</tem:importe>
                    </tem:RegistrarDeposito>
                </soapenv:Body>
            </soapenv:Envelope>
        """.trimIndent()
        
        val response = executeRequest("RegistrarDeposito", soapEnvelope)
        parseOperacionResult(response)
    }
    
    private fun parseOperacionResult(xmlResponse: String): OperacionResultData {
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(xmlResponse.reader())
            
            var eventType = parser.eventType
            var resultado = 0
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.name == "RegistrarDepositoResult") {
                            parser.next()
                            if (parser.eventType == XmlPullParser.TEXT) {
                                resultado = parser.text.toIntOrNull() ?: 0
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
            
            return OperacionResultData(
                resultado = resultado,
                mensaje = if (resultado > 0) "Depósito registrado exitosamente" else "Error al registrar depósito"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return OperacionResultData(0, "Error: ${e.message}")
        }
    }
}
