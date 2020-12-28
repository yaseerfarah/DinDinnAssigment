package eg.com.invive.dindinnassigment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import eg.com.invive.dindinnassigment.Adapter.ProductCardViewAdapter.CardViewType.*
import eg.com.invive.dindinnassigment.Model.ProductInfo
import eg.com.invive.dindinnassigment.R
import eg.com.invive.dindinnassigment.Utils.BaseDiffUtil
import eg.com.invive.dindinnassigment.Utils.loadImage
import eg.com.invive.dindinnassigment.Utils.show

class ProductCardViewAdapter(
    val context: Context,
    var productinfoList: MutableList<ProductInfo>,
    val typeView:CardViewType,
    val onItemClicked: (ProductInfo) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    enum class CardViewType{
         PRODUCT_LIST ,
         ORDER_LIST

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(typeView){
             PRODUCT_LIST ->{ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cardciew, parent, false))}
             ORDER_LIST->{OrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_cardview, parent, false))}
        }


    override fun getItemCount(): Int =productinfoList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(typeView){
            PRODUCT_LIST->{(holder as ProductHolder).bind(context,productinfoList[holder.adapterPosition],onItemClicked)}
            ORDER_LIST->{(holder as OrderHolder).bind(context,productinfoList[holder.adapterPosition],onItemClicked)}
        }

    }


    fun updateList(productInfos: List<ProductInfo>) {
        val placeInfoBaseDiffUtil: BaseDiffUtil =
            BaseDiffUtil(context, this.productinfoList, productInfos)
        val diffResult = DiffUtil.calculateDiff(placeInfoBaseDiffUtil)
        this.productinfoList.clear()
        this.productinfoList.addAll(productInfos)
        diffResult.dispatchUpdatesTo(this)
    }

//////////////////////////////////////////////////////////////////////////////////
    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(context: Context,productInfo: ProductInfo, onItemClicked: (ProductInfo) -> Unit) {
            val productImage = itemView.findViewById<ImageView>(R.id.pro_image)
            val name = itemView.findViewById<TextView>(R.id.pro_name)
            val details = itemView.findViewById<TextView>(R.id.pro_details)
            val progressBar = itemView.findViewById<ProgressBar>(R.id.progress)
            val orderButton = itemView.findViewById<MaterialButton>(R.id.order)

            progressBar.show()
            productImage.loadImage(productInfo.imageUrl,null,progressBar)
            name.text=productInfo.name
            details.text=productInfo.details

            orderButton.apply {
                setOnClickListener{onItemClicked(productInfo)}
                text=productInfo.price.toString()+" usd"


                setOnTouchListener { v, event ->
                    when(event.action){
                        MotionEvent.ACTION_DOWN->{text="added+1"}
                        MotionEvent.ACTION_UP->{text=productInfo.price.toString()+" usd"}
                        MotionEvent.ACTION_CANCEL->{text=productInfo.price.toString()+" usd"}
                    }
                    return@setOnTouchListener false
                }


            }

        }
    }



    //////////////////////////////////////////////////////////////////////////////////
    class OrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(context: Context,productInfo: ProductInfo, onItemClicked: (ProductInfo) -> Unit) {
            val productImage = itemView.findViewById<ImageView>(R.id.pro_image)
            val name = itemView.findViewById<TextView>(R.id.pro_name)
            val price = itemView.findViewById<TextView>(R.id.pro_price)
            val progressBar = itemView.findViewById<ProgressBar>(R.id.progress)
            val closeButton = itemView.findViewById<ImageButton>(R.id.close)

            progressBar.show()
            productImage.loadImage(productInfo.imageUrl,null,progressBar)
            name.text=productInfo.name
            price.text=productInfo.price.toString()+" usd"
            closeButton.setOnClickListener { onItemClicked(productInfo) }
        }
    }


}