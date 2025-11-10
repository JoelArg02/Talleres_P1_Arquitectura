using System.Collections.Generic;
using System.ServiceModel;
using ec.edu.monster.modelo;

namespace ec.edu.monster.controlador
{
    [ServiceContract]
    public interface IMovimientoController
    {
        [OperationContract]
        List<Movimiento> ObtenerPorCuenta(string cuenta);

        [OperationContract]
        string RegistrarDeposito(string cuenta, double importe);

        [OperationContract]
        string RegistrarRetiro(string cuenta, double importe);

        [OperationContract]
        string RegistrarTransferencia(string cuentaOrigen, string cuentaDestino, double importe);

        [OperationContract]
        bool Login(string username, string password);
    }
}
