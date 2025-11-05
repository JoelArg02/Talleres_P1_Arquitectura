using CLIESC_ConUni_SOAPDOTNET_GR03.LoginServiceRef;
using System;
using System.Threading.Tasks;
using System.Web.Services.Protocols; // Para SoapException
using System.Windows;

namespace CLIESC_ConUni_SOAPDOTNET_GR03
{
    public partial class LoginWindow : Window
    {
        public LoginWindow()
        {
            InitializeComponent();
        }

        private async void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            var loginClient = new LoginService();
            lblLoginResult.Text = "Validando...";

            // 1. Leemos los valores de la UI en el Hilo de UI.
            string username = txtUsuario.Text;
            string password = txtPassword.Password;

            try
            {
                // 2. Pasamos las variables al Hilo de Fondo.
                var response = await Task.Run(() =>
                    loginClient.ValidarCredenciales(username, password)
                );

                // 3. Volvemos al Hilo de UI para comprobar la respuesta
                if (response.Success)
                {
                    // ¡Login exitoso!
                    // Creamos la ventana de conversión pasándole el nombre de usuario
                    ConversionWindow conversionWindow = new ConversionWindow(username);
                    conversionWindow.Show();

                    // Cerramos esta ventana de Login
                    this.Close();
                }
                else
                {
                    // Credenciales inválidas
                    lblLoginResult.Text = response.Message;
                }
            }
            catch (SoapException exSoap)
            {
                lblLoginResult.Text = $"Error de SOAP: {exSoap.Message}";
            }
            catch (Exception ex)
            {
                lblLoginResult.Text = $"Error de conexión: {ex.Message}";
                MessageBox.Show($"No se pudo conectar al servicio de login. ¿Servicio ejecutándose en http://localhost:5001?", "Error de Conexión");
            }
        }
    }
}