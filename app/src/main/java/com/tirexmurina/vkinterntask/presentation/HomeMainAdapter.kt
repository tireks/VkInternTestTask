package com.tirexmurina.vkinterntask.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tirexmurina.vkinterntask.R
import com.tirexmurina.vkinterntask.databinding.ItemCatalogMainBinding
import com.tirexmurina.vkinterntask.databinding.ItemLoadingBinding
import com.tirexmurina.vkinterntask.domain.entity.Product

class HomeMainAdapter(
    private val productClickListener: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*var products: List<Product> = emptyList()
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
*/
    private var products: ArrayList<Product?> = arrayListOf()
    private var loadingStatus = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM){
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCatalogMainBinding.inflate(inflater, parent, false)
            ItemReadyViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemLoadingBinding.inflate(inflater, parent, false)
            ItemLastViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = products.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder.itemViewType){
            VIEW_TYPE_ITEM -> {
                (holder as ItemReadyViewHolder).bind(products[position], productClickListener)
            }
            VIEW_TYPE_LAST -> {
                (holder as ItemLastViewHolder).bindEmpty()
            }
            VIEW_TYPE_LOADER -> {
                (holder as ItemLastViewHolder).bindLoader()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (products[position] == null) {
            if (loadingStatus){
                VIEW_TYPE_LOADER
            } else {
                VIEW_TYPE_LAST
            }
        } else {
            VIEW_TYPE_ITEM
        }
    }

    fun rebuildData(newItems: List<Product?>){
        if (products == newItems){
            loadingStatus = false
            notifyItemChanged(products.indexOf(null))
        } else {
            if (products.isEmpty()) {
                products.clear()
                products.addAll(newItems)
                notifyDataSetChanged()
            } else {
                val changedPosition = products.indexOf(null)
                products.clear()
                products.addAll(newItems)
                removeLoadingView(changedPosition)
                loadingStatus = false
            }
        }
    }


    fun addLoadingView(){
        loadingStatus = true
        notifyItemChanged(products.indexOf(null))
    }

    private fun removeLoadingView(changedPosition: Int) {
        if (products.size != 0) {
            notifyItemChanged(changedPosition)
        }
    }

    companion object {
        const val VIEW_TYPE_LAST = 1
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADER = -1
    }


}

class ItemLastViewHolder(
    private val binding: ItemLoadingBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bindEmpty() {
        binding.progressbar.isVisible = false
    }

    fun bindLoader() {
        binding.progressbar.isVisible = true
    }

}

class ItemReadyViewHolder(
    private val binding: ItemCatalogMainBinding
): RecyclerView.ViewHolder(binding.root){
    var resources = itemView.context.resources
    fun bind(
        product: Product?,
        productClickListener: (Product) -> Unit
    ) {
        if (product != null){
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
}
/*
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
*/