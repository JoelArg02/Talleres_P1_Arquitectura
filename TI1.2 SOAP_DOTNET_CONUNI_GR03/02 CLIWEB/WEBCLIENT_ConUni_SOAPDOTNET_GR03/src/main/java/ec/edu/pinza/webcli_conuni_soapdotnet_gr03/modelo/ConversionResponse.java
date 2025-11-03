package ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo;

public class ConversionResponse {

    private final Double massKg;
    private final Double massLb;
    private final Double massG;
    private final Double latitudeDecimal;
    private final Double latitudeRadians;
    private final CoordinateDMS latitudeDms;
    private final Double longitudeDecimal;
    private final Double longitudeRadians;
    private final CoordinateDMS longitudeDms;

    public ConversionResponse(
            Double massKg,
            Double massLb,
            Double massG,
            Double latitudeDecimal,
            Double latitudeRadians,
            CoordinateDMS latitudeDms,
            Double longitudeDecimal,
            Double longitudeRadians,
            CoordinateDMS longitudeDms
    ) {
        this.massKg = massKg;
        this.massLb = massLb;
        this.massG = massG;
        this.latitudeDecimal = latitudeDecimal;
        this.latitudeRadians = latitudeRadians;
        this.latitudeDms = latitudeDms;
        this.longitudeDecimal = longitudeDecimal;
        this.longitudeRadians = longitudeRadians;
        this.longitudeDms = longitudeDms;
    }

    public Double getMassKg() {
        return massKg;
    }

    public Double getMassLb() {
        return massLb;
    }

    public Double getMassG() {
        return massG;
    }

    public Double getLatitudeDecimal() {
        return latitudeDecimal;
    }

    public Double getLatitudeRadians() {
        return latitudeRadians;
    }

    public CoordinateDMS getLatitudeDms() {
        return latitudeDms;
    }

    public Double getLongitudeDecimal() {
        return longitudeDecimal;
    }

    public Double getLongitudeRadians() {
        return longitudeRadians;
    }

    public CoordinateDMS getLongitudeDms() {
        return longitudeDms;
    }
}

