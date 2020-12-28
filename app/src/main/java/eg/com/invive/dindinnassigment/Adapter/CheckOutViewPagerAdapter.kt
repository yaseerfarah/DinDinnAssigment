package eg.com.invive.dindinnassigment.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import eg.com.invive.dindinnassigment.View.BlankFragment
import eg.com.invive.dindinnassigment.View.CartList


class CheckOutViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{CartList()}
            else -> {BlankFragment()}
        }
    }
}