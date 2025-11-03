package ec.edu.pinza.webcli_conuni_soapdotnet_gr03.controlador;

import ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo.LoginModel;
import ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo.LoginResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final LoginModel model = new LoginModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/vista/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String usuario = req.getParameter("usuario");
        String contrasena = req.getParameter("contrasena");

        LoginResponse resultado = model.autenticar(usuario, contrasena);

        if (resultado != null && resultado.isSuccess()) {
            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuario", usuario);
            if (resultado.getToken() != null && !resultado.getToken().isBlank()) {
                sesion.setAttribute("token", resultado.getToken());
            } else {
                sesion.removeAttribute("token");
            }
            if (resultado.getMessage() != null && !resultado.getMessage().isBlank()) {
                sesion.setAttribute("loginMessage", resultado.getMessage());
            } else {
                sesion.removeAttribute("loginMessage");
            }
            resp.sendRedirect(req.getContextPath() + "/vista/conversion.jsp");
        } else {
            String mensaje = (resultado != null && resultado.getMessage() != null && !resultado.getMessage().isBlank())
                    ? resultado.getMessage()
                    : "Usuario o contrasena incorrectos.";
            req.setAttribute("error", mensaje);
            req.getRequestDispatcher("/vista/login.jsp").forward(req, resp);
        }
    }
}

