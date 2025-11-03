using ConversionAPI.Enums;

namespace ConversionAPI.DTOs
{
    /// <summary>
    /// DTO para solicitud de conversión de peso
    /// </summary>
    public class WeightConversionRequestDto
    {
        public double Value { get; set; }
        public WeightUnit FromUnit { get; set; }
        public WeightUnit ToUnit { get; set; }
    }
}
