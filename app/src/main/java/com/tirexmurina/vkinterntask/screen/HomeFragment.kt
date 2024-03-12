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
import com.google.android.material.snackbar.Snackbar
import com.tirexmurina.vkinterntask.R
import com.tirexmurina.vkinterntask.databinding.FragmentHomeBinding
import com.tirexmurina.vkinterntask.domain.entity.Product
import com.tirexmurina.vkinterntask.presentation.HomeMainAdapter
import com.tirexmurina.vkinterntask.presentation.HomeViewModel
import com.tirexmurina.vkinterntask.presentation.HomeViewState
import com.tirexmurina.vkinterntask.utils.OnLoadMoreListener
import com.tirexmurina.vkinterntask.utils.RecyclerViewLoadMoreScroll
import com.tirexmurina.vkinterntask.utils.mainActivity
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
        //это тоже можно было заинжектить, но я подумал что это избыточно
        val adapter = HomeMainAdapter(::handleProductClick)
        binding.mainCatalogList.adapter = adapter
        binding.mainCatalogList.layoutManager = layoutManager
        //тут то же самое, все равно листенер прикрепляется и прописывается в фрагменте
        //так же возможно стоило листенер создавать во вьюмодели, и передавать его во фрагмент,
        // но тогда пришлось бы как минимум контекст передать в вьюмодель, а это не очень хорошо, кажется
        val scrollListener = RecyclerViewLoadMoreScroll(GridLayoutManager(context,2))
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                adapter.addLoadingView()
                lifecycleScope.launch {
                    delay(1500) //это исключительно чтобы показать, что есть загрузка (без нее слишком быстро грузит)) )
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

    private fun showContent(items: List<Product>, expandAvailable: Boolean) {
        Log.d("AAA", "content's here")
        with(binding){
            progressBar.isVisible = false
            errorContent.isVisible = false
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
        mainActivity.openDetails(product.id)
    }


}