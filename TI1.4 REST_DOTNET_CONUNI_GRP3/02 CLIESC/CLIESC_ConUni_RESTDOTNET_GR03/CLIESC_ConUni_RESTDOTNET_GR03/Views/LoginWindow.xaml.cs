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
        }

        private async void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            lblMensaje.Text = "Validando...";
            btnLogin.IsEnabled = false;

            try
            {
                bool loginExitoso = await _apiService.LoginAsync(
                    txtUsuario.Text,
                    txtPassword.Password
                );

                if (loginExitoso)
                {
                    // --- CAMBIO: Pasar el nombre de usuario a la ConversionWindow ---
                    string username = txtUsuario.Text;
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
                lblMensaje.Text = "Error al conectar con el servidor.";
                // Opcional: Mostrar más detalles
                MessageBox.Show("Error: " + ex.Message, "Error de Conexión");
                btnLogin.IsEnabled = true;
            }
        }
    }
}