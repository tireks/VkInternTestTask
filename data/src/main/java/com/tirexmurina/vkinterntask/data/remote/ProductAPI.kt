package com.tirexmurina.vkinterntask.data.remote

import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ProductAPI {

    @GET("products")
    suspend fun getProducts(
        @QueryMap parameters : Map<String, String>
    ) : ProductsResponse

    @GET
    suspend fun getProductById(
        @Url url: String
    ) : ProductModel


}