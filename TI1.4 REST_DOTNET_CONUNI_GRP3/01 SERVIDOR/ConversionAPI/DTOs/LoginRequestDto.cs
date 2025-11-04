namespace ConversionAPI.DTOs
{
    /// <summary>
    /// DTO para solicitud de autenticacion basica.
    /// </summary>
    public class LoginRequestDto
    {
        public string Username { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
    }
}
