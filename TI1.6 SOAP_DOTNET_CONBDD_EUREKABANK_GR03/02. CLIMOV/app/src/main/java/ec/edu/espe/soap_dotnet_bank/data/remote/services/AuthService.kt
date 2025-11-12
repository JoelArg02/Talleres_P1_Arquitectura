package ec.edu.espe.soap_dotnet_bank.data.remote.services

import ec.edu.espe.soap_dotnet_bank.data.remote.base.BaseSoapClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class AuthService : BaseSoapClient() {
    
    suspend fun login(usuario: String, clave: String): Boolean = withContext(Dispatchers.IO) {
        val soapEnvelope = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
                <soapenv:Header/>
                <soapenv:Body>
                    <tem:Login>
                        <tem:username>$usuario</tem:username>
                        <tem:password>$clave</tem:password>
                    </tem:Login>
                </soapenv:Body>
            </soapenv:Envelope>
        """.trimIndent()
        
        val response = executeRequest("Login", soapEnvelope)
        parseLoginResult(response)
    }
    
    private fun parseLoginResult(xmlResponse: String): Boolean {
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(xmlResponse.reader())
            
            var eventType = parser.eventType
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.name == "LoginResult") {
                            parser.next()
                            if (parser.eventType == XmlPullParser.TEXT) {
                                return parser.text.equals("true", ignoreCase = true)
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return false
    }
}
