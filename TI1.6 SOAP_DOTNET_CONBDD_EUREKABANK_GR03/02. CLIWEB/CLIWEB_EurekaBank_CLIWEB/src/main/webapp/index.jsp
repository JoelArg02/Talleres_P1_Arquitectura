<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Si el usuario ya tiene sesión, redirigir a movimientos
    if (session != null && session.getAttribute("usuario") != null) {
        response.sendRedirect("movimientos.jsp");
    } else {
        // Si no, redirigir al login
        response.sendRedirect("login.jsp");
    }
%>
