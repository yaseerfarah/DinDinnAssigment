package eg.com.invive.dindinnassigment.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import eg.com.invive.dindinnassigment.Adapter.ProductCardViewAdapter
import eg.com.invive.dindinnassigment.Model.ProductInfo
import eg.com.invive.dindinnassigment.R
import eg.com.invive.dindinnassigment.Utils.initVerticalList
import eg.com.invive.dindinnassigment.ViewModel.OrderViewModel
import eg.com.invive.dindinnassigment.ViewModel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_data_list.*
import kotlinx.android.synthetic.main.fragment_data_list.statefulLayout

class CartList() : BaseFragment(R.layout.fragment_data_list) {

    private val productCartList= mutableListOf<ProductInfo>()
    private val orderViewModel:OrderViewModel by activityViewModel()
    private val productCardViewAdapter
            by lazy { ProductCardViewAdapter(requireContext(), mutableListOf(),ProductCardViewAdapter.CardViewType.ORDER_LIST){
                orderViewModel.removeProductFromOrder(it)
            } }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun invalidate() {
        withState(orderViewModel){state->
            when(state.products){
                is Loading->{statefulLayout.showLoading("")}
                is Success->{
                    if (state.products.invoke().size!=productCartList.size) {
                        statefulLayout.showContent()
                        productCartList.clear()
                        productCartList.addAll(state.products.invoke())
                        productCardViewAdapter.updateList(productCartList)

                    }else{}
                }
                is Fail->{ statefulLayout.showContent()
                    Log.e(tag,state.products.error.message?:"Error")}
               is  Uninitialized -> {}
            }
        }
    }

    private fun initViews(){
        productsRecycler.initVerticalList(productCardViewAdapter)
    }
}