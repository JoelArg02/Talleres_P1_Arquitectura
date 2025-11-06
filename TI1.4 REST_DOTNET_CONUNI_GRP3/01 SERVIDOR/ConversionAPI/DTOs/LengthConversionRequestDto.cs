using ConversionAPI.Enums;
using System.Text.Json.Serialization;

namespace ConversionAPI.DTOs
{
    public class LengthConversionRequestDto
    {
        public double Value { get; set; }
        
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public LengthUnit FromUnit { get; set; }
        
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public LengthUnit ToUnit { get; set; }
    }
}
