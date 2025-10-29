using WCFService.Configuration;

var builder = WebApplication.CreateBuilder(args);

builder.Services.ConfigureWCFServices();

builder.Logging.ClearProviders();
builder.Logging.AddConsole();
builder.Logging.AddDebug();

var app = builder.Build();

app.ConfigureWCFEndpoints(builder.Configuration);

Console.WriteLine("===========================================");
Console.WriteLine("WCF Service iniciado con arquitectura MVC");
Console.WriteLine("===========================================");
Console.WriteLine($"Servicio: http://localhost:5000/Service.svc");
Console.WriteLine($"WSDL:     http://localhost:5000/Service.svc?wsdl");
Console.WriteLine("===========================================");
Console.WriteLine("Presiona Ctrl+C para detener el servicio");
Console.WriteLine("===========================================");

app.Run();
