package com.tirexmurina.vkinterntask.data.remote

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ProductAPI {

    @GET("products")
    suspend fun getProducts(
        @QueryMap parameters : Map<String, String>
    ) : ProductResponse
    /* suspend fun getTest() : ProductResponse*/


}