using CLIESC_ConUni_RESTDOTNET_GR03.Models;
using Newtonsoft.Json;
using System.Net.Http; // <-- ARREGLO 1 (para HttpClientHandler)
using System.Text;
using System.Threading.Tasks;

namespace CLIESC_ConUni_RESTDOTNET_GR03.Services
{
    public class ApiService
    {
        private readonly HttpClient _httpClient;

        // ¡¡IMPORTANTE!! Ajusta esta URL a la de tu servidor
        private const string BaseUrl = "https://localhost:5001/api";

        public ApiService()
        {
            // Opcional: Ignorar errores de certificado SSL (solo para desarrollo)
            var handler = new HttpClientHandler
            {
                ServerCertificateCustomValidationCallback = (message, cert, chain, errors) => true
            };
            _httpClient = new HttpClient(handler);
        }

        public async Task<bool> LoginAsync(string username, string password)
        {
            var request = new LoginRequestDto
            {
                Username = username,
                Password = password
            };

            // 1. Serializar manualmente a JSON (usando Newtonsoft)
            string jsonBody = JsonConvert.SerializeObject(request);
            // 2. Crear StringContent
            var httpContent = new StringContent(jsonBody, Encoding.UTF8, "application/json");

            var response = await _httpClient.PostAsync($"{BaseUrl}/Auth/login", httpContent);

            if (response.IsSuccessStatusCode)
            {
                // 3. Leer la respuesta como string y deserializar
                string jsonResponse = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<bool>(jsonResponse);
            }
            return false;
        }

        public async Task<ConversionResponseDto> ConvertAsync(string type, object requestDto)
        {
            string endpoint;
            if (type == "Longitud")
                endpoint = "Length/convert";
            else if (type == "Peso")
                endpoint = "Weight/convert";
            else if (type == "Temperatura")
                endpoint = "Temperature/convert";
            else if (type == "Volumen")
                endpoint = "Volume/convert";
            else
                throw new System.ArgumentException("Tipo no válido");

            // 1. Serializar el DTO a JSON
            string jsonBody = JsonConvert.SerializeObject(requestDto);
            // 2. Crear StringContent
            var httpContent = new StringContent(jsonBody, Encoding.UTF8, "application/json");

            var response = await _httpClient.PostAsync($"{BaseUrl}/{endpoint}", httpContent);

            if (response.IsSuccessStatusCode)
            {
                // 3. Deserializar la respuesta
                string jsonResponse = await response.Content.ReadAsStringAsync();
                return JsonConvert.DeserializeObject<ConversionResponseDto>(jsonResponse);
            }
            return null;
        }
    }
}