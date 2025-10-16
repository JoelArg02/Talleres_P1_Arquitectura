using CoreWCF;
using WCFService.Models;

namespace WCFService.Services.Interfaces
{
    [ServiceContract]
    public interface IService
    {
        [OperationContract]
        ConversionResponse Convert(ConversionRequest request);
    }
}
