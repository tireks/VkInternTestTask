package com.tirexmurina.vkinterntask.di

import com.tirexmurina.vkinterntask.data.remote.ProductAPI
import com.tirexmurina.vkinterntask.data.remote.ProductConverter
import com.tirexmurina.vkinterntask.data.remote.ProductRepositoryImpl
import com.tirexmurina.vkinterntask.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit) : ProductAPI{
        return retrofit.create(ProductAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideConverter(): ProductConverter = ProductConverter()


    @Provides
    @Singleton
    fun provideProductRepository(
        service: ProductAPI,
        converter: ProductConverter
    ) : ProductRepository {
        return ProductRepositoryImpl(service = service, converter = converter)
    }

}