using System.Runtime.Serialization;

namespace WCFService.Models
{
    [DataContract]
    public class LoginResponse
    {
        [DataMember]
        public bool Success { get; set; }

        [DataMember]
        public string Message { get; set; }

        [DataMember]
        public string Token { get; set; }
    }
}
