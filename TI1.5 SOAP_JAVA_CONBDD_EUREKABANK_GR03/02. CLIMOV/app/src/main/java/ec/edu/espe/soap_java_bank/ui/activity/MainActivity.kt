package ec.edu.espe.soap_java_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ec.edu.espe.soap_java_bank.R
import ec.edu.espe.soap_java_bank.ui.fragments.BalancesFragment
import ec.edu.espe.soap_java_bank.ui.fragments.DepositoFragment
import ec.edu.espe.soap_java_bank.ui.fragments.MovimientosFragment
import ec.edu.espe.soap_java_bank.ui.fragments.RetiroFragment
import ec.edu.espe.soap_java_bank.ui.fragments.TransferenciaFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        Log.d("MainActivity", "onCreate iniciado")

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            loadFragment(MovimientosFragment())
            navigationView.setCheckedItem(R.id.nav_movimientos)
            supportActionBar?.title = "Movimientos"
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", "Menu item seleccionado: ${item.title}")
        
        when (item.itemId) {
            R.id.nav_movimientos -> {
                loadFragment(MovimientosFragment())
                supportActionBar?.title = "Movimientos"
            }
            R.id.nav_retiro -> {
                loadFragment(RetiroFragment())
                supportActionBar?.title = "Retiro"
            }
            R.id.nav_deposito -> {
                loadFragment(DepositoFragment())
                supportActionBar?.title = "Depósito"
            }
            R.id.nav_transferencia -> {
                loadFragment(TransferenciaFragment())
                supportActionBar?.title = "Transferencia"
            }
            R.id.nav_balances -> {
                loadFragment(BalancesFragment())
                supportActionBar?.title = "Balances"
            }
            R.id.nav_logout -> {
                Log.d("MainActivity", "Cerrando sesión")
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        Log.d("MainActivity", "Cargando fragment: ${fragment.javaClass.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
