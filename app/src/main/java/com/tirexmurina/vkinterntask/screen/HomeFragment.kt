package com.tirexmurina.vkinterntask.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tirexmurina.vkinterntask.R
import com.tirexmurina.vkinterntask.databinding.FragmentHomeBinding
import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.presentation.HomeMainAdapter
import com.tirexmurina.vkinterntask.presentation.HomeViewModel
import com.tirexmurina.vkinterntask.presentation.HomeViewState
import com.tirexmurina.vkinterntask.utils.OnLoadMoreListener
import com.tirexmurina.vkinterntask.utils.RecyclerViewLoadMoreScroll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        setRecyclerViews()
        loadData()
    }



    private fun handleState(homeViewState: HomeViewState) {
        when(homeViewState){
            HomeViewState.Initial -> Unit
            is HomeViewState.Content -> showContent(homeViewState.items, homeViewState.expandAvailable)
            is HomeViewState.Error -> showError(homeViewState.errorMsg)
            HomeViewState.Loading -> showLoading()
        }
    }

    private fun setRecyclerViews() {
        val layoutManager = GridLayoutManager(context,2)
        val adapter = HomeMainAdapter(::handleProductClick)
        binding.mainCatalogList.adapter = adapter
        binding.mainCatalogList.layoutManager = layoutManager
        val scrollListener = RecyclerViewLoadMoreScroll(GridLayoutManager(context,2))
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                adapter.addLoadingView()
                lifecycleScope.launch {
                    delay(3000) //это исключительно чтобы показать, что есть загрузка (без нее слишком быстро грузит)) )
                    expandData()
                    scrollListener.setLoaded()
                }
            }
        })
        binding.mainCatalogList.addOnScrollListener(scrollListener)
    }

    private fun loadData() {
        viewModel.getProducts()
    }

    private fun expandData() {
        viewModel.expandData()
    }

    private fun showLoading() {
        Log.d("AAA", "loading")
    }

    private fun showError(errorMsg: String) {
        Log.d("AAA", "error $errorMsg")
    }

    private fun showContent(items: List<Product>, expandAvailable: Boolean) {
        Log.d("AAA", "content's here")
        with(binding){
            (mainCatalogList.adapter as? HomeMainAdapter)?.rebuildData(items + null)
            if (!expandAvailable){
                Snackbar.make(binding.mainCatalogList, getString(R.string.home_attention_snackbar_label), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.home_attention_snackbar_action)){  }
                    .show()
                mainCatalogList.clearOnScrollListeners()
            }
        }
    }

    private fun handleProductClick(product: Product) {

    }


}