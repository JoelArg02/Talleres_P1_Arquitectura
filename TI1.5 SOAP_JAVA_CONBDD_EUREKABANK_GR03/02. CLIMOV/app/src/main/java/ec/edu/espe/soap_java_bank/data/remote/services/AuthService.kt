package ec.edu.espe.soap_java_bank.data.remote.services

import android.util.Log
import ec.edu.espe.soap_java_bank.data.remote.base.BaseSoapClient

class AuthService : BaseSoapClient() {

    suspend fun login(username: String, password: String): Boolean {
        return try {
            Log.d("AuthService", "Intentando login - Usuario: $username")
            
            val soapRequest = """
                <?xml version="1.0" encoding="UTF-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:con="http://controller.gr03.edu.ec/">
                    <soapenv:Header/>
                    <soapenv:Body>
                        <con:login>
                            <username>$username</username>
                            <password>$password</password>
                        </con:login>
                    </soapenv:Body>
                </soapenv:Envelope>
            """.trimIndent()

            val response = executeRequest(soapRequest)
            val result = response.contains("<return>true</return>")
            Log.d("AuthService", "Login resultado: $result")
            result
        } catch (e: Exception) {
            Log.e("AuthService", "Error en login", e)
            false
        }
    }
}

