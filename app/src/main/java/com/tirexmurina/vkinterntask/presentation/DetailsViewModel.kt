package com.tirexmurina.vkinterntask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tirexmurina.vkinterntask.domain.usecase.IGetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getProductByIdUseCase: IGetProductByIdUseCase
): ViewModel(){

    private val _state = MutableLiveData<DetailsViewState>(DetailsViewState.Initial)
    val state: LiveData<DetailsViewState> = _state

    fun getProductById(id: String){
        viewModelScope.launch {
            _state.value = DetailsViewState.Loading
            try{
                _state.value = DetailsViewState.Content(getProductByIdUseCase(id))
            } catch(e: Exception) {
                _state.value = DetailsViewState.Error(e.message.toString())
            }
        }
    }

}