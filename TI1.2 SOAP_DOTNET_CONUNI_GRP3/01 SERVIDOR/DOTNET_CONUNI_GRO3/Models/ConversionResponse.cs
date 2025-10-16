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
        // Latitude/Longitude in decimal degrees (echo)
        [DataMember]
        public double LatitudeDecimal { get; set; }

        [DataMember]
        public double LongitudeDecimal { get; set; }

        // Latitude/Longitude in radians
        [DataMember]
        public double LatitudeRadians { get; set; }

        [DataMember]
        public double LongitudeRadians { get; set; }

        // Latitude/Longitude in DMS
        [DataMember]
    public CoordinateDMS? LatitudeDMS { get; set; }

    [DataMember]
    public CoordinateDMS? LongitudeDMS { get; set; }

        // Mass conversions
        [DataMember]
        public double MassKg { get; set; }

        [DataMember]
        public double MassLb { get; set; }

        [DataMember]
        public double MassG { get; set; }
    }
}
