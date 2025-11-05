package ec.edu.espe.conv_rtfull

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ec.edu.espe.conv_rtfull.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object {
        const val PREFS_NAME = "LoginPrefs"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val KEY_USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            navigateToMain()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            attemptLogin()
        }
    }

    private fun attemptLogin() {
        val username = binding.usernameInput.text.toString()
        val password = binding.passwordInput.text.toString()

        if (username == "MONSTER" && password == "MONSTER9") {
            val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putBoolean(KEY_IS_LOGGED_IN, true)
                putString(KEY_USERNAME, username)
                apply()
            }
            navigateToMain()
        } else {
            Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
