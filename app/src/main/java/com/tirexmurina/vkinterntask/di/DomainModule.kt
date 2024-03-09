package com.tirexmurina.vkinterntask.di

import com.tirexmurina.vkinterntask.domain.repository.ProductRepository
import com.tirexmurina.vkinterntask.domain.usecase.GetProductsUseCase
import com.tirexmurina.vkinterntask.domain.usecase.IGetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetProductsUseCase(productRepository: ProductRepository) : IGetProductsUseCase {
        return GetProductsUseCase(repository = productRepository)
    }

}