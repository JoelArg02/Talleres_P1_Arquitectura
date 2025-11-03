using ConversionAPI.Enums;

namespace ConversionAPI.DTOs
{
    /// <summary>
    /// DTO para solicitud de conversión de longitud
    /// </summary>
    public class LengthConversionRequestDto
    {
        public double Value { get; set; }
        public LengthUnit FromUnit { get; set; }
        public LengthUnit ToUnit { get; set; }
    }
}
