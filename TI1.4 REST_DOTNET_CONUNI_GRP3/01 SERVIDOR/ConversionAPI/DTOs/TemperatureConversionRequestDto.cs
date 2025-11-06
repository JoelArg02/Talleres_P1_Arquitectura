using ConversionAPI.Enums;
using System.Text.Json.Serialization;

namespace ConversionAPI.DTOs
{
    public class TemperatureConversionRequestDto
    {
        public double Value { get; set; }
        
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public TemperatureUnit FromUnit { get; set; }
        
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public TemperatureUnit ToUnit { get; set; }
    }
}
