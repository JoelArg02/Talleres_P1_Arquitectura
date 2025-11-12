using System;

namespace ec.edu.pinza.modelo
{
    public class Cuenta
    {
        public string NumeroCuenta { get; set; }
        public string NombreCliente { get; set; }
        public double Saldo { get; set; }
        public string Moneda { get; set; }
        public string Estado { get; set; }

        public Cuenta() { }

        public Cuenta(string numeroCuenta, string nombreCliente, double saldo, string moneda, string estado)
        {
            NumeroCuenta = numeroCuenta;
            NombreCliente = nombreCliente;
            Saldo = saldo;
            Moneda = moneda;
            Estado = estado;
        }
    }
}
