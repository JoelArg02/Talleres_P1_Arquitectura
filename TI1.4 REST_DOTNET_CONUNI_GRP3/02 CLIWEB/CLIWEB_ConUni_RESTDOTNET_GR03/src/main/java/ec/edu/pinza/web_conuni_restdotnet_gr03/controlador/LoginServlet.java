package ec.edu.pinza.web_conuni_restdotnet_gr03.controlador;

import ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.LoginModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final LoginModel loginModel = new LoginModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/vista/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usuario = req.getParameter("usuario");
        String contrasena = req.getParameter("contrasena");

        if (!loginModel.autenticar(usuario, contrasena)) {
            req.setAttribute("error", "Credenciales invalidas. Intente nuevamente.");
            req.getRequestDispatcher("/vista/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        String usuarioNormalizado = usuario == null ? "MONSTER" : usuario.trim().toUpperCase(Locale.ROOT);
        session.setAttribute("usuario", usuarioNormalizado);
        resp.sendRedirect(req.getContextPath() + "/conversion");
    }
}
