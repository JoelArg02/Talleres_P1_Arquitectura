using ConversionAPI.DTOs;
using ConversionAPI.Services;
using Microsoft.AspNetCore.Mvc;

namespace ConversionAPI.Controllers
{
    /// <summary>
    /// Controlador para conversiones de peso
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class WeightController : ControllerBase
    {
        private readonly IConversionService _conversionService;
        private readonly ILogger<WeightController> _logger;

        public WeightController(IConversionService conversionService, ILogger<WeightController> logger)
        {
            _conversionService = conversionService;
            _logger = logger;
        }

        /// <summary>
        /// Convierte unidades de peso
        /// </summary>
        /// <param name="request">Solicitud de conversión</param>
        /// <returns>Resultado de la conversión</returns>
        [HttpPost("convert")]
        [ProducesResponseType(typeof(ConversionResponseDto), 200)]
        [ProducesResponseType(typeof(ErrorResponseDto), 400)]
        public IActionResult Convert([FromBody] WeightConversionRequestDto request)
        {
            try
            {
                _logger.LogInformation("Convirtiendo {Value} {FromUnit} a {ToUnit}", 
                    request.Value, request.FromUnit, request.ToUnit);

                var result = _conversionService.ConvertWeight(request);
                return Ok(result);
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex, "Error en conversión de peso");
                return BadRequest(new ErrorResponseDto
                {
                    Message = "Error en la conversión",
                    Details = ex.Message,
                    Timestamp = DateTime.UtcNow
                });
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error inesperado en conversión de peso");
                return StatusCode(500, new ErrorResponseDto
                {
                    Message = "Error interno del servidor",
                    Details = ex.Message,
                    Timestamp = DateTime.UtcNow
                });
            }
        }
    }
}
