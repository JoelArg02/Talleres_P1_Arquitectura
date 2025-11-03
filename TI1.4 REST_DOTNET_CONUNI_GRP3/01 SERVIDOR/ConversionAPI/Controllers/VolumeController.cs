using ConversionAPI.DTOs;
using ConversionAPI.Services;
using Microsoft.AspNetCore.Mvc;

namespace ConversionAPI.Controllers
{
    /// <summary>
    /// Controlador para conversiones de volumen
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class VolumeController : ControllerBase
    {
        private readonly IConversionService _conversionService;
        private readonly ILogger<VolumeController> _logger;

        public VolumeController(IConversionService conversionService, ILogger<VolumeController> logger)
        {
            _conversionService = conversionService;
            _logger = logger;
        }

        /// <summary>
        /// Convierte unidades de volumen
        /// </summary>
        /// <param name="request">Solicitud de conversión</param>
        /// <returns>Resultado de la conversión</returns>
        [HttpPost("convert")]
        [ProducesResponseType(typeof(ConversionResponseDto), 200)]
        [ProducesResponseType(typeof(ErrorResponseDto), 400)]
        public IActionResult Convert([FromBody] VolumeConversionRequestDto request)
        {
            try
            {
                _logger.LogInformation("Convirtiendo {Value} {FromUnit} a {ToUnit}", 
                    request.Value, request.FromUnit, request.ToUnit);

                var result = _conversionService.ConvertVolume(request);
                return Ok(result);
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex, "Error en conversión de volumen");
                return BadRequest(new ErrorResponseDto
                {
                    Message = "Error en la conversión",
                    Details = ex.Message,
                    Timestamp = DateTime.UtcNow
                });
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error inesperado en conversión de volumen");
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
