package com.tirexmurina.vkinterntask.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tirexmurina.vkinterntask.R
import com.tirexmurina.vkinterntask.databinding.ItemCatalogMainBinding
import com.tirexmurina.vkinterntask.domain.entity.Product

class HomeMainAdapter(
    private val productClickListener: (Product) -> Unit
) : RecyclerView.Adapter<HomeMainViewHolder>() {

    var products: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatalogMainBinding.inflate(inflater, parent, false)
        return  HomeMainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: HomeMainViewHolder, position: Int) {
        holder.bind(products[position], productClickListener)
    }

}

class HomeMainViewHolder(
    private val binding: ItemCatalogMainBinding
) : RecyclerView.ViewHolder(binding.root)  {
    var resources = itemView.context.resources
    fun bind(
        product: Product,
        productClickListener: (Product) -> Unit
    ) {
        with(binding){
            itemTitle.text = product.title.toString()
            itemDescription.text= product.description.toString()
            Glide.with(itemImage.context)
                .load(product.thumbnail)
                .placeholder(R.drawable.ic_recycler_view_placeholder)
                .error(R.drawable.ic_recycler_view_placeholder)
                .into(itemImage)
        }
        itemView.setOnClickListener{ productClickListener(product)}
    }


}
