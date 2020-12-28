package eg.com.invive.dindinnassigment.View

import android.content.Context
import android.os.Bundle
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment


open class BaseFragment(private val contentLayoutId:Int): BaseMvRxFragment(contentLayoutId) {

    val navController: NavController
    get() {return findNavController()}

    fun navigate(action: Int, bundle: Bundle? = null) {
        findNavController().navigate(action, bundle)
    }

    fun navigate(direction: NavDirections, args: Bundle? = null) {
        findNavController().navigate(direction.actionId, args)
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }

    override fun invalidate() {

    }


}