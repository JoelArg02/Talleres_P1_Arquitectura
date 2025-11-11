package ec.edu.espe.soap_java_bank.data.remote.services

import android.util.Log
import ec.edu.espe.soap_java_bank.data.remote.base.BaseSoapClient

class MovimientosService : BaseSoapClient() {
    
    suspend fun obtenerMovimientos(cuenta: String): List<Map<String, String>> {
        return try {
            Log.d("MovimientosService", "Obteniendo movimientos para cuenta: $cuenta")
            
            val soapRequest = """
                <?xml version="1.0" encoding="UTF-8"?>
                <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns2="http://controller.gr03.edu.ec/">
                    <S:Body>
                        <ns2:traerMovimientos>
                            <arg0>$cuenta</arg0>
                        </ns2:traerMovimientos>
                    </S:Body>
                </S:Envelope>
            """.trimIndent()
            
            val response = executeRequest(soapRequest)
            val movimientos = parseMovimientos(response)
            Log.d("MovimientosService", "Movimientos obtenidos: ${movimientos.size}")
            movimientos
        } catch (e: Exception) {
            Log.e("MovimientosService", "Error obteniendo movimientos", e)
            emptyList()
        }
    }
    
    private fun parseMovimientos(xml: String): List<Map<String, String>> {
        val movimientos = mutableListOf<Map<String, String>>()
        val movimientoPattern = """<movimiento>(.*?)</movimiento>""".toRegex(RegexOption.DOT_MATCHES_ALL)
        
        movimientoPattern.findAll(xml).forEach { movMatch ->
            val movData = movMatch.groupValues[1]
            val movimiento = mutableMapOf<String, String>()
            
            extractValue(movData, "idMovimiento")?.let { 
                movimiento["idMovimiento"] = it
                Log.d("MovimientosService", "Movimiento ID: $it")
            }
            extractValue(movData, "numeroCuenta")?.let { movimiento["numeroCuenta"] = it }
            extractValue(movData, "tipoMovimiento")?.let { movimiento["tipoMovimiento"] = it }
            extractValue(movData, "importe")?.let { movimiento["importe"] = it }
            extractValue(movData, "fecha")?.let { movimiento["fecha"] = it }
            
            if (movimiento.isNotEmpty()) {
                movimientos.add(movimiento)
            }
        }
        
        Log.d("MovimientosService", "Total movimientos parseados: ${movimientos.size}")
        return movimientos
    }
}
