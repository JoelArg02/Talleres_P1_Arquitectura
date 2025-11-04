using System.Runtime.Serialization;

namespace WCFService.Models
{
    [DataContract]
    public class ConversionRequest
    {
        [DataMember]
        public double Latitude { get; set; }

        [DataMember]
        public double Longitude { get; set; }

        [DataMember]
        public double MassKg { get; set; }

        [DataMember]
        public double TemperatureCelsius { get; set; }
    }
}
