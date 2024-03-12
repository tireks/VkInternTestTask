package com.tirexmurina.vkinterntask.data.remote.product

import com.tirexmurina.vkinterntask.domain.entity.Product

class ProductConverter {

    fun convert(from: ProductModel): Product =
        with(from){
            Product(
                id = id,
                description = description,
                title = title,
                brand = brand,
                rating = rating,
                stock = stock,
                price = price,
                discountPercentage = discountPercentage,
                category = category,
                images = listOf(thumbnail) + images
            )
        }

}