package com.tirexmurina.vkinterntask.data.remote

import retrofit2.http.GET

interface ProductAPI {

    @GET("products")
    suspend fun getTest() : ProductResponse

}