using WCFService.Services.Interfaces;
using WCFService.Models;

namespace WCFService.Services
{
    public class LoginService : ILoginService
    {
        private static readonly string USUARIO_FIJO = "MONSTER";
        private static readonly string CONTRASENA_FIJA = "MONSTER9";

        public LoginResponse ValidarCredenciales(string username, string password)
        {
            bool esUsuarioCorrecto = USUARIO_FIJO.Equals(username);
            bool esContrasenaCorrecta = CONTRASENA_FIJA.Equals(password);

            bool credencialesValidas = esUsuarioCorrecto && esContrasenaCorrecta;

            var response = new LoginResponse
            {
                Success = credencialesValidas,
                Message = credencialesValidas ? "Login exitoso" : "Credenciales inválidas",
                Token = credencialesValidas ? GenerarToken() : string.Empty
            };

            return response;
        }

        private static string GenerarToken()
        {
            return Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes($"MONSTER-{DateTime.UtcNow.Ticks}"));
        }
    }
}
