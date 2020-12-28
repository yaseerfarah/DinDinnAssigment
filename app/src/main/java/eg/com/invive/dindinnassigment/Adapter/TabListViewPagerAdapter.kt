package eg.com.invive.dindinnassigment.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

import eg.com.invive.dindinnassigment.Model.CategoryInfo
import eg.com.invive.dindinnassigment.View.DataList

class TabListViewPagerAdapter(fragment: Fragment, val categoriesList: List<CategoryInfo>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =categoriesList.size

    override fun createFragment(position: Int): Fragment = DataList(categoriesList[position].id)
}