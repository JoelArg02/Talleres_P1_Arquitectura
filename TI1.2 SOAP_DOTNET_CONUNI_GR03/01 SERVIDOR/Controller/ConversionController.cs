using System;
using Microsoft.Extensions.Logging;
using WCFService.Services.Interfaces;
using WCFService.Models;

namespace WCFService.Controller
{
    public class ConversionController : IService
    {
        public ConversionResponse Convert(ConversionRequest request)
        {
            Console.WriteLine("=== INICIO CONVERSION ===");
            
            if (request == null)
            {
                Console.WriteLine("ERROR: Request es null");
                throw new ArgumentNullException(nameof(request));
            }

            Console.WriteLine($"MassKg recibido: {request.MassKg}");
            Console.WriteLine($"TemperatureCelsius recibido: {request.TemperatureCelsius}");
            Console.WriteLine($"Longitude2 recibido: {request.Longitude2}");

            var resp = new ConversionResponse
            {
                LatitudeDecimal = request.Latitude,
                LongitudeDecimal = request.Longitude,
                LatitudeRadians = DegreesToRadians(request.Latitude),
                LongitudeRadians = DegreesToRadians(request.Longitude),
                LatitudeDMS = ToDMS(request.Latitude, true),
                LongitudeDMS = ToDMS(request.Longitude, false),
                MassKg = request.MassKg,
                MassG = request.MassKg * 1000.0,
                MassLb = request.MassKg * 2.20462262185,
                TemperatureCelsius = request.TemperatureCelsius,
                TemperatureFahrenheit = (request.TemperatureCelsius * 9.0 / 5.0) + 32.0,
                TemperatureKelvin = request.TemperatureCelsius + 273.15,
                Longitude2Decimal = request.Longitude2,
                Longitude2Radians = DegreesToRadians(request.Longitude2),
                Longitude2DMS = ToDMS(request.Longitude2, false)
            };

            Console.WriteLine($"Mass calculada - Kg: {resp.MassKg}, Lb: {resp.MassLb}, G: {resp.MassG}");
            Console.WriteLine($"Temp calculada - C: {resp.TemperatureCelsius}, F: {resp.TemperatureFahrenheit}, K: {resp.TemperatureKelvin}");
            Console.WriteLine($"Long2 calculada - Decimal: {resp.Longitude2Decimal}, Radians: {resp.Longitude2Radians}");
            Console.WriteLine("=== FIN CONVERSION ===");

            return resp;
        }


        private static double DegreesToRadians(double degrees) => degrees * Math.PI / 180.0;

        private static CoordinateDMS ToDMS(double degreesDecimal, bool isLatitude)
        {
            var hemisphere = isLatitude ? (degreesDecimal >= 0 ? 'N' : 'S') : (degreesDecimal >= 0 ? 'E' : 'W');
            var deg = Math.Abs(degreesDecimal);
            var degrees = (int)Math.Floor(deg);
            var minutesFull = (deg - degrees) * 60.0;
            var minutes = (int)Math.Floor(minutesFull);
            var seconds = (minutesFull - minutes) * 60.0;

            return new CoordinateDMS
            {
                Degrees = degrees,
                Minutes = minutes,
                Seconds = Math.Round(seconds, 6),
                Hemisphere = hemisphere
            };
        }
    }
}
