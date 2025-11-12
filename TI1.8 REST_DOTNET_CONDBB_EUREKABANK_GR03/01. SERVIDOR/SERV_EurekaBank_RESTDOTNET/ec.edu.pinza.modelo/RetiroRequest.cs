using Newtonsoft.Json;

namespace ec.edu.pinza.modelo
{
    public class RetiroRequest
    {
        [JsonProperty("cuenta")]
        public string Cuenta { get; set; }
        
        [JsonProperty("importe")]
        public double Importe { get; set; }
    }
}
