package ec.edu.espe.rest_dotnet_bank.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ec.edu.espe.rest_dotnet_bank.R
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)

        btnLogin.setOnClickListener {
            val cuenta = etUsername.text.toString().trim()
            val clave = etPassword.text.toString().trim()
            viewModel.login(cuenta, clave)
        }

        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is OperacionResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnLogin.isEnabled = false
                }
                is OperacionResult.Success -> {
                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled = true
                    
                    // Guardar datos de la cuenta en SharedPreferences
                    val prefs = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                    prefs.edit().apply {
                        putString("cuenta", result.data.numeroCuenta)
                        putString("nombreCliente", result.data.nombreCliente)
                        putString("moneda", result.data.moneda)
                        apply()
                    }
                    
                    Toast.makeText(this, "Login exitoso - ${result.data.nombreCliente}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is OperacionResult.Error -> {
                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled = true
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
