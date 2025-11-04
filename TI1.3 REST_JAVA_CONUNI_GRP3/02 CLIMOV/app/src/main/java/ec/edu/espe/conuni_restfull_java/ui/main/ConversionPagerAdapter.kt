package ec.edu.espe.conuni_restfull_java.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ec.edu.espe.conuni_restfull_java.ui.conversion.ConversionFragment

class ConversionPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val types: List<String>
) : FragmentStateAdapter(fragmentActivity) {
    
    override fun getItemCount(): Int = types.size
    
    override fun createFragment(position: Int): Fragment {
        return ConversionFragment.newInstance(types[position])
    }
}
