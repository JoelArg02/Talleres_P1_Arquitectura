using CoreWCF;
using WCFService.Models;

namespace WCFService.Contracts
{
    [ServiceContract]
    public interface IService
    {
        [OperationContract]
        string GetData(int value);

        [OperationContract]
        CompositeType GetDataUsingDataContract(CompositeType composite);

        [OperationContract]
        int Add(int a, int b);

        [OperationContract]
        string GetGreeting(string name);
    }
}
