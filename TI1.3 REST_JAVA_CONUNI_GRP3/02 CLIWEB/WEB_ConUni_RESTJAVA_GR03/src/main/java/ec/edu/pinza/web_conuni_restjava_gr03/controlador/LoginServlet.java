package ec.edu.pinza.web_conuni_restjava_gr03.controlador;

import ec.edu.pinza.web_conuni_restjava_gr03.modelo.LoginModel;
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

        if (usuario == null || contrasena == null) {
            req.setAttribute("error", "Debe ingresar usuario y contrasena");
            req.getRequestDispatcher("/vista/login.jsp").forward(req, resp);
            return;
        }

        boolean ok = model.autenticar(usuario, contrasena);

        if (ok) {
            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuario", usuario);
            resp.sendRedirect(req.getContextPath() + "/conversion");
        } else {
            req.setAttribute("error", "Usuario o contrasena incorrectos");
            req.getRequestDispatcher("/vista/login.jsp").forward(req, resp);
        }
    }
}
