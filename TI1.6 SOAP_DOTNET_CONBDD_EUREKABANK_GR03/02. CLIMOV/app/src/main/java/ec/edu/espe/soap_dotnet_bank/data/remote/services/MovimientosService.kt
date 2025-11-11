package ec.edu.espe.soap_dotnet_bank.data.remote.services

import ec.edu.espe.soap_dotnet_bank.data.models.Movimiento
import ec.edu.espe.soap_dotnet_bank.data.remote.base.BaseSoapClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class MovimientosService : BaseSoapClient() {
    
    suspend fun obtenerPorCuenta(cuenta: String): List<Movimiento> = withContext(Dispatchers.IO) {
        val soapEnvelope = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
                <soapenv:Header/>
                <soapenv:Body>
                    <tem:ObtenerPorCuenta>
                        <tem:cuenta>$cuenta</tem:cuenta>
                    </tem:ObtenerPorCuenta>
                </soapenv:Body>
            </soapenv:Envelope>
        """.trimIndent()
        
        val response = executeRequest("ObtenerPorCuenta", soapEnvelope)
        parseMovimientos(response)
    }
    
    private fun parseMovimientos(xmlResponse: String): List<Movimiento> {
        val movimientos = mutableListOf<Movimiento>()
        
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(xmlResponse.reader())
            
            var eventType = parser.eventType
            var currentMovimiento: MutableMap<String, String>? = null
            var currentTag = ""
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        currentTag = parser.name
                        if (currentTag == "Movimiento") {
                            currentMovimiento = mutableMapOf()
                        }
                    }
                    XmlPullParser.TEXT -> {
                        if (currentMovimiento != null && parser.text.isNotBlank()) {
                            currentMovimiento[currentTag] = parser.text.trim()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "Movimiento" && currentMovimiento != null) {
                            movimientos.add(
                                Movimiento(
                                    nroMov = currentMovimiento["NroMov"]?.toIntOrNull() ?: 0,
                                    cuenta = currentMovimiento["Cuenta"] ?: "",
                                    fecha = currentMovimiento["Fecha"] ?: "",
                                    tipo = currentMovimiento["Tipo"] ?: "",
                                    accion = currentMovimiento["Accion"] ?: "",
                                    importe = currentMovimiento["Importe"]?.toDoubleOrNull() ?: 0.0
                                )
                            )
                            currentMovimiento = null
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return movimientos
    }
}
