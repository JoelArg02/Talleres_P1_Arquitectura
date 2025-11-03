package ec.edu.espe.conv_rtfull

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import ec.edu.espe.conv_rtfull.databinding.ActivityMainBinding
import ec.edu.espe.conv_rtfull.ui.adapter.ConversionPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Conversor de Unidades"

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ConversionPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Longitud"
                1 -> "Peso"
                2 -> "Temperatura"
                3 -> "Volumen"
                else -> "Tab $position"
            }
        }.attach()
    }
}