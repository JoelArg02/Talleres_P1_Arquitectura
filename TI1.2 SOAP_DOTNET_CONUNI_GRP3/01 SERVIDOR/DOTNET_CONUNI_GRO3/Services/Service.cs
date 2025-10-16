using WCFService.Contracts;
using WCFService.Models;

namespace WCFService.Services
{

    public class Service : IService
    {
        private readonly ILogger<Service> _logger;

        public Service(ILogger<Service> logger)
        {
            _logger = logger;
        }

        public string GetData(int value)
        {
            _logger.LogInformation($"GetData called with value: {value}");
            return string.Format("You entered: {0}", value);
        }

        public CompositeType GetDataUsingDataContract(CompositeType composite)
        {
            _logger.LogInformation("GetDataUsingDataContract called");

            if (composite == null)
            {
                _logger.LogWarning("Composite parameter is null");
                throw new ArgumentNullException(nameof(composite));
            }

            if (composite.BoolValue)
            {
                composite.StringValue += "Suffix";
            }

            return composite;
        }

        public int Add(int a, int b)
        {
            _logger.LogInformation($"Add called with a={a}, b={b}");
            return a + b;
        }

        public string GetGreeting(string name)
        {
            _logger.LogInformation($"GetGreeting called with name: {name}");

            if (string.IsNullOrEmpty(name))
            {
                return "Hello, Guest!";
            }

            return $"Hello, {name}! Welcome to WCF Service.";
        }
    }
}
