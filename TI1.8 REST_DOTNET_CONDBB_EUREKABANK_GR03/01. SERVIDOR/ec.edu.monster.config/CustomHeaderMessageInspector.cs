using System.ServiceModel.Dispatcher;
using System.ServiceModel.Channels;
using System.ServiceModel.Web;
using System.ServiceModel;

public class CustomHeaderMessageInspector : IDispatchMessageInspector
{
    public object AfterReceiveRequest(ref Message request, IClientChannel channel, InstanceContext instanceContext)
    {
        // Verifica si es una solicitud OPTIONS
        var context = WebOperationContext.Current;
        if (context != null && context.IncomingRequest.Method == "OPTIONS")
        {
            var response = context.OutgoingResponse;
            response.StatusCode = System.Net.HttpStatusCode.OK;
            AddCorsHeaders(response);
        }
        return null;
    }

    public void BeforeSendReply(ref Message reply, object correlationState)
    {
        var response = WebOperationContext.Current?.OutgoingResponse;
        if (response != null)
        {
            AddCorsHeaders(response);
        }
    }

    private void AddCorsHeaders(OutgoingWebResponseContext response)
    {
        response.Headers.Add("Access-Control-Allow-Origin", "*");
        response.Headers.Add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.Headers.Add("Access-Control-Allow-Headers", "Content-Type, Accept");
    }
}
