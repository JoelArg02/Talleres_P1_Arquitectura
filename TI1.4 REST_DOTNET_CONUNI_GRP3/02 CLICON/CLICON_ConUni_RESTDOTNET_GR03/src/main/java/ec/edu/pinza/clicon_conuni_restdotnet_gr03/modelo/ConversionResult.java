package ec.edu.pinza.clicon_conuni_restdotnet_gr03.modelo;

public record ConversionResult(
        double originalValue,
        String fromUnit,
        double convertedValue,
        String toUnit,
        String category,
        String timestamp) {

    public ConversionResult {
        fromUnit = fromUnit == null ? "" : fromUnit;
        toUnit = toUnit == null ? "" : toUnit;
        category = category == null ? "" : category;
        timestamp = timestamp == null ? "" : timestamp;
    }
}
