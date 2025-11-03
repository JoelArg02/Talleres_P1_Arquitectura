package ec.edu.espe.conv_rtfull.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ec.edu.espe.conv_rtfull.ui.fragments.LengthFragment
import ec.edu.espe.conv_rtfull.ui.fragments.TemperatureFragment
import ec.edu.espe.conv_rtfull.ui.fragments.VolumeFragment
import ec.edu.espe.conv_rtfull.ui.fragments.WeightFragment

/**
 * Adapter para el ViewPager2 que maneja los diferentes fragments de conversión
 */
class ConversionPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    
    override fun getItemCount(): Int = 4
    
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LengthFragment()
            1 -> WeightFragment()
            2 -> TemperatureFragment()
            3 -> VolumeFragment()
            else -> LengthFragment()
        }
    }
}
