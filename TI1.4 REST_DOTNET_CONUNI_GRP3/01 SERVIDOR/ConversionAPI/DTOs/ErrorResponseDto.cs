namespace ConversionAPI.DTOs
{
    /// <summary>
    /// DTO para respuesta de error
    /// </summary>
    public class ErrorResponseDto
    {
        public string Message { get; set; } = string.Empty;
        public string? Details { get; set; }
        public DateTime Timestamp { get; set; }
    }
}
