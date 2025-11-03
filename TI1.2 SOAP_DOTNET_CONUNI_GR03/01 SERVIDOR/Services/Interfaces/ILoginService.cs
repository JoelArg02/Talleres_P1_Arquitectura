using CoreWCF;
using WCFService.Models;

namespace WCFService.Services.Interfaces
{
    [ServiceContract]
    public interface ILoginService
    {
        [OperationContract]
        LoginResponse ValidarCredenciales(string username, string password);
    }
}
