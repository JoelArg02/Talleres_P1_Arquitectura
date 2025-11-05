using Newtonsoft.Json;

namespace CLIESC_ConUni_RESTDOTNET_GR03.Models
{
    // --- ENUMS (Copiados de tus archivos de servidor) ---
    //
    public enum LengthUnit { Meters, Kilometers, Centimeters, Millimeters, Miles, Yards, Feet, Inches }
    //
    public enum WeightUnit { Kilograms, Grams, Milligrams, Pounds, Ounces, Tons }
    //
    public enum TemperatureUnit { Celsius, Fahrenheit, Kelvin }
    //
    public enum VolumeUnit { Liters, Milliliters, CubicMeters, Gallons, Quarts, Pints, Cups }
    //
    // (UnitCategory no es necesario para los DTOs de conversión)


    // --- DTOs (Ahora usan los Enums de arriba) ---

    public class LoginRequestDto
    {
        [JsonProperty("username")]
        public string Username { get; set; }

        [JsonProperty("password")]
        public string Password { get; set; }
    }

    // El DTO de respuesta del servidor
    public class ConversionResponseDto
    {
        public double OriginalValue { get; set; }
        public string FromUnit { get; set; }
        public double ConvertedValue { get; set; }
        public string ToUnit { get; set; }
    }

    // --- DTOs de Solicitud (Request) ---

    public class LengthConversionRequestDto
    {
        [JsonProperty("value")]
        public double Value { get; set; }
        [JsonProperty("fromUnit")]
        public LengthUnit FromUnit { get; set; } // <-- CAMBIO (de string a enum)
        [JsonProperty("toUnit")]
        public LengthUnit ToUnit { get; set; }   // <-- CAMBIO
    }

    public class WeightConversionRequestDto
    {
        [JsonProperty("value")]
        public double Value { get; set; }
        [JsonProperty("fromUnit")]
        public WeightUnit FromUnit { get; set; } // <-- CAMBIO
        [JsonProperty("toUnit")]
        public WeightUnit ToUnit { get; set; }   // <-- CAMBIO
    }

    public class TemperatureConversionRequestDto
    {
        [JsonProperty("value")]
        public double Value { get; set; }
        [JsonProperty("fromUnit")]
        public TemperatureUnit FromUnit { get; set; } // <-- CAMBIO
        [JsonProperty("toUnit")]
        public TemperatureUnit ToUnit { get; set; }   // <-- CAMBIO
    }

    public class VolumeConversionRequestDto
    {
        [JsonProperty("value")]
        public double Value { get; set; }
        [JsonProperty("fromUnit")]
        public VolumeUnit FromUnit { get; set; } // <-- CAMBIO
        [JsonProperty("toUnit")]
        public VolumeUnit ToUnit { get; set; }   // <-- CAMBIO
    }
}