using System.Runtime.Serialization;

namespace WCFService.Models
{
    [DataContract]
    public class CoordinateDMS
    {
        [DataMember]
        public int Degrees { get; set; }

        [DataMember]
        public int Minutes { get; set; }

        [DataMember]
        public double Seconds { get; set; }

        [DataMember]
        public char Hemisphere { get; set; }
    }

    [DataContract]
    public class ConversionResponse
    {
        [DataMember]
        public double LatitudeDecimal { get; set; }

        [DataMember]
        public double LongitudeDecimal { get; set; }

        [DataMember]
        public double LatitudeRadians { get; set; }

        [DataMember]
        public double LongitudeRadians { get; set; }

        [DataMember]
    public CoordinateDMS? LatitudeDMS { get; set; }

    [DataMember]
    public CoordinateDMS? LongitudeDMS { get; set; }

        [DataMember]
        public double MassKg { get; set; }

        [DataMember]
        public double MassLb { get; set; }

        [DataMember]
        public double MassG { get; set; }

        [DataMember]
        public double TemperatureCelsius { get; set; }

        [DataMember]
        public double TemperatureFahrenheit { get; set; }

        [DataMember]
        public double TemperatureKelvin { get; set; }

        [DataMember]
        public double Longitude2Decimal { get; set; }

        [DataMember]
        public double Longitude2Radians { get; set; }

        [DataMember]
        public CoordinateDMS? Longitude2DMS { get; set; }
    }
}
