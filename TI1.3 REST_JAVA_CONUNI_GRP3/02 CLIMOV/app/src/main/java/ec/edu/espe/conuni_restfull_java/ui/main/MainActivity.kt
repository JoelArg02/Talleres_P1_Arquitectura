package ec.edu.espe.conuni_restfull_java.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import ec.edu.espe.conuni_restfull_java.R
import ec.edu.espe.conuni_restfull_java.databinding.ActivityMainBinding
import ec.edu.espe.conuni_restfull_java.ui.conversion.ConversionViewModel
import ec.edu.espe.conuni_restfull_java.ui.login.LoginActivity
import ec.edu.espe.conuni_restfull_java.utils.SessionManager

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ConversionViewModel by viewModels()
    private lateinit var sessionManager: SessionManager
    
    private val conversionTypes = listOf("longitud", "masa", "temperatura")
    private val tabTitles = listOf("Longitud", "Peso", "Temperatura")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        sessionManager = SessionManager(this)
        
        // Verificar si hay sesión activa
        if (!sessionManager.isLoggedIn()) {
            navigateToLogin()
            return
        }
        
        setSupportActionBar(binding.toolbar)
        setupViewPager()
        loadSupportedTypes()
    }
    
    private fun setupViewPager() {
        val adapter = ConversionPagerAdapter(this, conversionTypes)
        binding.viewPager.adapter = adapter
        
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
    
    private fun loadSupportedTypes() {
        viewModel.loadSupportedTypes()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun logout() {
        sessionManager.logout()
        navigateToLogin()
    }
    
    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
