using System;
using System.Linq;
using ConversionAPI.DTOs;
using Microsoft.AspNetCore.Mvc;

namespace ConversionAPI.Controllers
{
    /// <summary>
    /// Controlador sencillo para autenticacion por credenciales fijas.
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class AuthController : ControllerBase
    {
        private static readonly (string Username, string Password)[] ValidUsers = new[]
        {
            ("admin", "1234"),
            ("MONSTER", "MONSTER9")
        };

        private readonly ILogger<AuthController> _logger;

        public AuthController(ILogger<AuthController> logger)
        {
            _logger = logger;
        }

        /// <summary>
        /// Valida credenciales y devuelve true en caso de exito.
        /// </summary>
        [HttpPost("login")]
        [ProducesResponseType(typeof(bool), 200)]
        [ProducesResponseType(typeof(ErrorResponseDto), 400)]
        public IActionResult Login([FromBody] LoginRequestDto request)
        {
            if (request == null ||
                string.IsNullOrWhiteSpace(request.Username) ||
                string.IsNullOrWhiteSpace(request.Password))
            {
                _logger.LogWarning("Autenticacion fallida por datos incompletos");
                return BadRequest(new ErrorResponseDto
                {
                    Message = "Error en la autenticacion",
                    Details = "Debe proporcionar usuario y contrasena.",
                    Timestamp = DateTime.UtcNow
                });
            }

            bool autorizado = ValidUsers.Any(user =>
                string.Equals(user.Username, request.Username.Trim(), StringComparison.OrdinalIgnoreCase) &&
                user.Password == request.Password);

            _logger.LogInformation("Autenticacion para {Username}: {Resultado}", request.Username, autorizado ? "EXITOSA" : "FALLIDA");
            return Ok(autorizado);
        }
    }
}
