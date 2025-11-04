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
    
    fun convertMass(massKg: Double, temperatureCelsius: Double): ConversionResponse {
        Log.d(TAG, "Converting mass: $massKg kg, temperature: $temperatureCelsius °C")
        
        val soapRequest = buildConversionRequest(massKg, temperatureCelsius)
        val response = soapClient.callSoap(
            SoapClient.CONVERSION_ENDPOINT,
            SOAP_ACTION,
            soapRequest
        )
        
        return parseConversionResponse(response)
    }
    
    private fun buildConversionRequest(massKg: Double, temperatureCelsius: Double): String {
        return """<?xml version="1.0" encoding="utf-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/" xmlns:mod="http://schemas.datacontract.org/2004/07/WCFService.Models">
  <soapenv:Header/>
  <soapenv:Body>
    <tem:Convert>
      <tem:request>
        <mod:MassKg>$massKg</mod:MassKg>
        <mod:TemperatureCelsius>$temperatureCelsius</mod:TemperatureCelsius>
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
            
            val massKg = massKgNode?.textContent?.toDouble() ?: 0.0
            val massLb = massLbNode?.textContent?.toDouble() ?: 0.0
            val massG = massGNode?.textContent?.toDouble() ?: 0.0
            val tempC = tempCNode?.textContent?.toDouble() ?: 0.0
            val tempF = tempFNode?.textContent?.toDouble() ?: 0.0
            val tempK = tempKNode?.textContent?.toDouble() ?: 0.0
            
            Log.d(TAG, "Conversion result: mass kg=$massKg, temp c=$tempC")
            
            return ConversionResponse(massKg, massLb, massG, tempC, tempF, tempK)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing conversion response", e)
            throw RuntimeException("Error parsing SOAP response", e)
        }
    }
}
