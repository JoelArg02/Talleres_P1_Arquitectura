using ConversionAPI.DTOs;

namespace ConversionAPI.Services
{
    /// <summary>
    /// Interfaz para el servicio de conversión de unidades
    /// </summary>
    public interface IConversionService
    {
        ConversionResponseDto ConvertLength(LengthConversionRequestDto request);
        ConversionResponseDto ConvertWeight(WeightConversionRequestDto request);
        ConversionResponseDto ConvertTemperature(TemperatureConversionRequestDto request);
        ConversionResponseDto ConvertVolume(VolumeConversionRequestDto request);
    }
}
