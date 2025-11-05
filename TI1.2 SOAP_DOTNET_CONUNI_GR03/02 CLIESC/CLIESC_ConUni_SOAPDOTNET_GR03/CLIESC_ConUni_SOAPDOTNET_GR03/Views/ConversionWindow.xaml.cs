using CLIESC_ConUni_SOAPDOTNET_GR03.ConversionServiceRef;
using System;
using System.Threading.Tasks;
using System.Web.Services.Protocols; // Para SoapException
using System.Windows;
using System.Windows.Controls; // Necesario para ComboBoxItem

namespace CLIESC_ConUni_SOAPDOTNET_GR03
{
    public partial class ConversionWindow : Window
    {
        public ConversionWindow(string username)
        {
            InitializeComponent();

            // Establecer el nombre de usuario en el TextBlock
            txtUsuarioNombre.Text = username;
        }

        /// <summary>
        /// Este método se dispara cuando el usuario hace clic en "Cerrar sesión"
        /// </summary>
        private void cmbUserMenu_SelectionChanged(object sender, RoutedEventArgs e)
        {
            // 1. Abrir una nueva ventana de Login
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.Show();

            // 2. Cerrar esta ventana de Conversión
            this.Close();
        }

        // --- El resto de tu código de conversión no cambia en absoluto ---

        private async void btnConvertir_Click(object sender, RoutedEventArgs e)
        {
            var conversionClient = new Service();
            LimpiarResultados();
            lblError.Text = "";

            try
            {
                // 1. Lógica de Pre-conversión
                double massValue = double.Parse(txtMassValue.Text);
                string massUnit = (cmbMassUnit.SelectedItem as ComboBoxItem).Content.ToString();
                double massInKg = 0;

                if (massUnit == "Gramos")
                {
                    massInKg = massValue / 1000.0;
                }
                else if (massUnit == "Libras")
                {
                    massInKg = massValue / 2.20462262185;
                }
                else
                {
                    massInKg = massValue;
                }

                double tempValue = double.Parse(txtTempValue.Text);
                string tempUnit = (cmbTempUnit.SelectedItem as ComboBoxItem).Content.ToString();
                double tempInCelsius = 0;

                if (tempUnit == "Fahrenheit")
                {
                    tempInCelsius = (tempValue - 32) * 5.0 / 9.0;
                }
                else if (tempUnit == "Kelvin")
                {
                    tempInCelsius = tempValue - 273.15;
                }
                else
                {
                    tempInCelsius = tempValue;
                }

                // 2. Crear el 'request'
                var request = new ConversionRequest
                {
                    MassKg = massInKg,
                    MassKgSpecified = true,
                    TemperatureCelsius = tempInCelsius,
                    TemperatureCelsiusSpecified = true,
                    Latitude = double.Parse(txtLatitude.Text),
                    LatitudeSpecified = true,
                    Longitude = double.Parse(txtLongitude.Text),
                    LongitudeSpecified = true,
                    Longitude2 = double.Parse(txtLongitude2.Text),
                    Longitude2Specified = true
                };

                // 3. Pasa el 'request' al Hilo de Fondo
                var response = await Task.Run(() =>
                    conversionClient.Convert(request)
                );

                // 4. Mostrar resultado principal (basado en la conversión de masa)
                string unidadOrigen = (cmbMassUnit.SelectedItem as ComboBoxItem).Content.ToString();
                string resultadoPrincipal = "";
                
                if (unidadOrigen == "Kilogramos")
                {
                    resultadoPrincipal = $"{massValue:F2} kg = {response.MassLb:F2} lb";
                }
                else if (unidadOrigen == "Gramos")
                {
                    resultadoPrincipal = $"{massValue:F2} g = {response.MassKg:F2} kg";
                }
                else if (unidadOrigen == "Libras")
                {
                    resultadoPrincipal = $"{massValue:F2} lb = {response.MassKg:F2} kg";
                }

                lblResultado.Text = resultadoPrincipal;

                // 5. Mostrar resultados detallados
                lblResultMassLb.Text = $"Libras (Lb): {response.MassLb:F2}";
                lblResultMassG.Text = $"Gramos (G): {response.MassG:F2}";
                lblResultMassKg.Text = $"Kilogramos (Kg): {response.MassKg:F2}";

                lblResultTempF.Text = $"Fahrenheit (F): {response.TemperatureFahrenheit:F2}";
                lblResultTempK.Text = $"Kelvin (K): {response.TemperatureKelvin:F2}";
                lblResultTempC.Text = $"Celsius (C): {response.TemperatureCelsius:F2}";

                lblResultLatDMS.Text = $"Latitud 1: {FormatDMS(response.LatitudeDMS)}";
                lblResultLonDMS.Text = $"Longitud 1: {FormatDMS(response.LongitudeDMS)}";
                lblResultLon2DMS.Text = $"Longitud 2: {FormatDMS(response.Longitude2DMS)}";
            }
            catch (SoapException exSoap)
            {
                lblError.Text = $"Error de SOAP: {exSoap.Message}";
            }
            catch (FormatException)
            {
                lblError.Text = "Por favor, ingresa solo números válidos en los campos de entrada.";
            }
            catch (Exception ex)
            {
                lblError.Text = $"Error al convertir o conectar: {ex.Message}. ¿Servicio WCFService ejecutándose?";
            }
        }

        private string FormatDMS(CoordinateDMS dms)
        {
            if (dms == null) return "N/A";
            return $"{dms.Degrees}° {dms.Minutes}' {dms.Seconds:F2}\" {dms.Hemisphere}";
        }

        private void LimpiarResultados()
        {
            lblResultado.Text = "";
            lblResultMassLb.Text = "Libras (Lb): ...";
            lblResultMassG.Text = "Gramos (G): ...";
            lblResultMassKg.Text = "Kilogramos (Kg): ...";
            lblResultTempF.Text = "Fahrenheit (F): ...";
            lblResultTempK.Text = "Kelvin (K): ...";
            lblResultTempC.Text = "Celsius (C): ...";
            lblResultLatDMS.Text = "Latitud 1: ...";
            lblResultLonDMS.Text = "Longitud 1: ...";
            lblResultLon2DMS.Text = "Longitud 2: ...";
        }
    }
}