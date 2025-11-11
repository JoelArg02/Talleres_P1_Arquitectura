package ec.edu.espe.soap_java_bank.data.remote.services

import android.util.Log
import ec.edu.espe.soap_java_bank.data.remote.base.BaseSoapClient

class BalancesService : BaseSoapClient() {
    
    suspend fun obtenerBalances(): List<Map<String, String>> {
        return try {
            Log.d("BalancesService", "Iniciando petición traerBalances")
            
            val soapRequest = """
                <?xml version="1.0" encoding="UTF-8"?>
                <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns2="http://controller.gr03.edu.ec/">
                    <S:Body>
                        <ns2:traerBalances/>
                    </S:Body>
                </S:Envelope>
            """.trimIndent()
            
            val response = executeRequest(soapRequest)
            val balances = parseBalances(response)
            Log.d("BalancesService", "Balances obtenidos: ${balances.size}")
            balances
        } catch (e: Exception) {
            Log.e("BalancesService", "Error obteniendo balances", e)
            emptyList()
        }
    }
    
    private fun parseBalances(xml: String): List<Map<String, String>> {
        val balances = mutableListOf<Map<String, String>>()
        val cuentaPattern = """<cuenta>(.*?)</cuenta>""".toRegex(RegexOption.DOT_MATCHES_ALL)
        
        cuentaPattern.findAll(xml).forEach { cuentaMatch ->
            val cuentaData = cuentaMatch.groupValues[1]
            val balance = mutableMapOf<String, String>()
            
            extractValue(cuentaData, "numeroCuenta")?.let { 
                balance["numeroCuenta"] = it
                Log.d("BalancesService", "Cuenta: $it")
            }
            extractValue(cuentaData, "nombreCliente")?.let { 
                balance["nombreCliente"] = it 
            }
            extractValue(cuentaData, "saldo")?.let { 
                balance["saldo"] = it 
            }
            extractValue(cuentaData, "estado")?.let { 
                balance["estado"] = it 
            }
            
            if (balance.isNotEmpty()) {
                balances.add(balance)
            }
        }
        
        Log.d("BalancesService", "Total balances parseados: ${balances.size}")
        return balances
    }
}
