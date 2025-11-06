using ConversionAPI.Enums;
using System.Text.Json.Serialization;

namespace ConversionAPI.DTOs
{
    public class WeightConversionRequestDto
    {
        public double Value { get; set; }
        
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public WeightUnit FromUnit { get; set; }
        
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public WeightUnit ToUnit { get; set; }
    }
}
