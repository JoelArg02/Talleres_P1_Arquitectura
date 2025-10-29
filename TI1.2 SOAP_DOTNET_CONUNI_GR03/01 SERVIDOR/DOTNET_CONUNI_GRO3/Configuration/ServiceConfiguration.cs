using CoreWCF;
using CoreWCF.Configuration;
using CoreWCF.Description;
using WCFService.Services.Interfaces;
using WCFService.Services;

namespace WCFService.Configuration
{
    public static class ServiceConfiguration
    {
        public static void ConfigureWCFServices(this IServiceCollection services)
        {
            services.AddServiceModelServices();
            services.AddServiceModelMetadata();
            services.AddSingleton<IServiceBehavior, UseRequestHeadersForMetadataAddressBehavior>();
        }

        public static void ConfigureWCFEndpoints(this WebApplication app, IConfiguration configuration)
        {
            var serviceUrl = configuration["ServiceSettings:BaseUrl"] ?? "http://localhost:5000";

            app.UseServiceModel(serviceBuilder =>
            {
                serviceBuilder.AddService<Service>(serviceOptions =>
                {
                    serviceOptions.BaseAddresses.Add(new Uri(serviceUrl));
                });

                serviceBuilder.AddServiceEndpoint<Service, IService>(
                    new BasicHttpBinding(),
                    "/Service.svc"
                );

                serviceBuilder.AddService<WCFService.Controller.ConversionController>(serviceOptions =>
                {
                    serviceOptions.BaseAddresses.Add(new Uri(serviceUrl));
                });

                serviceBuilder.AddServiceEndpoint<WCFService.Controller.ConversionController, IService>(
                    new BasicHttpBinding(),
                    "/Conversion.svc"
                );

                var serviceMetadataBehavior = app.Services.GetRequiredService<ServiceMetadataBehavior>();
                serviceMetadataBehavior.HttpGetEnabled = true;
                serviceMetadataBehavior.HttpGetUrl = new Uri($"{serviceUrl}/Service.svc");
            });

            var logger = app.Services.GetRequiredService<ILogger<Program>>();
            logger.LogInformation($"WCF Service is running on {serviceUrl}/Service.svc");
            logger.LogInformation($"WSDL is available at {serviceUrl}/Service.svc?wsdl");
        }
    }
}
