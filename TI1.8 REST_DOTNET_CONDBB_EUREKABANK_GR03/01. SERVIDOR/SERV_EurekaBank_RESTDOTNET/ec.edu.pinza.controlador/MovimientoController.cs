using System;
using System.Collections.Generic;
using System.Web.Http;
using ec.edu.pinza.modelo;
using ec.edu.pinza.servicio;

namespace ec.edu.pinza.controlador
{
    [RoutePrefix("api/movimiento")]
    public class MovimientoController : ApiController
    {
        // GET: api/movimiento/listar?cuenta=00100001
        [HttpGet]
        [Route("listar")]
        public IHttpActionResult ObtenerPorCuenta(string cuenta)
        {
            try
            {
                if (string.IsNullOrWhiteSpace(cuenta))
                {
                    return BadRequest("El número de cuenta es requerido.");
                }

                var movimientos = MovimientoServicio.ListarPorCuenta(cuenta);
                return Ok(movimientos);
            }
            catch (Exception ex)
            {
                return InternalServerError(new Exception("Error al obtener movimientos: " + ex.Message));
            }
        }

        // POST: api/movimiento/deposito
        [HttpPost]
        [Route("deposito")]
        public IHttpActionResult RegistrarDeposito([FromBody] DepositoRequest request)
        {
            try
            {
                if (request == null || string.IsNullOrWhiteSpace(request.Cuenta) || request.Importe <= 0)
                {
                    return BadRequest("Datos de depósito inválidos.");
                }

                MovimientoServicio.RegistrarDeposito(request.Cuenta, request.Importe, "0001");
                return Ok(new { mensaje = "Depósito registrado exitosamente.", resultado = "1" });
            }
            catch (Exception ex)
            {
                return InternalServerError(new Exception("Error al registrar depósito: " + ex.Message));
            }
        }

        // POST: api/movimiento/retiro
        [HttpPost]
        [Route("retiro")]
        public IHttpActionResult RegistrarRetiro([FromBody] RetiroRequest request)
        {
            try
            {
                if (request == null || string.IsNullOrWhiteSpace(request.Cuenta) || request.Importe <= 0)
                {
                    return BadRequest("Datos de retiro inválidos.");
                }

                MovimientoServicio.RegistrarRetiro(request.Cuenta, request.Importe, "0001");
                return Ok(new { mensaje = "Retiro registrado exitosamente.", resultado = "1" });
            }
            catch (Exception ex)
            {
                return InternalServerError(new Exception("Error al registrar retiro: " + ex.Message));
            }
        }

        // POST: api/movimiento/transferencia
        [HttpPost]
        [Route("transferencia")]
        public IHttpActionResult RegistrarTransferencia([FromBody] TransferenciaRequest request)
        {
            try
            {
                if (request == null || string.IsNullOrWhiteSpace(request.CuentaOrigen) || 
                    string.IsNullOrWhiteSpace(request.CuentaDestino) || request.Importe <= 0)
                {
                    return BadRequest("Datos de transferencia inválidos.");
                }

                if (request.CuentaOrigen == request.CuentaDestino)
                {
                    return BadRequest("La cuenta origen y destino no pueden ser iguales.");
                }

                MovimientoServicio.RegistrarTransferencia(request.CuentaOrigen, request.CuentaDestino, request.Importe, "0001");
                return Ok(new { mensaje = "Transferencia registrada exitosamente.", resultado = "1" });
            }
            catch (Exception ex)
            {
                return InternalServerError(new Exception("Error al registrar transferencia: " + ex.Message));
            }
        }

        // POST: api/movimiento/login
        [HttpPost]
        [Route("login")]
        public IHttpActionResult Login([FromBody] LoginRequest request)
        {
            try
            {
                if (request == null || string.IsNullOrWhiteSpace(request.Username) || string.IsNullOrWhiteSpace(request.Password))
                {
                    return BadRequest("Usuario y contraseña son requeridos.");
                }

                bool resultado = MovimientoServicio.Login(request.Username, request.Password);
                
                if (resultado)
                {
                    return Ok(new { mensaje = "Login exitoso.", autenticado = true });
                }
                else
                {
                    return Ok(new { mensaje = "Credenciales inválidas.", autenticado = false });
                }
            }
            catch (Exception ex)
            {
                return InternalServerError(new Exception("Error al autenticar: " + ex.Message));
            }
        }

        // GET: api/movimiento/balances
        [HttpGet]
        [Route("balances")]
        public IHttpActionResult TraerBalances()
        {
            try
            {
                var balances = MovimientoServicio.ListarBalances();
                return Ok(balances);
            }
            catch (Exception ex)
            {
                return InternalServerError(new Exception("Error al obtener balances: " + ex.Message));
            }
        }
    }
}
