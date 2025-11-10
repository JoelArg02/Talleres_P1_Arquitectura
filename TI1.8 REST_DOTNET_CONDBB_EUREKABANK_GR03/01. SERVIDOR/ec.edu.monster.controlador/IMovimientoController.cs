using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel.Web;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using ec.edu.monster.modelo;

namespace ec.edu.monster.controlador
{
    [ServiceContract]
    public interface IMovimientoController
    {
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "/movimientos/{cuenta}", ResponseFormat = WebMessageFormat.Json)]
        List<Movimiento> ObtenerPorCuenta(string cuenta);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/deposito?cuenta={cuenta}&importe={importe}", ResponseFormat = WebMessageFormat.Json)]
        string RegistrarDeposito(string cuenta, double importe);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/retiro?cuenta={cuenta}&importe={importe}", ResponseFormat = WebMessageFormat.Json)]
        string RegistrarRetiro(string cuenta, double importe);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/transferencia?cuentaOrigen={cuentaOrigen}&cuentaDestino={cuentaDestino}&importe={importe}", ResponseFormat = WebMessageFormat.Json)]
        string RegistrarTransferencia(string cuentaOrigen, string cuentaDestino, double importe);

        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "/login?username={username}&password={password}", ResponseFormat = WebMessageFormat.Json)]
        bool Login(string username, string password);
    }
}
