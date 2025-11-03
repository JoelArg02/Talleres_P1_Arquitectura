using ConversionAPI.Enums;

namespace ConversionAPI.DTOs
{
    /// <summary>
    /// DTO para solicitud de conversión de volumen
    /// </summary>
    public class VolumeConversionRequestDto
    {
        public double Value { get; set; }
        public VolumeUnit FromUnit { get; set; }
        public VolumeUnit ToUnit { get; set; }
    }
}
