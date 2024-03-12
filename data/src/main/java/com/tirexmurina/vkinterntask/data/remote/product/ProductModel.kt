package com.tirexmurina.vkinterntask.data.remote.product



data class ProductModel(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail : String,
    val images: List<String>,
    val brand : String,
    val rating : String,
    val stock : String,
    val category: String,
    val price : String,
    val discountPercentage : String
)

/*
data class ProductImageModel (
    val picture : String
)*/
