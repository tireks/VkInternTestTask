package com.tirexmurina.vkinterntask.data.remote

import com.tirexmurina.vkinterntask.domain.entity.Product

class ProductConverter {
    /*fun convert(from: List<ProductModel>): List<Product> {
        with(from){
            forEach {
                Product(
                    id = it.id,
                    description = it.description,
                    thumbnail = it.thumbnail,
                    title = it.title
                )
            }
        }
    }*/

    fun convert(from: ProductModel): Product =
        with(from){
            Product(
                id = id,
                description = description,
                thumbnail = thumbnail,
                title = title
            )
        }



}