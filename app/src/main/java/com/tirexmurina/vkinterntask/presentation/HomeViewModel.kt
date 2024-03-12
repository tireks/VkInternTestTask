package com.tirexmurina.vkinterntask.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tirexmurina.vkinterntask.domain.entity.Category
import com.tirexmurina.vkinterntask.domain.usecase.GetProductsUseCase
import com.tirexmurina.vkinterntask.domain.usecase.IGetCategoriesUseCase
import com.tirexmurina.vkinterntask.domain.usecase.IGetProductsByCategoryUseCase
import com.tirexmurina.vkinterntask.domain.usecase.IGetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: IGetProductsUseCase,
    private val getCategoriesUseCase: IGetCategoriesUseCase,
    private val getProductsByCategoryUseCase: IGetProductsByCategoryUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HomeViewState>(HomeViewState.Initial)
    val state: LiveData<HomeViewState> = _state
    private val parameters : MutableMap<String, String> = mutableMapOf(
        SKIP_STRING to "0",
        LIMIT_STRING to "20"
    )
    private var activeCategory : Category? = null


    fun getProducts(){
        viewModelScope.launch {
            val localState = _state.value
            if (localState is HomeViewState.Content && localState.items.isNotEmpty()){
                Log.d("AAA", "point skip")
                _state.value = HomeViewState.Content(
                    localState.items,
                    true,
                    localState.categories,
                    localState.activeCategory
                )
            } else {
                _state.value = HomeViewState.Loading
                try{
                    Log.d("AAA", "getting new data")
                    val products =
                        if (activeCategory == null){
                            getProductsUseCase(parameters)
                        } else {
                            getProductsByCategoryUseCase(activeCategory!!, parameters)
                        }
                    val prodString = products.joinToString(separator = ", ") { it.title }
                    Log.d("AAA", prodString)
                    val categories = getCategoriesUseCase()
                    _state.value = HomeViewState.Content(
                        products,
                        true,
                        categories,
                        activeCategory
                    )
                }catch (e: Exception) {
                    _state.value = HomeViewState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    fun expandData() {
        viewModelScope.launch {
            var expandAvailabilityFlag = true
            val localState = _state.value
            if (localState is HomeViewState.Content){
                val tempList = localState.items
                try {
                    parameters[SKIP_STRING] = (parameters[SKIP_STRING]?.toInt()?.plus(20)).toString() // todo обязательно сделать определение какая активкатегори и сделать разные параметры в зависимости от результата
                    val products =
                        if (activeCategory == null){
                            getProductsUseCase(parameters)
                        } else {
                            getProductsByCategoryUseCase(activeCategory!!, parameters)
                        }
                    if (products.isEmpty()){
                        expandAvailabilityFlag = false
                    }
                    _state.value = HomeViewState.Content(
                        tempList + products,
                        expandAvailabilityFlag,
                        localState.categories,
                        localState.activeCategory
                    )
                } catch (e: Exception) {
                    _state.value = HomeViewState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    /*private fun getProductsByCategory(category: Category){
        viewModelScope.launch {
            val localState = _state.value
            try {
                val products = getProductsByCategoryUseCase(category, parameters)
                val categories = getCategoriesUseCase()
                _state.value = HomeViewState.Content(
                    products,
                    true,
                    categories,
                    activeCategory
                )
            } catch (e: Exception){
                _state.value = HomeViewState.Error(e.message ?: "Unknown error")
            }
        }
    }*/

    fun clearCategoryRequest(){
        activeCategory = null
        parameters[SKIP_STRING] = "0"
        _state.value = HomeViewState.Initial
        getProducts()
    }

    fun categoryRequest(category: Category){
        activeCategory = category
        parameters[SKIP_STRING] = "0"
        _state.value = HomeViewState.Initial
        getProducts()
    }

    companion object {
        const val SKIP_STRING = "skip"
        const val LIMIT_STRING = "limit"
    }

}