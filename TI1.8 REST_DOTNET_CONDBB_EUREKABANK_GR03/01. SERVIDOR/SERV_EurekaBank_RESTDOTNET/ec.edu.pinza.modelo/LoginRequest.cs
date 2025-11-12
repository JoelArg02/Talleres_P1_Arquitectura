using Newtonsoft.Json;

namespace ec.edu.pinza.modelo
{
    public class LoginRequest
    {
        [JsonProperty("username")]
        public string Username { get; set; }
        
        [JsonProperty("password")]
        public string Password { get; set; }
    }
}
