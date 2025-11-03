using ConversionAPI.DTOs;
using ConversionAPI.Enums;

namespace ConversionAPI.Services
{
    /// <summary>
    /// Servicio de conversión de unidades
    /// </summary>
    public class ConversionService : IConversionService
    {
        /// <summary>
        /// Traduce el nombre de las unidades al español
        /// </summary>
        private string TranslateUnitToSpanish(string unit)
        {
            return unit switch
            {
                // Longitud
                "Meters" => "Metros",
                "Kilometers" => "Kilómetros",
                "Centimeters" => "Centímetros",
                "Millimeters" => "Milímetros",
                "Miles" => "Millas",
                "Yards" => "Yardas",
                "Feet" => "Pies",
                "Inches" => "Pulgadas",
                
                // Peso
                "Kilograms" => "Kilogramos",
                "Grams" => "Gramos",
                "Milligrams" => "Miligramos",
                "Pounds" => "Libras",
                "Ounces" => "Onzas",
                "Tons" => "Toneladas",
                
                // Temperatura
                "Celsius" => "Celsius",
                "Fahrenheit" => "Fahrenheit",
                "Kelvin" => "Kelvin",
                
                // Volumen
                "Liters" => "Litros",
                "Milliliters" => "Mililitros",
                "CubicMeters" => "Metros Cúbicos",
                "Gallons" => "Galones",
                "Quarts" => "Cuartos",
                "Pints" => "Pintas",
                "Cups" => "Tazas",
                
                _ => unit
            };
        }
        
        /// <summary>
        /// Traduce la categoría al español
        /// </summary>
        private string TranslateCategoryToSpanish(string category)
        {
            return category switch
            {
                "Length" => "Longitud",
                "Weight" => "Peso",
                "Temperature" => "Temperatura",
                "Volume" => "Volumen",
                _ => category
            };
        }

        public ConversionResponseDto ConvertLength(LengthConversionRequestDto request)
        {
            // Convertir a metros (unidad base)
            double valueInMeters = request.FromUnit switch
            {
                LengthUnit.Meters => request.Value,
                LengthUnit.Kilometers => request.Value * 1000,
                LengthUnit.Centimeters => request.Value / 100,
                LengthUnit.Millimeters => request.Value / 1000,
                LengthUnit.Miles => request.Value * 1609.344,
                LengthUnit.Yards => request.Value * 0.9144,
                LengthUnit.Feet => request.Value * 0.3048,
                LengthUnit.Inches => request.Value * 0.0254,
                _ => throw new ArgumentException("Unidad de origen no válida")
            };

            // Convertir de metros a la unidad de destino
            double result = request.ToUnit switch
            {
                LengthUnit.Meters => valueInMeters,
                LengthUnit.Kilometers => valueInMeters / 1000,
                LengthUnit.Centimeters => valueInMeters * 100,
                LengthUnit.Millimeters => valueInMeters * 1000,
                LengthUnit.Miles => valueInMeters / 1609.344,
                LengthUnit.Yards => valueInMeters / 0.9144,
                LengthUnit.Feet => valueInMeters / 0.3048,
                LengthUnit.Inches => valueInMeters / 0.0254,
                _ => throw new ArgumentException("Unidad de destino no válida")
            };

            return new ConversionResponseDto
            {
                OriginalValue = request.Value,
                FromUnit = TranslateUnitToSpanish(request.FromUnit.ToString()),
                ConvertedValue = result,
                ToUnit = TranslateUnitToSpanish(request.ToUnit.ToString()),
                Category = TranslateCategoryToSpanish("Length"),
                Timestamp = DateTime.UtcNow
            };
        }

