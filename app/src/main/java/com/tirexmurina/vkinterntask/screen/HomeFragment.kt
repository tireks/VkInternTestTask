package com.tirexmurina.vkinterntask.screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tirexmurina.vkinterntask.R
import com.tirexmurina.vkinterntask.databinding.FragmentHomeBinding
import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.presentation.HomeMainAdapter
import com.tirexmurina.vkinterntask.presentation.HomeViewModel
import com.tirexmurina.vkinterntask.presentation.HomeViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel : HomeViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context,2)
        binding.mainCatalogList.adapter = HomeMainAdapter(::handleProductClick)
        binding.mainCatalogList.layoutManager = layoutManager
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        loadData()
    }

    private fun handleState(homeViewState: HomeViewState) {
        when(homeViewState){
            HomeViewState.Initial -> Unit
            is HomeViewState.Content -> showContent(homeViewState.items)
            is HomeViewState.Error -> showError(homeViewState.errorMsg)
            HomeViewState.Loading -> showLoading()
        }
    }

    private fun loadData() {
        viewModel.getProducts()
    }

    private fun showLoading() {
        Log.d("AAA", "loading")
    }

    private fun showError(errorMsg: String) {
        Log.d("AAA", "error $errorMsg")
    }

    private fun showContent(items: List<Product>) {
        Log.d("AAA", "content's here")
        with(binding){
            (mainCatalogList.adapter as? HomeMainAdapter)?.products = items
        }
    }

    private fun handleProductClick(product: Product) {

    }


}