using CLIESC_ConUni_RESTDOTNET_GR03.Services;
using System.Windows;

namespace CLIESC_ConUni_RESTDOTNET_GR03.Views
{
    public partial class LoginWindow : Window
    {
        private readonly ApiService _apiService;

        public LoginWindow()
        {
            InitializeComponent();
            _apiService = new ApiService();
            
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
            lblMensaje.Text = "Validando...";
            btnLogin.IsEnabled = false;

            // Obtener valores limpios (sin placeholder)
            string username = txtUsuario.Text == "Usuario" ? "" : txtUsuario.Text;
            string password = txtPassword.Password;

            // Validar que no estén vacíos
            if (string.IsNullOrWhiteSpace(username) || string.IsNullOrWhiteSpace(password))
            {
                lblMensaje.Text = "Por favor ingresa usuario y contraseña";
                btnLogin.IsEnabled = true;
                return;
            }

            try
            {
                bool loginExitoso = await _apiService.LoginAsync(username, password);

                if (loginExitoso)
                {
                    ConversionWindow conversionWindow = new ConversionWindow(username);
                    conversionWindow.Show();
                    this.Close();
                }
                else
                {
                    lblMensaje.Text = "Credenciales inválidas.";
                    btnLogin.IsEnabled = true;
                }
            }
            catch (System.Exception ex)
            {
                lblMensaje.Text = "Error al conectar con el servidor: " + ex.Message;
                btnLogin.IsEnabled = true;
            }
        }
    }
}