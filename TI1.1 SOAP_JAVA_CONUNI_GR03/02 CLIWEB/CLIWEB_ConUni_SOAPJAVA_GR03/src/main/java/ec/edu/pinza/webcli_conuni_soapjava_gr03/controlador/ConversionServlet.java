package ec.edu.pinza.webcli_conuni_soapjava_gr03.controlador;

import ec.edu.pinza.webcli_conuni_soapjava_gr03.modelo.ConversionModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/convertir")
public class ConversionServlet extends HttpServlet {

    private final ConversionModel model = new ConversionModel();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession ses = req.getSession(false);
        if (ses == null || ses.getAttribute("usuario") == null) {
            // Redirección segura al login
            resp.sendRedirect(req.getContextPath() + "/vista/login.jsp");
            return;
        }

        double valor = Double.parseDouble(req.getParameter("valor"));
        String inUnit = req.getParameter("inUnit");
        String outUnit = req.getParameter("outUnit");

        double resultado = model.convertir(valor, inUnit, outUnit);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<div data-resultado=\"" + resultado + "\"></div>");
    }
}