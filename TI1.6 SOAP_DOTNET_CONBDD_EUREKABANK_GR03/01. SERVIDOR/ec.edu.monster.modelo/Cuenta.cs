using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace ec.edu.monster.modelo
{
    [DataContract]
    public class Cuenta
    {
        [DataMember]
        public string NumeroCuenta { get; set; }

        [DataMember]
        public string NombreCliente { get; set; }

        [DataMember]
        public double Saldo { get; set; }

        [DataMember]
        public string Moneda { get; set; }

        [DataMember]
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
