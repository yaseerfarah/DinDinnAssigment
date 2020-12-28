package eg.com.invive.dindinnassigment.Utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import java.io.File
import kotlin.math.abs


data class SnackbarOptions(
    val duration: Int = Snackbar.LENGTH_LONG,
    val actionText: String? = null,
    val actionListener: View.OnClickListener? = null
)

fun View?.snakeBar(message: String, options: SnackbarOptions = SnackbarOptions()) {
    this?.let {
        Snackbar.make(it, message, options.duration)
            .setAction(options.actionText, options.actionListener)
            .show()
    }
}

fun View?.toast(message: String) {
    this?.let {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}



// Load Image

fun ImageView.loadImage(
    imageUrl: String?,
    placeholderDrawable: Drawable? = null,
    progressBar: ProgressBar?
) {
    Glide.with(this)
        .load(imageUrl)
        .error(placeholderDrawable)
        .apply(RequestOptions.timeoutOf(60*1000))
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
        .listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            Log.e("Load Image",e!!.message!!)
            progressBar?.hide()
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

            progressBar?.hide()
            return false
        }

    })
        .into(this)
}



fun ImageView.loadImage(file: File?) {
    Glide.with(this)
        .load(file)
        .into(this)
}



// init RecyclerView

fun RecyclerView.initVerticalList(recyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>,itemDecoration:RecyclerView.ItemDecoration?=null){
    this.apply {
        layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        adapter=recyclerAdapter
        itemDecoration?.let { addItemDecoration(it) }

    }
}

fun RecyclerView.initHorizontalList(recyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>){
    this.apply {
        layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        adapter=recyclerAdapter
    }
}



fun View.hide(visibility: Int = View.GONE) {
    this.visibility = visibility
}


fun View.hideAnimY() {
    this.animate()
       // .translationY(this.height.toFloat())
        .alpha(0.0f)
        .setDuration(300)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                this@hideAnimY.hide()
            }
        })
}
fun View.hideAnimEnd() {
    this.animate()
        .translationX(-1*this.width.toFloat())
        .alpha(0.0f)
        .setDuration(300)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                this@hideAnimEnd.hide()
            }
        })
}

fun View.hideAnimStart() {
    this.animate()
        .translationX(this.width.toFloat())
        .alpha(0.0f)
        .setDuration(300)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                this@hideAnimStart.hide()
            }
        })
}




fun View.showAnimX() {
    this.animate()
        .translationX(0f)
        .alpha(1.0f)
        .setDuration(300)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                this@showAnimX.show()
            }
        })
}
fun View.showAnimY() {
    this.animate()
        //.translationY(0f)
        .alpha(1.0f)
        .setDuration(300)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                this@showAnimY.show()
            }
        })
}

fun View.show() {
    visibility = View.VISIBLE
}