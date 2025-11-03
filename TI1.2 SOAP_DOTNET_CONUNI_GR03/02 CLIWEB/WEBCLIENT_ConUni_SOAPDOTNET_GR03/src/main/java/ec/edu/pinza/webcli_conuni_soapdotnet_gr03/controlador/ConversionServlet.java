package ec.edu.pinza.webcli_conuni_soapdotnet_gr03.controlador;

import ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo.ConversionModel;
import ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo.ConversionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/convertir")
public class ConversionServlet extends HttpServlet {

    private final ConversionModel model = new ConversionModel();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession sesion = req.getSession(false);
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/vista/login.jsp");
            return;
        }

        String valorParam = req.getParameter("valor");
        String inUnit = req.getParameter("inUnit");
        String outUnit = req.getParameter("outUnit");

        double valor;
        try {
            valor = Double.parseDouble(valorParam);
        } catch (NumberFormatException ex) {
            req.setAttribute("valor", valorParam);
            req.setAttribute("inUnit", inUnit);
            req.setAttribute("outUnit", outUnit);
            req.setAttribute("errorConversion", "Ingresa un valor numerico valido.");
            req.getRequestDispatcher("/vista/conversion.jsp").forward(req, resp);
            return;
        }

        if (!isSupportedUnit(inUnit) || !isSupportedUnit(outUnit)) {
            req.setAttribute("valor", valor);
            req.setAttribute("inUnit", inUnit);
            req.setAttribute("outUnit", outUnit);
            req.setAttribute("errorConversion", "Selecciona unidades validas.");
            req.getRequestDispatcher("/vista/conversion.jsp").forward(req, resp);
            return;
        }

        double masaKg = toKilograms(valor, inUnit);
        ConversionResponse conversion = model.convertir(masaKg);

        if (conversion == null) {
            req.setAttribute("errorConversion", "No se pudo realizar la conversion. Intenta nuevamente mas tarde.");
        } else {
            Double resultado = extractUnit(conversion, outUnit);
            if (resultado == null) {
                req.setAttribute("errorConversion", "El servicio no devolvio la unidad solicitada.");
            } else {
                req.setAttribute("resultadoTexto", formatResultado(resultado));
            }
        }

        req.setAttribute("valor", valor);
        req.setAttribute("inUnit", inUnit);
        req.setAttribute("outUnit", outUnit);

        String loginMessage = (String) sesion.getAttribute("loginMessage");
        if (loginMessage != null) {
            req.setAttribute("loginMessage", loginMessage);
            sesion.removeAttribute("loginMessage");
        }

        req.getRequestDispatcher("/vista/conversion.jsp").forward(req, resp);
    }

    private boolean isSupportedUnit(String unit) {
        return "kg".equals(unit) || "lb".equals(unit) || "g".equals(unit);
    }

    private double toKilograms(double value, String unit) {
        switch (unit) {
            case "lb":
                return value * 0.45359237;
            case "g":
                return value / 1000.0;
            case "kg":
            default:
                return value;
        }
    }

    private Double extractUnit(ConversionResponse response, String unit) {
        if (response == null) {
            return null;
        }
        return switch (unit) {
            case "lb" -> response.getMassLb();
            case "g" -> response.getMassG();
            case "kg" -> response.getMassKg();
            default -> null;
        };
    }

    private String formatResultado(Double valor) {
        if (valor == null) {
            return null;
        }
        return String.format(Locale.US, "%.4f", valor);
    }
}
