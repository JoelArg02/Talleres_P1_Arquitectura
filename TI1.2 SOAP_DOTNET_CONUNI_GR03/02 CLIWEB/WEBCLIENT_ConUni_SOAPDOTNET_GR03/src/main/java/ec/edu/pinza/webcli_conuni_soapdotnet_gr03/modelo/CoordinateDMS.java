package ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo;

public class CoordinateDMS {

    private final Integer degrees;
    private final Integer minutes;
    private final Double seconds;
    private final String hemisphere;

    public CoordinateDMS(Integer degrees, Integer minutes, Double seconds, String hemisphere) {
        this.degrees = degrees;
        this.minutes = minutes;
        this.seconds = seconds;
        this.hemisphere = hemisphere;
    }

    public Integer getDegrees() {
        return degrees;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Double getSeconds() {
        return seconds;
    }

    public String getHemisphere() {
        return hemisphere;
    }

    public boolean isDefined() {
        return degrees != null || minutes != null || seconds != null || (hemisphere != null && !hemisphere.isBlank());
    }
}

