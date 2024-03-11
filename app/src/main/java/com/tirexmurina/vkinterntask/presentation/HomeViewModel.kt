package com.tirexmurina.vkinterntask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tirexmurina.vkinterntask.domain.usecase.GetProductsUseCase
import com.tirexmurina.vkinterntask.domain.usecase.IGetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: IGetProductsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HomeViewState>(HomeViewState.Initial)
    val state: LiveData<HomeViewState> = _state
    private val parameters : MutableMap<String, String> = mutableMapOf(
        SKIP_STRING to "0",
        LIMIT_STRING to "20"
    )



    fun getProducts(){
        viewModelScope.launch {
            val localState = _state.value
            if (localState is HomeViewState.Content && localState.items.isNotEmpty()){
                _state.value = HomeViewState.Content(localState.items, true)
            } else {
                try{
                    val products = getProductsUseCase(parameters)
                    _state.value = HomeViewState.Content(products, true)
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
                    parameters[SKIP_STRING] = (parameters[SKIP_STRING]?.toInt()?.plus(20)).toString()
                    val beers = getProductsUseCase(parameters)
                    if (beers.isEmpty()){
                        expandAvailabilityFlag = false
                    }
                    _state.value = HomeViewState.Content(tempList + beers, expandAvailabilityFlag)
                } catch (e: Exception) {
                    _state.value = HomeViewState.Error(e.message ?: "Unknown error")
                }
            }
        }

    }

    companion object {
        const val SKIP_STRING = "skip"
        const val LIMIT_STRING = "limit"
    }

}