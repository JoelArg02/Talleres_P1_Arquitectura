using System.Runtime.Serialization;

namespace WCFService.Models
{
    [DataContract]
    public class ConversionRequest
    {
        // Latitud y longitud en grados decimales
        [DataMember]
        public double Latitude { get; set; }

        [DataMember]
        public double Longitude { get; set; }

        // Masa en kilogramos por defecto
        [DataMember]
        public double MassKg { get; set; }
    }
}
