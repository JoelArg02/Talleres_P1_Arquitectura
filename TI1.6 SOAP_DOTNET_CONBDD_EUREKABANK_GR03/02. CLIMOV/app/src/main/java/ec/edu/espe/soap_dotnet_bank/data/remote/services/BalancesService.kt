package ec.edu.espe.soap_dotnet_bank.data.remote.services

import ec.edu.espe.soap_dotnet_bank.data.models.Cuenta
import ec.edu.espe.soap_dotnet_bank.data.remote.base.BaseSoapClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class BalancesService : BaseSoapClient() {
    
    suspend fun traerBalances(): List<Cuenta> = withContext(Dispatchers.IO) {
        val soapEnvelope = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
                <soapenv:Header/>
                <soapenv:Body>
                    <tem:TraerBalances/>
                </soapenv:Body>
            </soapenv:Envelope>
        """.trimIndent()
        
        val response = executeRequest("TraerBalances", soapEnvelope)
        parseCuentas(response)
    }
    
    private fun parseCuentas(xmlResponse: String): List<Cuenta> {
        val cuentas = mutableListOf<Cuenta>()
        
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(xmlResponse.reader())
            
            var eventType = parser.eventType
            var currentCuenta: MutableMap<String, String>? = null
            var currentTag = ""
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        currentTag = parser.name
                        if (currentTag == "Cuenta") {
                            currentCuenta = mutableMapOf()
                        }
                    }
                    XmlPullParser.TEXT -> {
                        if (currentCuenta != null && parser.text.isNotBlank()) {
                            currentCuenta[currentTag] = parser.text.trim()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "Cuenta" && currentCuenta != null) {
                            cuentas.add(
                                Cuenta(
                                    numeroCuenta = currentCuenta["NumeroCuenta"] ?: "",
                                    nombreCliente = currentCuenta["NombreCliente"] ?: "",
                                    saldo = currentCuenta["Saldo"]?.toDoubleOrNull() ?: 0.0,
                                    moneda = currentCuenta["Moneda"] ?: "",
                                    estado = currentCuenta["Estado"] ?: ""
                                )
                            )
                            currentCuenta = null
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return cuentas
    }
}
