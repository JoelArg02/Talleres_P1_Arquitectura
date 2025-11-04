package ec.edu.espe.dotnetsoap.data.repository

import android.util.Log
import ec.edu.espe.dotnetsoap.data.models.ConversionResponse
import ec.edu.espe.dotnetsoap.data.remote.SoapClient
import org.w3c.dom.Document
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

class ConversionRepository {
    
    private val soapClient = SoapClient()
    
    companion object {
        private const val TAG = "ConversionRepository"
        private const val SOAP_ACTION = "http://tempuri.org/IService/Convert"
    }
    
    fun convertMass(massKg: Double, temperatureCelsius: Double, longitude2: Double): ConversionResponse {
        Log.d(TAG, "Converting mass: $massKg kg, temperature: $temperatureCelsius °C, longitude2: $longitude2")
        
        val soapRequest = buildConversionRequest(massKg, temperatureCelsius, longitude2)
        val response = soapClient.callSoap(
            SoapClient.CONVERSION_ENDPOINT,
            SOAP_ACTION,
            soapRequest
        )
        
        return parseConversionResponse(response)
    }
    
    private fun buildConversionRequest(massKg: Double, temperatureCelsius: Double, longitude2: Double): String {
        return """<?xml version="1.0" encoding="utf-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/" xmlns:mod="http://schemas.datacontract.org/2004/07/WCFService.Models">
  <soapenv:Header/>
  <soapenv:Body>
    <tem:Convert>
      <tem:request>
        <mod:MassKg>$massKg</mod:MassKg>
        <mod:TemperatureCelsius>$temperatureCelsius</mod:TemperatureCelsius>
        <mod:Longitude2>$longitude2</mod:Longitude2>
      </tem:request>
    </tem:Convert>
  </soapenv:Body>
</soapenv:Envelope>"""
    }
    
    private fun parseConversionResponse(xml: String): ConversionResponse {
        try {
            Log.d(TAG, "Parsing conversion response...")
            
            val factory = DocumentBuilderFactory.newInstance()
            factory.isNamespaceAware = true
            val builder = factory.newDocumentBuilder()
            val doc: Document = builder.parse(ByteArrayInputStream(xml.toByteArray()))
            doc.documentElement.normalize()
            
            val massKgNode = doc.getElementsByTagName("a:MassKg").item(0)
            val massLbNode = doc.getElementsByTagName("a:MassLb").item(0)
            val massGNode = doc.getElementsByTagName("a:MassG").item(0)
            val tempCNode = doc.getElementsByTagName("a:TemperatureCelsius").item(0)
            val tempFNode = doc.getElementsByTagName("a:TemperatureFahrenheit").item(0)
            val tempKNode = doc.getElementsByTagName("a:TemperatureKelvin").item(0)
            val long2DecimalNode = doc.getElementsByTagName("a:Longitude2Decimal").item(0)
            val long2RadiansNode = doc.getElementsByTagName("a:Longitude2Radians").item(0)
            
            val massKg = massKgNode?.textContent?.toDouble() ?: 0.0
            val massLb = massLbNode?.textContent?.toDouble() ?: 0.0
            val massG = massGNode?.textContent?.toDouble() ?: 0.0
            val tempC = tempCNode?.textContent?.toDouble() ?: 0.0
            val tempF = tempFNode?.textContent?.toDouble() ?: 0.0
            val tempK = tempKNode?.textContent?.toDouble() ?: 0.0
            val long2Dec = long2DecimalNode?.textContent?.toDouble() ?: 0.0
            val long2Rad = long2RadiansNode?.textContent?.toDouble() ?: 0.0
            
            Log.d(TAG, "Conversion result: mass kg=$massKg, temp c=$tempC, long2=$long2Dec")
            
            return ConversionResponse(massKg, massLb, massG, tempC, tempF, tempK, long2Dec, long2Rad)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing conversion response", e)
            throw RuntimeException("Error parsing SOAP response", e)
        }
    }
}
