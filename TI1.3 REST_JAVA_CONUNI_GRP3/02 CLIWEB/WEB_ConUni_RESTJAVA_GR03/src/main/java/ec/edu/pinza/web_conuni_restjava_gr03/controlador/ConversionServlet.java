package ec.edu.pinza.web_conuni_restjava_gr03.controlador;

import ec.edu.pinza.web_conuni_restjava_gr03.modelo.ConversionModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/convertir", "/conversion"})
public class ConversionServlet extends HttpServlet {

    private final ConversionModel model = new ConversionModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!sesionEsValida(req)) {
            resp.sendRedirect(req.getContextPath() + "/vista/login.jsp");
            return;
        }

        String tipo = model.getTipoPorDefecto();
        req.setAttribute("tipo", tipo);
        cargarCatalogos(req, tipo);
        req.getRequestDispatcher("/vista/conversion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!sesionEsValida(req)) {
            resp.sendRedirect(req.getContextPath() + "/vista/login.jsp");
            return;
        }

        String tipo = req.getParameter("tipo");
        if (tipo == null || tipo.isBlank()) {
            tipo = model.getTipoPorDefecto();
        }

        String valorParametro = req.getParameter("valor");
        String inUnit = req.getParameter("inUnit");
        String outUnit = req.getParameter("outUnit");

        req.setAttribute("tipo", tipo);
        req.setAttribute("valor", valorParametro);
        req.setAttribute("inUnit", inUnit);
        req.setAttribute("outUnit", outUnit);

        Double resultado = null;
        if (valorParametro != null && !valorParametro.isBlank()) {
            try {
                double valor = Double.parseDouble(valorParametro);
                resultado = model.convertir(valor, tipo, inUnit, outUnit);
                if (resultado == null) {
                    req.setAttribute("errorConversion", "No se pudo obtener un resultado desde el servicio REST.");
                }
            } catch (NumberFormatException ex) {
                req.setAttribute("errorConversion", "Ingrese un valor numerico valido.");
            }
        } else {
            req.setAttribute("errorConversion", "Debe ingresar un valor a convertir.");
        }

        if (resultado != null) {
            req.setAttribute("resultado", resultado);
        }

        cargarCatalogos(req, tipo);
        req.getRequestDispatcher("/vista/conversion.jsp").forward(req, resp);
    }

    private boolean sesionEsValida(HttpServletRequest req) {
        HttpSession ses = req.getSession(false);
        return ses != null && ses.getAttribute("usuario") != null;
    }

    private void cargarCatalogos(HttpServletRequest req, String tipo) {
        List<String> unidades = model.obtenerUnidadesConFallback(tipo);
        req.setAttribute("units", unidades);
    }
}
