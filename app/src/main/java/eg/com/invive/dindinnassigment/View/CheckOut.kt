package eg.com.invive.dindinnassigment.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.tabs.TabLayoutMediator
import eg.com.invive.dindinnassigment.Adapter.CheckOutViewPagerAdapter
import eg.com.invive.dindinnassigment.R
import eg.com.invive.dindinnassigment.ViewModel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_check_out.*
import kotlinx.android.synthetic.main.fragment_check_out.tabLayout

class CheckOut : BaseFragment(R.layout.fragment_check_out) {

    private val orderViewModel: OrderViewModel by activityViewModel()
    private val checkOutTabsList= mutableListOf("Cart","Order","Information")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun invalidate() {
        withState(orderViewModel){state->
            if (state.products is Success){
                var total=0
                state.products.invoke().forEach { total+=it.price }
                totalPrice.text= "$total usd"
            }
        }
    }

    private fun initViews(){
        backButton.setOnClickListener { navigateUp() }
        viewPager.adapter = CheckOutViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text=checkOutTabsList[position]
        }.attach()
    }
}