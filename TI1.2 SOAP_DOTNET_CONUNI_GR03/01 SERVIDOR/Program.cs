using WCFService.Configuration;

var builder = WebApplication.CreateBuilder(args);

builder.Services.ConfigureWCFServices();

builder.Services.AddCors(o => o.AddPolicy("AllowAll", p => p.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader()));

builder.Logging.ClearProviders();
builder.Logging.AddConsole();
builder.Logging.AddDebug();

builder.WebHost.ConfigureKestrel(o =>
{
    o.ListenAnyIP(5001);
});

var app = builder.Build();

app.UseCors("AllowAll");

app.ConfigureWCFEndpoints(builder.Configuration);

Console.WriteLine("===========================================");
Console.WriteLine("Servicio escuchando en todos los orígenes e IPs");
Console.WriteLine("===========================================");
Console.WriteLine("Presiona Ctrl+C para detener el servicio");
Console.WriteLine("===========================================");

app.Run();
