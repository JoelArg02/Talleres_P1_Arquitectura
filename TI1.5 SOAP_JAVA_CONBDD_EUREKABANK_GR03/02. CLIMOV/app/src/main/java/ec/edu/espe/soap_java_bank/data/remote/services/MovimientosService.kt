package ec.edu.espe.soap_java_bank.data.remote.services

import android.util.Log
import ec.edu.espe.soap_java_bank.data.remote.base.BaseSoapClient

class MovimientosService : BaseSoapClient() {

    suspend fun obtenerMovimientos(cuenta: String): List<Map<String, String>> {
        return try {
            Log.d("MovimientosService", "Obteniendo movimientos para cuenta: $cuenta")

            val soapRequest = """
                <?xml version="1.0" encoding="UTF-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:con="http://controller.gr03.edu.ec/">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <con:traerMovimientos>
                         <cuenta>$cuenta</cuenta>
                      </con:traerMovimientos>
                   </soapenv:Body>
                </soapenv:Envelope>
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

        movimientoPattern.findAll(xml).forEach { match ->
            val movData = match.groupValues[1]
            val movimiento = mutableMapOf<String, String>()

            extractTagValue(movData, "accion")?.let { movimiento["accion"] = it }
            extractTagValue(movData, "cuenta")?.let { movimiento["cuenta"] = it }
            extractTagValue(movData, "fecha")?.let { movimiento["fecha"] = it }
            extractTagValue(movData, "importe")?.let { movimiento["importe"] = it }
            extractTagValue(movData, "nromov")?.let { movimiento["nromov"] = it }
            extractTagValue(movData, "tipo")?.let { movimiento["tipo"] = it }

            if (movimiento.isNotEmpty()) {
                movimientos.add(movimiento)
            }
        }

        Log.d("MovimientosService", "Total movimientos parseados: ${movimientos.size}")
        return movimientos
    }

    private fun extractTagValue(xml: String, tag: String): String? {
        val regex = "<$tag>(.*?)</$tag>".toRegex()
        return regex.find(xml)?.groupValues?.get(1)
    }
}
