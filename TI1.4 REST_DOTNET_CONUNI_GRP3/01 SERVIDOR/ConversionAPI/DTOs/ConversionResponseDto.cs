namespace ConversionAPI.DTOs
{
    /// <summary>
    /// DTO para respuesta de conversión
    /// </summary>
    public class ConversionResponseDto
    {
        public double OriginalValue { get; set; }
        public string FromUnit { get; set; } = string.Empty;
        public double ConvertedValue { get; set; }
        public string ToUnit { get; set; } = string.Empty;
        public string Category { get; set; } = string.Empty;
        public DateTime Timestamp { get; set; }
    }
}
