package ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public final class UnitOption {

    private static final List<UnitOption> MASA;
    private static final List<UnitOption> TEMPERATURA;
    private static final List<UnitOption> LONGITUD;

    static {
        List<UnitOption> masa = new ArrayList<>();
        masa.add(new UnitOption("milligrams", "Milligrams", "Miligramo (mg)"));
        masa.add(new UnitOption("grams", "Grams", "Gramo (g)"));
        masa.add(new UnitOption("kilograms", "Kilograms", "Kilogramo (kg)"));
        masa.add(new UnitOption("pounds", "Pounds", "Libra (lb)"));
        masa.add(new UnitOption("ounces", "Ounces", "Onza (oz)"));
        masa.add(new UnitOption("tons", "Tons", "Tonelada (t)"));
        MASA = Collections.unmodifiableList(masa);

        List<UnitOption> temperatura = new ArrayList<>();
        temperatura.add(new UnitOption("celsius", "Celsius", "Celsius (°C)"));
        temperatura.add(new UnitOption("fahrenheit", "Fahrenheit", "Fahrenheit (°F)"));
        temperatura.add(new UnitOption("kelvin", "Kelvin", "Kelvin (K)"));
        temperatura.add(new UnitOption("rankine", "Rankine", "Rankine (°R)"));
        TEMPERATURA = Collections.unmodifiableList(temperatura);

        List<UnitOption> longitud = new ArrayList<>();
        longitud.add(new UnitOption("millimeters", "Millimeters", "Milímetro (mm)"));
        longitud.add(new UnitOption("centimeters", "Centimeters", "Centímetro (cm)"));
        longitud.add(new UnitOption("meters", "Meters", "Metro (m)"));
        longitud.add(new UnitOption("kilometers", "Kilometers", "Kilómetro (km)"));
        longitud.add(new UnitOption("inches", "Inches", "Pulgada (in)"));
        longitud.add(new UnitOption("feet", "Feet", "Pie (ft)"));
        LONGITUD = Collections.unmodifiableList(longitud);
    }

    private final String slug;
    private final String apiValue;
    private final String etiqueta;

    public UnitOption(String slug, String apiValue, String etiqueta) {
        this.slug = slug;
        this.apiValue = apiValue;
        this.etiqueta = etiqueta;
    }

    public String getSlug() {
        return slug;
    }

    public String getApiValue() {
        return apiValue;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static List<UnitOption> masa() {
        return MASA;
    }

    public static List<UnitOption> temperatura() {
        return TEMPERATURA;
    }

    public static List<UnitOption> longitud() {
        return LONGITUD;
    }

    public static List<UnitOption> porTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            return masa();
        }
        String tipoLower = tipo.toLowerCase(Locale.ROOT);
        if ("masa".equals(tipoLower) || "weight".equals(tipoLower)) {
            return masa();
        } else if ("temperatura".equals(tipoLower) || "temperature".equals(tipoLower)) {
            return temperatura();
        } else if ("longitud".equals(tipoLower) || "length".equals(tipoLower)) {
            return longitud();
        } else {
            return masa();
        }
    }

    public static Optional<UnitOption> findBySlug(String slug, String tipo) {
        if (slug == null || slug.isBlank()) {
            return Optional.empty();
        }
        String normalized = slug.toLowerCase(Locale.ROOT);
        return porTipo(tipo).stream()
                .filter(option -> option.slug.equalsIgnoreCase(normalized))
                .findFirst();
    }
}
