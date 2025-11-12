using System;

namespace ec.edu.pinza.modelo
{
    public class Movimiento
    {
        public string Cuenta { get; set; }
        public int NroMov { get; set; }
        public DateTime Fecha { get; set; }
        public string Tipo { get; set; }
        public string Accion { get; set; }
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
