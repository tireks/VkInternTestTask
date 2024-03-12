package com.tirexmurina.vkinterntask.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tirexmurina.vkinterntask.databinding.ItemCatalogTopActiveBinding
import com.tirexmurina.vkinterntask.databinding.ItemCatalogTopBinding
import com.tirexmurina.vkinterntask.domain.entity.Category

class HomeTopAdapter (
    private val categoryClickListener: (Category) -> Unit,
    private val crossClickListener:  () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var categories: List<Category?> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
            // знаю что все надо было на DiffUtil сделать, не успел
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ACTIVE){
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCatalogTopActiveBinding.inflate(inflater, parent, false)
            ItemCategoryActiveViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCatalogTopBinding.inflate(inflater, parent, false)
            ItemCategoryViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            VIEW_TYPE_ACTIVE -> {
                (holder as ItemCategoryActiveViewHolder).bind(categories[position], crossClickListener = crossClickListener)
            }
            VIEW_TYPE_NORMAL -> {
                (holder as ItemCategoryViewHolder).bind(categories[position], categoryClickListener = categoryClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_ACTIVE
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    companion object {
        const val VIEW_TYPE_ACTIVE = 1
        const val VIEW_TYPE_NORMAL = 0
    }

}

class ItemCategoryViewHolder(
    private val binding : ItemCatalogTopBinding
) :  RecyclerView.ViewHolder(binding.root) {
    fun bind(
        category: Category?,
        categoryClickListener: (Category) -> Unit
    ) {
        if (category != null){
            binding.textview.text = category.name
            itemView.setOnClickListener { categoryClickListener(category) }
        } else {
            binding.textview.text = "Ошибка категории"
        }
    }

}

class ItemCategoryActiveViewHolder(
    private val binding : ItemCatalogTopActiveBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        category: Category?,
        crossClickListener: () -> Unit
    ) {
        if (category != null){
            binding.textview.text = category.name
            itemView.setOnClickListener { crossClickListener() }
        } else {
            binding.textview.text = "Все категории"
        }
    }

}