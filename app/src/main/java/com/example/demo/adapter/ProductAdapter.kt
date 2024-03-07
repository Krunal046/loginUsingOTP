package com.example.demo.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.R
import com.example.demo.model.productList.ProductListModelItem
import com.example.demo.ui.ProductDetailActivity

class ProductAdapter(private val context: Context, private var productList: ArrayList<ProductListModelItem>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.textViewTitle.text = product.title
        holder.textViewPrice.text = "$${product.price}"
        holder.textViewDescription.text = product.description
        holder.ratingBar.rating = product.rating.rate.toFloat()

        Glide.with(context)
            .load(product.image)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.imageViewProduct)

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("productId", product.id.toString())
                putString("productTitle", product.title)
                putDouble("productPrice", product.price)
                putString("productDescription", product.description)
                putFloat("productRating", product.rating.rate.toFloat())
                putString("productImage", product.image)
            }

            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("productId", product.id.toString())
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewProduct: ImageView = itemView.findViewById(R.id.imageViewProduct)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    }

    fun updateList(list:ArrayList<ProductListModelItem>){
        productList = list
        notifyDataSetChanged()
    }

}
