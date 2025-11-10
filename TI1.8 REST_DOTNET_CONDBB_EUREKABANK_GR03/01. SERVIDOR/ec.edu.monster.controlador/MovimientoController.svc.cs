using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ec.edu.monster.modelo;
using ec.edu.monster.servicio;


namespace ec.edu.monster.controlador
{
    public class MovimientoController : IMovimientoController
    {
        public List<Movimiento> ObtenerPorCuenta(string cuenta)
        {
            return MovimientoServicio.ListarPorCuenta(cuenta);
        }

        public string RegistrarDeposito(string cuenta, double importe)
        {
            MovimientoServicio.RegistrarDeposito(cuenta, importe, "0001");
            return "1";
        }

        public string RegistrarRetiro(string cuenta, double importe)
        {
            MovimientoServicio.RegistrarRetiro(cuenta, importe, "0001");
            return "1";
        }

        public string RegistrarTransferencia(string cuentaOrigen, string cuentaDestino, double importe)
        {
            MovimientoServicio.RegistrarTransferencia(cuentaOrigen, cuentaDestino, importe, "0001");
            return "1";
        }

        public bool Login(string username, string password)
        {
            return MovimientoServicio.Login(username, password);
        }
    }
}