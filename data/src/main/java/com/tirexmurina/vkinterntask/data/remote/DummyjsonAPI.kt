package com.tirexmurina.vkinterntask.data.remote

import com.tirexmurina.vkinterntask.data.remote.category.CategoryModel
import com.tirexmurina.vkinterntask.data.remote.product.ProductModel
import com.tirexmurina.vkinterntask.data.remote.product.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface DummyjsonAPI {

    @GET("products")
    suspend fun getProducts(
        @QueryMap parameters : Map<String, String>
    ) : ProductsResponse

    @GET
    suspend fun getProductById(
        @Url url: String
    ) : ProductModel

    @GET
    suspend fun getProductsByCategory(
        @Url url: String, @QueryMap parameters : Map<String, String>
    ) : ProductsResponse

    @GET("products/categories")
    suspend fun getCategories() : List<CategoryModel>



}