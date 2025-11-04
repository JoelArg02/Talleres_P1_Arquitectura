package ec.edu.espe.conuni_restfull_java.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ec.edu.espe.conuni_restfull_java.ui.conversion.LongitudFragment
import ec.edu.espe.conuni_restfull_java.ui.conversion.MasaFragment
import ec.edu.espe.conuni_restfull_java.ui.conversion.TemperaturaFragment

class ConversionPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    
    override fun getItemCount(): Int = 3
    
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LongitudFragment()
            1 -> MasaFragment()
            2 -> TemperaturaFragment()
            else -> LongitudFragment()
        }
    }
}
