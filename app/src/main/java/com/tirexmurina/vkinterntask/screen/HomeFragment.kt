package com.tirexmurina.vkinterntask.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tirexmurina.vkinterntask.R
import com.tirexmurina.vkinterntask.databinding.FragmentHomeBinding
import com.tirexmurina.vkinterntask.domain.entity.Category
import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.presentation.HomeMainAdapter
import com.tirexmurina.vkinterntask.presentation.HomeTopAdapter
import com.tirexmurina.vkinterntask.presentation.HomeViewModel
import com.tirexmurina.vkinterntask.presentation.HomeViewState
import com.tirexmurina.vkinterntask.utils.OnLoadMoreListener
import com.tirexmurina.vkinterntask.utils.RecyclerViewLoadMoreScroll
import com.tirexmurina.vkinterntask.utils.mainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var scrollListener : RecyclerViewLoadMoreScroll

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
            HomeViewState.Initial -> refreshData()
            is HomeViewState.Content -> showContent(
                homeViewState.items,
                homeViewState.expandAvailable,
                homeViewState.categories,
                homeViewState.activeCategory
            )
            is HomeViewState.Error -> showError(homeViewState.errorMsg)
            HomeViewState.Loading -> showLoading()
        }
    }



    private fun setRecyclerViews() {
        val layoutManager = GridLayoutManager(context,2)
        //это тоже можно было заинжектить, но я подумал что это избыточно
        val adapter = HomeMainAdapter(::handleProductClick)
        binding.mainCatalogList.adapter = adapter
        binding.mainCatalogList.layoutManager = layoutManager
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                adapter.addLoadingView()
                lifecycleScope.launch {
                    delay(1500) //это исключительно чтобы показать, что есть загрузка (без нее слишком быстро грузит =) )
                    expandData()
                    scrollListener.setLoaded()
                }
            }
        })
        binding.mainCatalogList.addOnScrollListener(scrollListener)

        val layoutManagerHorizontal = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapterHorizontal = HomeTopAdapter(::handleCategoryChoice, ::handleCategoryClear)
        binding.topCatalogList.adapter = adapterHorizontal
        binding.topCatalogList.layoutManager = layoutManagerHorizontal
    }

    private fun refreshData() {
        (binding.mainCatalogList.adapter as? HomeMainAdapter)?.refreshData()
        binding.mainCatalogList.scrollToPosition(0)
        binding.mainCatalogList.addOnScrollListener(scrollListener)
        binding.topCatalogList.scrollToPosition(0)
    }

    private fun loadData() {
        viewModel.getProducts()
    }

    private fun expandData() {
        viewModel.expandData()
    }

    private fun showLoading() {
        with(binding) {
            errorContent.isVisible = false
            contentContainer.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun showError(errorMsg: String) {
        Log.d("AAA", errorMsg)
        with(binding) {
            progressBar.isVisible = false
            contentContainer.isVisible = false
            errorContent.isVisible = true
            errorText.text = errorMsg
            errorButton.setOnClickListener {
                loadData()
            }
        }
    }

    private fun showContent(
        items: List<Product>,
        expandAvailable: Boolean,
        categories: List<Category>,
        activeCategory: Category?
    ) {
        with(binding){
            progressBar.isVisible = false
            errorContent.isVisible = false
            contentContainer.isVisible = true
            (mainCatalogList.adapter as? HomeMainAdapter)?.rebuildData(items + null)
            if (!expandAvailable){
                Snackbar.make(binding.mainCatalogList, getString(R.string.home_attention_snackbar_label), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.home_attention_snackbar_action)){  }
                    .show()
                mainCatalogList.clearOnScrollListeners()
            }
            val adapterDataList : List<Category?> = mutableListOf<Category?>().apply {
                add(activeCategory)
                categories.forEach { category ->
                    if (category != activeCategory) {
                        add(category)
                    }
                }
            }.toList()
            (topCatalogList.adapter as? HomeTopAdapter)?.categories = adapterDataList
        }
    }

    private fun handleProductClick(product: Product) {
        mainActivity.openDetails(product.id)
    }

    private fun handleCategoryClear() {
        viewModel.clearCategoryRequest()
    }

    private fun handleCategoryChoice(category: Category) {
        viewModel.categoryRequest(category)
    }

}