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
            
            // Configurar eventos para el placeholder
            txtUsuario.GotFocus += RemovePlaceholder;
            txtUsuario.LostFocus += AddPlaceholder;
            
            // Inicializar con el placeholder
            txtUsuario.Text = "Usuario";
            txtUsuario.Foreground = System.Windows.Media.Brushes.Gray;
        }

        private void RemovePlaceholder(object sender, RoutedEventArgs e)
        {
            if (txtUsuario.Text == "Usuario")
            {
                txtUsuario.Text = "";
                txtUsuario.Foreground = System.Windows.Media.Brushes.Black;
            }
        }

        private void AddPlaceholder(object sender, RoutedEventArgs e)
        {
            if (string.IsNullOrWhiteSpace(txtUsuario.Text))
            {
                txtUsuario.Text = "Usuario";
                txtUsuario.Foreground = System.Windows.Media.Brushes.Gray;
            }
        }

        private async void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            var loginClient = new LoginService();
            lblLoginResult.Text = "Validando...";

            // 1. Leemos los valores de la UI en el Hilo de UI.
            string username = txtUsuario.Text == "Usuario" ? "" : txtUsuario.Text;
            string password = txtPassword.Password;

            // Validar que no estén vacíos
            if (string.IsNullOrWhiteSpace(username) || string.IsNullOrWhiteSpace(password))
            {
                lblLoginResult.Text = "Por favor ingresa usuario y contraseña";
                return;
            }

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
            }
        }
    }
}