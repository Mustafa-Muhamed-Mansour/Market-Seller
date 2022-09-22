package com.market_seller.interfaces

import com.market_seller.model.ProductModel

interface DeleteProduct
{
    fun deleteOfProduct(productModel: ProductModel)
}