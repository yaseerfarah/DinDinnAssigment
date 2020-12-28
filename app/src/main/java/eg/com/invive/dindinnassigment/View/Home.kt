package eg.com.invive.dindinnassigment.View

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.google.android.material.tabs.TabLayoutMediator
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import eg.com.invive.dindinnassigment.Adapter.ImageSliderAdapter
import eg.com.invive.dindinnassigment.Adapter.TabListViewPagerAdapter
import eg.com.invive.dindinnassigment.Model.CategoryInfo
import eg.com.invive.dindinnassigment.R
import eg.com.invive.dindinnassigment.Utils.hide
import eg.com.invive.dindinnassigment.Utils.show
import eg.com.invive.dindinnassigment.Utils.toast

import eg.com.invive.dindinnassigment.ViewModel.HomeResponseViewModel
import eg.com.invive.dindinnassigment.ViewModel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_home2.*
import q.rorbin.badgeview.QBadgeView


class Home : BaseFragment(R.layout.fragment_home2) {

    private val categoriesList= mutableListOf<CategoryInfo>()
    private val sliderImages= mutableListOf<String>()
    private val homeResponseViewModel:HomeResponseViewModel by activityViewModel()
    private val orderViewModel: OrderViewModel by activityViewModel()
    private lateinit var imageSliderViewAdapter:ImageSliderAdapter
    private lateinit var orderBadge:QBadgeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        if (categoriesList.isNotEmpty()){
            initViews()
        }

    }

    override fun onStart() {
        super.onStart()
        orderBadge = QBadgeView(requireContext())
            .setBadgeBackgroundColor(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    resources.getColor(R.color.green, null)
                else resources.getColor(R.color.green)
            )
            .setBadgeTextSize(resources.getDimension(R.dimen._3ssp), true)
            .bindTarget(checkOutButton) as QBadgeView

    }

    override fun invalidate() {
       withState(homeResponseViewModel){homeState->
           when(homeState.categories){
               is Loading->{statefulLayout.showLoading("")}
               is Success ->{
                   if (categoriesList.isEmpty()) {

                       categoriesList.addAll(homeState.categories.invoke().categoriesList)
                       sliderImages.addAll(homeState.categories.invoke().sliderImages)
                       imageSliderViewAdapter = ImageSliderAdapter(requireContext(), sliderImages)

                       initViews()
                   }else{}

               }
               is Fail->{

                   Log.e(tag,homeState.categories.error.message?:"Error")
               }
               Uninitialized -> {}
           }



       }


        withState(orderViewModel){orderState->
            if (orderState.products is Success){
                orderBadge.badgeNumber=orderState.products.invoke().size

            }
        }

    }



    private fun initViews() {
        statefulLayout.showContent()
        slider_view.apply {
            setSliderAdapter(imageSliderViewAdapter)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
            setInfiniteAdapterEnabled(false)
            scrollTimeInSec = 3
            isAutoCycle = true
            startAutoCycle()
        }
        val tabListViewPagerAdapter = TabListViewPagerAdapter(this, categoriesList)
        viewPager2.adapter = tabListViewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text=categoriesList[position].name
        }.attach()

        checkOutButton.setOnClickListener { navigate(R.id.action_home2_to_checkOut) }
    }

    }