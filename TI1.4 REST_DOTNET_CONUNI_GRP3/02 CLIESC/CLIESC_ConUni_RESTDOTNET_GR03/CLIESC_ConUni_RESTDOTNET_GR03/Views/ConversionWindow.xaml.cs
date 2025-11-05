using CLIESC_ConUni_RESTDOTNET_GR03.Models;
using CLIESC_ConUni_RESTDOTNET_GR03.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows;
using System.Windows.Controls;

namespace CLIESC_ConUni_RESTDOTNET_GR03.Views
{
    public partial class ConversionWindow : Window
    {
        private readonly ApiService _apiService;

        // Lista de los tipos principales
        private readonly List<string> tiposDeConversion = new List<string> { "Longitud", "Peso", "Temperatura", "Volumen" };

        // === CAMBIO: El constructor ahora recibe el nombre de usuario ===
        public ConversionWindow(string username)
        {
            InitializeComponent();
            _apiService = new ApiService();

            // Configurar el ComboBox de Tipos
            cmbTipo.ItemsSource = tiposDeConversion;
            cmbTipo.SelectedIndex = 0;

            // --- NUEVO: Configurar el ComboBox de Usuario ---
            // 1. Añade el nombre de usuario (deshabilitado)
            cmbUserMenu.Items.Add(new ComboBoxItem
            {
                Content = $"Usuario: {username}",
                IsEnabled = false
            });
            // 2. Añade la opción de Logout
            cmbUserMenu.Items.Add(new ComboBoxItem
            {
                Content = "Cerrar Sesión"
            });
            // 3. Establece el ítem por defecto
            cmbUserMenu.SelectedIndex = 0;
        }

        /// <summary>
        /// NUEVO: Maneja el clic en el menú de usuario
        /// </summary>
        private void cmbUserMenu_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbUserMenu.SelectedItem == null) return;
            var selectedItem = cmbUserMenu.SelectedItem as ComboBoxItem;
            if (selectedItem == null) return;

            // Comprobar si se seleccionó "Cerrar Sesión"
            if (selectedItem.Content.ToString() == "Cerrar Sesión")
            {
                // 1. Abrir una nueva ventana de Login
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.Show();

                // 2. Cerrar esta ventana de Conversión
                this.Close();
            }
        }

        // --- El resto del código es el de la conversión (con la corrección de Enums) ---

        private void cmbTipo_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbTipo.SelectedItem == null) return;
            string tipoSeleccionado = cmbTipo.SelectedItem.ToString();

            System.Collections.IEnumerable unidades = null;

            switch (tipoSeleccionado)
            {
                case "Longitud":
                    unidades = Enum.GetValues(typeof(LengthUnit)).Cast<LengthUnit>();
                    break;
                case "Peso":
                    unidades = Enum.GetValues(typeof(WeightUnit)).Cast<WeightUnit>();
                    break;
                case "Temperatura":
                    unidades = Enum.GetValues(typeof(TemperatureUnit)).Cast<TemperatureUnit>();
                    break;
                case "Volumen":
                    unidades = Enum.GetValues(typeof(VolumeUnit)).Cast<VolumeUnit>();
                    break;
            }

            cmbUnidadDe.ItemsSource = unidades;
            cmbUnidadA.ItemsSource = unidades;
            cmbUnidadDe.SelectedIndex = 0;
            cmbUnidadA.SelectedIndex = 1;
        }

        private async void btnConvertir_Click(object sender, RoutedEventArgs e)
        {
            lblResultado.Text = "Convirtiendo...";
            btnConvertir.IsEnabled = false;

            try
            {
                string tipo = cmbTipo.SelectedItem.ToString();
                object fromUnit = cmbUnidadDe.SelectedItem;
                object toUnit = cmbUnidadA.SelectedItem;
                double value = double.Parse(txtValor.Text);

                ConversionResponseDto resultado = null;
                object requestDto = null;

                switch (tipo)
                {
                    case "Longitud":
                        requestDto = new LengthConversionRequestDto { Value = value, FromUnit = (LengthUnit)fromUnit, ToUnit = (LengthUnit)toUnit };
                        break;
                    case "Peso":
                        requestDto = new WeightConversionRequestDto { Value = value, FromUnit = (WeightUnit)fromUnit, ToUnit = (WeightUnit)toUnit };
                        break;
                    case "Temperatura":
                        requestDto = new TemperatureConversionRequestDto { Value = value, FromUnit = (TemperatureUnit)fromUnit, ToUnit = (TemperatureUnit)toUnit };
                        break;
                    case "Volumen":
                        requestDto = new VolumeConversionRequestDto { Value = value, FromUnit = (VolumeUnit)fromUnit, ToUnit = (VolumeUnit)toUnit };
                        break;
                }

                if (requestDto != null)
                {
                    resultado = await _apiService.ConvertAsync(tipo, requestDto);
                }

                if (resultado != null)
                {
                    lblResultado.Text = $"Resultado: {resultado.ConvertedValue:F2} {resultado.ToUnit}";
                }
                else
                {
                    lblResultado.Text = "Error en la conversión.";
                }
            }
            catch (System.Exception ex)
            {
                lblResultado.Text = "Error: " + ex.Message;
            }
            finally
            {
                btnConvertir.IsEnabled = true;
            }
        }
    }
}