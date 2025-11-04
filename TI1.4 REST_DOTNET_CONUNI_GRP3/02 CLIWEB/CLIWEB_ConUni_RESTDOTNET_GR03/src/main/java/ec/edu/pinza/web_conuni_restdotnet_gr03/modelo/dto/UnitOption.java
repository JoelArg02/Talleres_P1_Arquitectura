package ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public final class UnitOption {

    private static final List<UnitOption> LONGITUD;

    static {
        List<UnitOption> opciones = new ArrayList<>();
        opciones.add(new UnitOption("meters", 0, "Metros (m)"));
        opciones.add(new UnitOption("kilometers", 1, "Kilometros (km)"));
        opciones.add(new UnitOption("centimeters", 2, "Centimetros (cm)"));
        opciones.add(new UnitOption("millimeters", 3, "Milimetros (mm)"));
        opciones.add(new UnitOption("miles", 4, "Millas (mi)"));
        opciones.add(new UnitOption("yards", 5, "Yardas (yd)"));
        opciones.add(new UnitOption("feet", 6, "Pies (ft)"));
        opciones.add(new UnitOption("inches", 7, "Pulgadas (in)"));
        LONGITUD = Collections.unmodifiableList(opciones);
    }

    private final String slug;
    private final int codigoApi;
    private final String etiqueta;

    public UnitOption(String slug, int codigoApi, String etiqueta) {
        this.slug = slug;
        this.codigoApi = codigoApi;
        this.etiqueta = etiqueta;
    }

    public String getSlug() {
        return slug;
    }

    public int getCodigoApi() {
        return codigoApi;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static List<UnitOption> longitud() {
        return LONGITUD;
    }

    public static Optional<UnitOption> findBySlug(String slug) {
        if (slug == null || slug.isBlank()) {
            return Optional.empty();
        }
        String normalized = slug.toLowerCase(Locale.ROOT);
        return LONGITUD.stream()
                .filter(option -> option.slug.equalsIgnoreCase(normalized))
                .findFirst();
    }
}
