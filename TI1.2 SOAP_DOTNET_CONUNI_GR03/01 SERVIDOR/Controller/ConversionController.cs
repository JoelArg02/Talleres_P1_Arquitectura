using System;
using WCFService.Services.Interfaces;

namespace WCFService.Controller
{
    public class ConversionController : IService
    {
        public double ConvertUnit(double value, string inUnit, string outUnit)
        {
            if (string.IsNullOrWhiteSpace(inUnit) || string.IsNullOrWhiteSpace(outUnit))
            {
                Console.WriteLine("Error: inUnit o outUnit es null o vacio");
                return 0.0;
            }

            string inUnitLower = inUnit.Trim().ToLower();
            string outUnitLower = outUnit.Trim().ToLower();

            Console.WriteLine($"Conversion solicitada: {value} {inUnitLower} -> {outUnitLower}");

            // Conversion de masa
            if (IsMassUnit(inUnitLower) && IsMassUnit(outUnitLower))
            {
                double baseValue = ConvertToBaseKg(value, inUnitLower);
                double result = ConvertFromBaseKg(baseValue, outUnitLower);
                Console.WriteLine($"Resultado masa: {result}");
                return result;
            }

            // Conversion de temperatura
            if (IsTemperatureUnit(inUnitLower) && IsTemperatureUnit(outUnitLower))
            {
                double baseValue = ConvertToCelsius(value, inUnitLower);
                double result = ConvertFromCelsius(baseValue, outUnitLower);
                Console.WriteLine($"Resultado temperatura: {result}");
                return result;
            }

            // Conversion de longitud
            if (IsLengthUnit(inUnitLower) && IsLengthUnit(outUnitLower))
            {
                double baseValue = ConvertToBaseMeters(value, inUnitLower);
                double result = ConvertFromBaseMeters(baseValue, outUnitLower);
                Console.WriteLine($"Resultado longitud: {result}");
                return result;
            }

            Console.WriteLine("Error: Unidades incompatibles o no reconocidas");
            return 0.0;
        }

        private bool IsMassUnit(string unit)
        {
            return unit == "mg" 
                || unit == "g" 
                || unit == "kg"
                || unit == "lb" 
                || unit == "oz" 
                || unit == "t";
        }

        private bool IsTemperatureUnit(string unit)
        {
            return unit == "c" || unit == "f" || unit == "k" || unit == "r";
        }

        private bool IsLengthUnit(string unit)
        {
            return unit == "mm" 
                || unit == "cm" 
                || unit == "m"
                || unit == "km" 
                || unit == "in" 
                || unit == "ft";
        }

        private double ConvertToBaseKg(double value, string unit)
        {
            return unit switch
            {
                "kg" => value,
                "mg" => value / 1000000.0,
                "g" => value / 1000.0,
                "lb" => value / 2.20462262185,
                "oz" => value / 35.27396195,
                "t" => value * 1000.0,
                _ => 0.0,
            };
        }

        private double ConvertFromBaseKg(double value, string unit)
        {
            return unit switch
            {
                "kg" => value,
                "mg" => value * 1000000.0,
                "g" => value * 1000.0,
                "lb" => value * 2.20462262185,
                "oz" => value * 35.27396195,
                "t" => value / 1000.0,
                _ => 0.0,
            };
        }

        private double ConvertToCelsius(double value, string unit)
        {
            return unit switch
            {
                "c" => value,
                "f" => (value - 32.0) * 5.0 / 9.0,
                "k" => value - 273.15,
                "r" => (value - 491.67) * 5.0 / 9.0,
                _ => 0.0,
            };
        }

        private double ConvertFromCelsius(double value, string unit)
        {
            return unit switch
            {
                "c" => value,
                "f" => (value * 9.0 / 5.0) + 32.0,
                "k" => value + 273.15,
                "r" => (value * 9.0 / 5.0) + 491.67,
                _ => 0.0,
            };
        }

        private double ConvertToBaseMeters(double value, string unit)
        {
            return unit switch
            {
                "m" => value,
                "mm" => value / 1000.0,
                "cm" => value / 100.0,
                "km" => value * 1000.0,
                "in" => value * 0.0254,
                "ft" => value * 0.3048,
                _ => 0.0,
            };
        }

        private double ConvertFromBaseMeters(double value, string unit)
        {
            return unit switch
            {
                "m" => value,
                "mm" => value * 1000.0,
                "cm" => value * 100.0,
                "km" => value / 1000.0,
                "in" => value / 0.0254,
                "ft" => value / 0.3048,
                _ => 0.0,
            };
        }
    }
}
