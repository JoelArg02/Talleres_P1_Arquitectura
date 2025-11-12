using Newtonsoft.Json;

namespace ec.edu.pinza.modelo
{
    public class TransferenciaRequest
    {
        [JsonProperty("cuentaOrigen")]
        public string CuentaOrigen { get; set; }
        
        [JsonProperty("cuentaDestino")]
        public string CuentaDestino { get; set; }
        
        [JsonProperty("importe")]
        public double Importe { get; set; }
    }
}
