using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace ec.edu.monster.modelo
{
    [DataContract]
    public class Movimiento
    {
        [DataMember]
        public string Cuenta { get; set; }

        [DataMember]
        public int NroMov { get; set; }

        [DataMember]
        public DateTime Fecha { get; set; }

        [DataMember]
        public string Tipo { get; set; }

        [DataMember]
        public string Accion { get; set; }

        [DataMember]
        public double Importe { get; set; }

        public Movimiento() { }

        public Movimiento(string cuenta, int nromov, DateTime fecha, string tipo, string accion, double importe)
        {
            Cuenta = cuenta;
            NroMov = nromov;
            Fecha = fecha;
            Tipo = tipo;
            Accion = accion;
            Importe = importe;
        }
    }
}