<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Panel Lateral de Navegación -->
<div class="sidebar">
    <div class="logo-container">
        <img src="images/logo.png" alt="EurekaBank Logo" class="logo">
    </div>
    
    <nav class="nav-menu">
        <a href="movimientos.jsp" class="nav-button <%= request.getRequestURI().contains("movimientos") ? "active" : "" %>">
            <img src="images/icon_movimientos.png" alt="" class="nav-icon">
            Movimientos
        </a>
        
        <a href="retiro.jsp" class="nav-button <%= request.getRequestURI().contains("retiro") ? "active" : "" %>">
            <img src="images/icon_retiro.png" alt="" class="nav-icon">
            Retiro
        </a>
        
        <a href="deposito.jsp" class="nav-button <%= request.getRequestURI().contains("deposito") ? "active" : "" %>">
            <img src="images/icon_deposito.png" alt="" class="nav-icon">
            Depósito
        </a>
        
        <a href="transferencia.jsp" class="nav-button <%= request.getRequestURI().contains("transferencia") ? "active" : "" %>">
            <img src="images/icon_transferencia.png" alt="" class="nav-icon">
            Transferencia
        </a>
        
        <a href="balances.jsp" class="nav-button <%= request.getRequestURI().contains("balances") ? "active" : "" %>">
            <img src="images/icon_balances.png" alt="" class="nav-icon">
            Balances
        </a>
        
        <a href="logout" class="nav-button logout" onclick="return confirmarLogout()">
            <img src="images/icon_logout.png" alt="" class="nav-icon">
            Cerrar Sesión
        </a>
    </nav>
</div>
