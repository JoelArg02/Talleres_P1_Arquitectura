package ec.edu.espe.dotnetsoap.data.repository

import android.util.Log
import ec.edu.espe.dotnetsoap.data.models.LoginResponse
import ec.edu.espe.dotnetsoap.data.remote.SoapClient
import org.w3c.dom.Document
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

class LoginRepository {
    
    private val soapClient = SoapClient()
    
    companion object {
        private const val TAG = "LoginRepository"
        private const val SOAP_ACTION = "http://tempuri.org/ILoginService/ValidarCredenciales"
    }
    
    fun login(username: String, password: String): LoginResponse {
        Log.d(TAG, "Login attempt for user: $username")
        
        val soapRequest = buildLoginRequest(username, password)
        val response = soapClient.callSoap(
            SoapClient.LOGIN_ENDPOINT,
            SOAP_ACTION,
            soapRequest
        )
        
        return parseLoginResponse(response)
    }
    
    private fun buildLoginRequest(username: String, password: String): String {
        return """<?xml version="1.0" encoding="utf-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
  <soapenv:Header/>
  <soapenv:Body>
    <tem:ValidarCredenciales>
      <tem:username>${escapeXml(username)}</tem:username>
      <tem:password>${escapeXml(password)}</tem:password>
    </tem:ValidarCredenciales>
  </soapenv:Body>
</soapenv:Envelope>"""
    }
    
    private fun parseLoginResponse(xml: String): LoginResponse {
        try {
            Log.d(TAG, "Parsing login response...")
            
            val factory = DocumentBuilderFactory.newInstance()
            factory.isNamespaceAware = true
            val builder = factory.newDocumentBuilder()
            val doc: Document = builder.parse(ByteArrayInputStream(xml.toByteArray()))
            doc.documentElement.normalize()
            
            val successNode = doc.getElementsByTagName("a:Success").item(0)
            val messageNode = doc.getElementsByTagName("a:Message").item(0)
            val tokenNode = doc.getElementsByTagName("a:Token").item(0)
            
            val success = successNode?.textContent?.toBoolean() ?: false
            val message = messageNode?.textContent ?: ""
            val token = tokenNode?.textContent
            
            Log.d(TAG, "Login result: success=$success, message=$message")
            
            return LoginResponse(success, message, token)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing login response", e)
            throw RuntimeException("Error parsing SOAP response", e)
        }
    }
    
    private fun escapeXml(input: String): String {
        return input.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }
}
