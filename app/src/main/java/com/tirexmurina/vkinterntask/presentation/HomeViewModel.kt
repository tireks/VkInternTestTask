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

    fun getProducts(){
        viewModelScope.launch {
            _state.value = HomeViewState.Loading
            try {
                val products = getProductsUseCase()
                _state.value = HomeViewState.Content(products)
            } catch (e : Exception) {
                _state.value = HomeViewState.Error(e.message ?: "Unknown error")
            }
        }
    }


}