package com.tirexmurina.vkinterntask.domain.entity

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val images : List<String>,
    val brand : String,
    val rating : String,
    val stock : String,
    val category: String,
    val price : String,
    val discountPercentage : String
)