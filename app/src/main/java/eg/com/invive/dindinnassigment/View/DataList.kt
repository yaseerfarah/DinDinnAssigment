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

class DataList(val categoryID:Int) : BaseFragment(R.layout.fragment_data_list) {

    private val productList= mutableListOf<ProductInfo>()
    private val productViewModel:ProductViewModel by fragmentViewModel()
    private val orderViewModel:OrderViewModel by activityViewModel()
    private val productCardViewAdapter
            by lazy { ProductCardViewAdapter(requireContext(),productList,ProductCardViewAdapter.CardViewType.PRODUCT_LIST){
                orderViewModel.addToProductOrder(it)
            } }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (productList.isNotEmpty()){
            productsRecycler.initVerticalList(productCardViewAdapter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productViewModel.getProductsByCategory(categoryID)
    }

    override fun invalidate() {
        withState(productViewModel){state->
            when(state.products){
                is Loading->{statefulLayout.showLoading("")}
                is Success->{
                    if (productList.isEmpty()) {
                        statefulLayout.showContent()
                        productList.addAll(state.products.invoke())
                        productsRecycler.initVerticalList(productCardViewAdapter)
                    }else{}
                }
                is Fail->{ statefulLayout.showContent()
                    Log.e(tag,state.products.error.message?:"Error")}
               is  Uninitialized -> {}
            }
        }
    }
}