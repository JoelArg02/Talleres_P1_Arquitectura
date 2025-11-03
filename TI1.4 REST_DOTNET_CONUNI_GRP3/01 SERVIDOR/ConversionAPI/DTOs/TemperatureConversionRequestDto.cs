using ConversionAPI.Enums;

namespace ConversionAPI.DTOs
{
    /// <summary>
    /// DTO para solicitud de conversión de temperatura
    /// </summary>
    public class TemperatureConversionRequestDto
    {
        public double Value { get; set; }
        public TemperatureUnit FromUnit { get; set; }
        public TemperatureUnit ToUnit { get; set; }
    }
}
