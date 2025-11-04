package ec.edu.pinza.web_conuni_restdotnet_gr03.controlador;

import ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.ConversionException;
import ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.ConversionModel;
import ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.dto.ConversionResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/conversion", "/convertir"})
public class ConversionServlet extends HttpServlet {

    private final ConversionModel conversionModel = new ConversionModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!sesionValida(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        prepararCatalogos(req);
        req.getRequestDispatcher("/vista/conversion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!sesionValida(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String valorParam = req.getParameter("valor");
        valorParam = valorParam != null ? valorParam.trim() : null;
        String unidadOrigen = req.getParameter("inUnit");
        String unidadDestino = req.getParameter("outUnit");

        if (unidadOrigen == null || unidadOrigen.isBlank()) {
            unidadOrigen = "meters";
        }
        if (unidadDestino == null || unidadDestino.isBlank()) {
            unidadDestino = "kilometers";
        }

        req.setAttribute("valor", valorParam);
        req.setAttribute("inUnit", unidadOrigen);
        req.setAttribute("outUnit", unidadDestino);

        if (valorParam == null || valorParam.isBlank()) {
            req.setAttribute("errorConversion", "Debe ingresar un valor a convertir.");
        } else {
            try {
                double valor = Double.parseDouble(valorParam);
                ConversionResult resultado = conversionModel.convertir(valor, unidadOrigen, unidadDestino);
                req.setAttribute("resultado", resultado);
            } catch (NumberFormatException ex) {
                req.setAttribute("errorConversion", "Ingrese un valor numerico valido.");
            } catch (ConversionException ex) {
                req.setAttribute("errorConversion", ex.getMessage());
            }
        }

        prepararCatalogos(req);
        req.getRequestDispatcher("/vista/conversion.jsp").forward(req, resp);
    }

    private boolean sesionValida(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("usuario") != null;
    }

    private void prepararCatalogos(HttpServletRequest req) {
        req.setAttribute("units", conversionModel.obtenerUnidades());
    }
}
