package ec.edu.espe.conuni_restfull_java.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ec.edu.espe.conuni_restfull_java.databinding.ActivityLoginBinding
import ec.edu.espe.conuni_restfull_java.ui.main.MainActivity
import ec.edu.espe.conuni_restfull_java.utils.SessionManager

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sessionManager = SessionManager(this)
        
        // Si ya hay sesión activa, ir a MainActivity
        if (sessionManager.isLoggedIn()) {
            navigateToMain()
            return
        }
        
        setupListeners()
        observeViewModel()
    }
    
    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.login(username, password)
        }
    }
    
    private fun observeViewModel() {
        viewModel.loginResult.observe(this) { success ->
            if (success) {
                val username = binding.etUsername.text.toString().trim()
                sessionManager.saveSession(username)
                Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()
                navigateToMain()
            }
        }
        
        viewModel.errorMessage.observe(this) { message ->
            binding.tvError.text = message
            binding.tvError.visibility = View.VISIBLE
        }
        
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnLogin.isEnabled = !isLoading
            if (isLoading) {
                binding.tvError.visibility = View.GONE
            }
        }
    }
    
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
