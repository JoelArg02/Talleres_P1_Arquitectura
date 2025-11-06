package ec.edu.espe.conv_rtfull.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.abs

/**
 * Utilidad para formatear números según las reglas establecidas
 */
object NumberFormatter {
    
    /**
     * Formatea un número según las siguientes reglas:
     * - Si |valor| < 0.001 o |valor| >= 1,000,000: notación exponencial (ej: 1.234567E+06)
     * - Si 1000 <= |valor| < 1,000,000: separador de miles con 3 decimales (ej: 1,234.567)
     * - En otros casos: 3 decimales (ej: 123.456)
     */
    fun formatNumber(value: Double): String {
        val absValue = abs(value)
        
        return when {
            // Notación exponencial para valores muy pequeños o muy grandes
            absValue < 0.001 && absValue != 0.0 -> {
                String.format(Locale.US, "%.6e", value)
            }
            absValue >= 1_000_000.0 -> {
                String.format(Locale.US, "%.6e", value)
            }
            // Separador de miles con 3 decimales para valores entre 1000 y 1,000,000
            absValue >= 1000.0 -> {
                val symbols = DecimalFormatSymbols(Locale.US).apply {
                    groupingSeparator = ','
                    decimalSeparator = '.'
                }
                val formatter = DecimalFormat("#,##0.000", symbols)
                formatter.format(value)
            }
            // 3 decimales para valores pequeños
            else -> {
                String.format(Locale.US, "%.3f", value)
            }
        }
    }
}
