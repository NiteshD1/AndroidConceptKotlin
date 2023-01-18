package com.androidready.demo.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidready.demo.MainApplication
import com.androidready.demo.R
import com.androidready.demo.data.models.Product
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerViewAdapterForProducts(private val mList: MutableList<Product>,val isSavedProduct : Boolean,val dbCallback: DbCallback) : RecyclerView.Adapter<RecyclerViewAdapterForProducts.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_product_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = mList[position]

        Glide.with(MainApplication.appContext)
            .load(product.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imageView)

        holder.textViewName.text = product.name.toString()
        holder.textViewPrice.text = product.price.toString() + "/-"

        if(isSavedProduct) holder.buttonSave.text = "Delete"

        holder.buttonSave.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                if(isSavedProduct){
                    mList.remove(product)
                    withContext(Dispatchers.Main){
                        notifyDataSetChanged()
                    }
                    dbCallback.removeProductFromDb(product)
                }else{
                    dbCallback.addProductToDb(product)
                }
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.product_image)
        val textViewName: TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        val buttonSave: TextView = itemView.findViewById(R.id.buttonSave)
    }
}