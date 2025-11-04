package ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.dto;

import java.time.OffsetDateTime;

public class ConversionResult {

    private final double originalValue;
    private final String fromUnit;
    private final double convertedValue;
    private final String toUnit;
    private final String category;
    private final OffsetDateTime timestamp;

    public ConversionResult(double originalValue,
                            String fromUnit,
                            double convertedValue,
                            String toUnit,
                            String category,
                            OffsetDateTime timestamp) {
        this.originalValue = originalValue;
        this.fromUnit = fromUnit;
        this.convertedValue = convertedValue;
        this.toUnit = toUnit;
        this.category = category;
        this.timestamp = timestamp;
    }

    public double originalValue() {
        return originalValue;
    }

    public String fromUnit() {
        return fromUnit;
    }

    public double convertedValue() {
        return convertedValue;
    }

    public String toUnit() {
        return toUnit;
    }

    public String category() {
        return category;
    }

    public OffsetDateTime timestamp() {
        return timestamp;
    }
}
