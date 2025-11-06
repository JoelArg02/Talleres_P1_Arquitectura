using CLIESC_ConUni_SOAPDOTNET_GR03.ConversionServiceRef;
using System;
using System.Globalization;
using System.Threading.Tasks;
using System.Web.Services.Protocols;
using System.Windows;
using System.Windows.Controls;

namespace CLIESC_ConUni_SOAPDOTNET_GR03
{
    public partial class ConversionWindow : Window
    {
        private class UnitItem
        {
            public string Code { get; set; }
            public string Display { get; set; }

            public override string ToString() => Display;
        }

        private readonly UnitItem[] masaUnits = new[]
        {
            new UnitItem { Code = "mg", Display = "Miligramo (mg)" },
            new UnitItem { Code = "g", Display = "Gramo (g)" },
            new UnitItem { Code = "kg", Display = "Kilogramo (kg)" },
            new UnitItem { Code = "lb", Display = "Libra (lb)" },
            new UnitItem { Code = "oz", Display = "Onza (oz)" },
            new UnitItem { Code = "t", Display = "Tonelada (t)" }
        };

        private readonly UnitItem[] temperaturaUnits = new[]
        {
            new UnitItem { Code = "c", Display = "Celsius (°C)" },
            new UnitItem { Code = "f", Display = "Fahrenheit (°F)" },
            new UnitItem { Code = "k", Display = "Kelvin (K)" },
            new UnitItem { Code = "r", Display = "Rankine (°R)" }
        };

        private readonly UnitItem[] longitudUnits = new[]
        {
            new UnitItem { Code = "mm", Display = "Milímetro (mm)" },
            new UnitItem { Code = "cm", Display = "Centímetro (cm)" },
            new UnitItem { Code = "m", Display = "Metro (m)" },
            new UnitItem { Code = "km", Display = "Kilómetro (km)" },
            new UnitItem { Code = "in", Display = "Pulgada (in)" },
            new UnitItem { Code = "ft", Display = "Pie (ft)" }
        };

        public ConversionWindow(string username)
        {
            InitializeComponent();
            txtUsuarioNombre.Text = username;
            
            // Inicializar unidades por defecto (Masa)
            UpdateUnits();
        }

        private void cmbUserMenu_SelectionChanged(object sender, RoutedEventArgs e)
        {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.Show();
            this.Close();
        }

        private void cmbConversionType_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            UpdateUnits();
            if (resultPanel != null)
            {
                resultPanel.Visibility = Visibility.Collapsed;
            }
            if (lblError != null)
            {
                lblError.Text = "";
            }
        }

        private void UpdateUnits()
        {
            if (cmbConversionType == null || cmbInUnit == null || cmbOutUnit == null)
                return;

            var selectedType = ((ComboBoxItem)cmbConversionType.SelectedItem)?.Content?.ToString();
            
            UnitItem[] units = selectedType switch
            {
                "Masa" => masaUnits,
                "Temperatura" => temperaturaUnits,
                "Longitud" => longitudUnits,
                _ => masaUnits
            };

            cmbInUnit.ItemsSource = units;
            cmbOutUnit.ItemsSource = units;
            
            cmbInUnit.SelectedIndex = 0;
            cmbOutUnit.SelectedIndex = units.Length > 1 ? 1 : 0;
        }

        private async void btnConvertir_Click(object sender, RoutedEventArgs e)
        {
            var conversionClient = new Service();
            lblError.Text = "";
            resultPanel.Visibility = Visibility.Collapsed;

            try
            {
                if (!double.TryParse(txtValue.Text, NumberStyles.Any, CultureInfo.InvariantCulture, out double value))
                {
                    lblError.Text = "Por favor, ingresa un valor numérico válido.";
                    return;
                }

                var inUnit = (cmbInUnit.SelectedItem as UnitItem)?.Code;
                var outUnit = (cmbOutUnit.SelectedItem as UnitItem)?.Code;

                if (string.IsNullOrEmpty(inUnit) || string.IsNullOrEmpty(outUnit))
                {
                    lblError.Text = "Por favor, selecciona las unidades de conversión.";
                    return;
                }

                double result = await Task.Run(() =>
                    conversionClient.ConvertUnit(value, inUnit, outUnit)
                );

                var inUnitDisplay = (cmbInUnit.SelectedItem as UnitItem)?.Display;
                var outUnitDisplay = (cmbOutUnit.SelectedItem as UnitItem)?.Display;

                lblResultado.Text = $"{FormatNumber(value)} {inUnitDisplay} = {FormatNumber(result)} {outUnitDisplay}";
                resultPanel.Visibility = Visibility.Visible;
            }
            catch (SoapException exSoap)
            {
                lblError.Text = $"Error de SOAP: {exSoap.Message}";
            }
            catch (FormatException)
            {
                lblError.Text = "Por favor, ingresa solo números válidos.";
            }
            catch (Exception ex)
            {
                lblError.Text = $"Error al convertir: {ex.Message}. ¿Servicio ejecutándose?";
            }
        }

        private string FormatNumber(double value)
        {
            double absValue = Math.Abs(value);

            // Para números muy pequeños o muy grandes
            if ((absValue > 0 && absValue < 0.001) || absValue >= 1_000_000)
            {
                return value.ToString("0.000000e+00", CultureInfo.InvariantCulture);
            }
            // Para números normales >= 1000
            else if (absValue >= 1000)
            {
                return value.ToString("N3", CultureInfo.InvariantCulture);
            }
            // Para números menores a 1000
            else
            {
                return value.ToString("0.000", CultureInfo.InvariantCulture);
            }
        }
    }
}