package com.tirexmurina.vkinterntask.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.tirexmurina.vkinterntask.data.remote.DummyjsonAPI
import com.tirexmurina.vkinterntask.data.remote.category.CategoryConverter
import com.tirexmurina.vkinterntask.data.remote.category.CategoryModel
import com.tirexmurina.vkinterntask.data.remote.category.CategoryModelDeserializer
import com.tirexmurina.vkinterntask.data.remote.category.CategoryRepositoryImpl
import com.tirexmurina.vkinterntask.data.remote.product.ProductConverter
import com.tirexmurina.vkinterntask.data.remote.product.ProductRepositoryImpl
import com.tirexmurina.vkinterntask.domain.repository.CategoryRepository
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
    fun provideCategoryModelDeserializer() : JsonDeserializer<CategoryModel> {
        return CategoryModelDeserializer()
    }

    @Provides
    @Singleton
    fun provideRetrofit(deserializer: JsonDeserializer<CategoryModel>) : Retrofit{
        return Retrofit.Builder().baseUrl("https://dummyjson.com/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(CategoryModel::class.java, deserializer)
                        .create()
                )
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) : DummyjsonAPI{
        return retrofit.create(DummyjsonAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideProductConverter(): ProductConverter = ProductConverter()

    @Provides
    @Singleton
    fun provideCategoryConverter(): CategoryConverter = CategoryConverter()


    @Provides
    @Singleton
    fun provideProductRepository(
        service: DummyjsonAPI,
        converter: ProductConverter
    ) : ProductRepository {
        return ProductRepositoryImpl(service = service, converter = converter)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        service: DummyjsonAPI,
        converter: CategoryConverter
    ) : CategoryRepository {
        return CategoryRepositoryImpl(service = service, converter = converter)
    }

}