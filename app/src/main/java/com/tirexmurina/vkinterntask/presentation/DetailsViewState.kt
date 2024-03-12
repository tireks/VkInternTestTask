package com.tirexmurina.vkinterntask.presentation

import com.tirexmurina.vkinterntask.domain.entity.Product

sealed class DetailsViewState {
    data object Initial : DetailsViewState()
    data object Loading : DetailsViewState()
    data class Content (val item: Product): DetailsViewState()
    data class Error(val errorMsg: String) : DetailsViewState()
}
