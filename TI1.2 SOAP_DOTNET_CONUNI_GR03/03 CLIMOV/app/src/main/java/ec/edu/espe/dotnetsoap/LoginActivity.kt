package ec.edu.espe.dotnetsoap

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ec.edu.espe.dotnetsoap.data.repository.LoginRepository
import ec.edu.espe.dotnetsoap.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginRepository = LoginRepository()
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val TAG = "LoginActivity"
        private const val PREFS_NAME = "LoginPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        if (isUserLoggedIn()) {
            navigateToMain()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            attemptLogin()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    private fun attemptLogin() {
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        Log.d(TAG, "Login attempt for user: $username")

        if (username.isEmpty()) {
            binding.usernameInput.error = "Usuario requerido"
            binding.usernameInput.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.passwordInput.error = "Contraseña requerida"
            binding.passwordInput.requestFocus()
            return
        }

        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = loginRepository.login(username, password)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (result.success) {
                        sharedPreferences.edit().apply {
                            putBoolean(KEY_IS_LOGGED_IN, true)
                            putString(KEY_USERNAME, username)
                            apply()
                        }

                        Toast.makeText(
                            this@LoginActivity,
                            "Bienvenido, $username",
                            Toast.LENGTH_SHORT
                        ).show()

                        navigateToMain()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            result.message,
                            Toast.LENGTH_LONG
                        ).show()
                        binding.passwordInput.setText("")
                        binding.passwordInput.requestFocus()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@LoginActivity,
                        "Error al conectar: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !show
        binding.usernameInput.isEnabled = !show
        binding.passwordInput.isEnabled = !show
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
