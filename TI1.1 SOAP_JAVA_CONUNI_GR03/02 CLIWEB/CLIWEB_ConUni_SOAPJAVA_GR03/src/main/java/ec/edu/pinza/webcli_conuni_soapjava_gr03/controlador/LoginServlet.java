/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.pinza.webcli_conuni_soapjava_gr03.controlador;

/**
 *
 * @author pinza
 */
import ec.edu.pinza.webcli_conuni_soapjava_gr03.modelo.LoginModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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

        boolean ok = model.autenticar(usuario, contrasena);

        if (ok) {
            req.getSession().setAttribute("usuario", usuario);
            // Redirige de forma absoluta al JSP de conversión
            resp.sendRedirect(req.getContextPath() + "/vista/conversion.jsp");
        } else {
            req.setAttribute("error", "Usuario o contraseña incorrectos");
            req.getRequestDispatcher("/vista/login.jsp").forward(req, resp);
        }
    }
}