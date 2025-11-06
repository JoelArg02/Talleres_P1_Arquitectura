using CoreWCF;

namespace WCFService.Services.Interfaces
{
    [ServiceContract]
    public interface IService
    {
        [OperationContract]
        double ConvertUnit(double value, string inUnit, string outUnit);
    }
}
