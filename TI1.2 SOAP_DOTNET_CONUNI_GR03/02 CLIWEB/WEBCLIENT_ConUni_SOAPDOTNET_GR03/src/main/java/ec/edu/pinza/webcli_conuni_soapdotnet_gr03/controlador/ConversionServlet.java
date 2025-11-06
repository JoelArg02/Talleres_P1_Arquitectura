package ec.edu.pinza.webcli_conuni_soapdotnet_gr03.controlador;

import ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo.ConversionModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
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
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><body>");
            out.println("<div data-resultado='0'>Error: Valor invalido</div>");
            out.println("</body></html>");
            return;
        }

        Double resultado = model.convertir(valor, inUnit, outUnit);

        if (resultado == null) {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><body>");
            out.println("<div data-resultado='0'>Error en conversion</div>");
            out.println("</body></html>");
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<div data-resultado='" + resultado + "'>" + formatearNumero(resultado) + "</div>");
        out.println("</body></html>");
    }
    
    private String formatearNumero(double value) {
        double absValue = Math.abs(value);
        
        // Para numeros muy pequenos (menores a 0.001) o muy grandes (mayores a 1,000,000)
        if ((absValue > 0 && absValue < 0.001) || absValue >= 1_000_000) {
            return String.format(Locale.US, "%.6e", value);
        }
        // Para numeros normales, usar formato con 3 decimales
        else if (absValue >= 1000) {
            return String.format(Locale.US, "%,.3f", value);
        }
        // Para numeros menores a 1000, usar 3 decimales
        else {
            return String.format(Locale.US, "%.3f", value);
        }
    }
}