        public ConversionResponseDto ConvertWeight(WeightConversionRequestDto request)
        {
            // Convertir a kilogramos (unidad base)
            double valueInKilograms = request.FromUnit switch
            {
                WeightUnit.Kilograms => request.Value,
                WeightUnit.Grams => request.Value / 1000,
                WeightUnit.Milligrams => request.Value / 1_000_000,
                WeightUnit.Pounds => request.Value * 0.453592,
                WeightUnit.Ounces => request.Value * 0.0283495,
                WeightUnit.Tons => request.Value * 1000,
                _ => throw new ArgumentException("Unidad de origen no válida")
            };

            // Convertir de kilogramos a la unidad de destino
            double result = request.ToUnit switch
            {
                WeightUnit.Kilograms => valueInKilograms,
                WeightUnit.Grams => valueInKilograms * 1000,
                WeightUnit.Milligrams => valueInKilograms * 1_000_000,
                WeightUnit.Pounds => valueInKilograms / 0.453592,
                WeightUnit.Ounces => valueInKilograms / 0.0283495,
                WeightUnit.Tons => valueInKilograms / 1000,
                _ => throw new ArgumentException("Unidad de destino no válida")
            };

            return new ConversionResponseDto
            {
                OriginalValue = request.Value,
                FromUnit = TranslateUnitToSpanish(request.FromUnit.ToString()),
                ConvertedValue = result,
                ToUnit = TranslateUnitToSpanish(request.ToUnit.ToString()),
                Category = TranslateCategoryToSpanish("Weight"),
                Timestamp = DateTime.UtcNow
            };
        }

        public ConversionResponseDto ConvertTemperature(TemperatureConversionRequestDto request)
        {
            // Convertir a Celsius (unidad base)
            double valueInCelsius = request.FromUnit switch
            {
                TemperatureUnit.Celsius => request.Value,
                TemperatureUnit.Fahrenheit => (request.Value - 32) * 5 / 9,
                TemperatureUnit.Kelvin => request.Value - 273.15,
                _ => throw new ArgumentException("Unidad de origen no válida")
            };

            // Convertir de Celsius a la unidad de destino
            double result = request.ToUnit switch
            {
                TemperatureUnit.Celsius => valueInCelsius,
                TemperatureUnit.Fahrenheit => (valueInCelsius * 9 / 5) + 32,
                TemperatureUnit.Kelvin => valueInCelsius + 273.15,
                _ => throw new ArgumentException("Unidad de destino no válida")
            };

            return new ConversionResponseDto
            {
                OriginalValue = request.Value,
                FromUnit = TranslateUnitToSpanish(request.FromUnit.ToString()),
                ConvertedValue = result,
                ToUnit = TranslateUnitToSpanish(request.ToUnit.ToString()),
                Category = TranslateCategoryToSpanish("Temperature"),
                Timestamp = DateTime.UtcNow
            };
        }

        public ConversionResponseDto ConvertVolume(VolumeConversionRequestDto request)
        {
            // Convertir a litros (unidad base)
            double valueInLiters = request.FromUnit switch
            {
                VolumeUnit.Liters => request.Value,
                VolumeUnit.Milliliters => request.Value / 1000,
                VolumeUnit.CubicMeters => request.Value * 1000,
                VolumeUnit.Gallons => request.Value * 3.78541,
                VolumeUnit.Quarts => request.Value * 0.946353,
                VolumeUnit.Pints => request.Value * 0.473176,
                VolumeUnit.Cups => request.Value * 0.236588,
                _ => throw new ArgumentException("Unidad de origen no válida")
            };

            // Convertir de litros a la unidad de destino
            double result = request.ToUnit switch
            {
                VolumeUnit.Liters => valueInLiters,
                VolumeUnit.Milliliters => valueInLiters * 1000,
                VolumeUnit.CubicMeters => valueInLiters / 1000,
                VolumeUnit.Gallons => valueInLiters / 3.78541,
                VolumeUnit.Quarts => valueInLiters / 0.946353,
                VolumeUnit.Pints => valueInLiters / 0.473176,
                VolumeUnit.Cups => valueInLiters / 0.236588,
                _ => throw new ArgumentException("Unidad de destino no válida")
            };

            return new ConversionResponseDto
            {
                OriginalValue = request.Value,
                FromUnit = TranslateUnitToSpanish(request.FromUnit.ToString()),
                ConvertedValue = result,
                ToUnit = TranslateUnitToSpanish(request.ToUnit.ToString()),
                Category = TranslateCategoryToSpanish("Volume"),
                Timestamp = DateTime.UtcNow
            };
        }
    }
}
