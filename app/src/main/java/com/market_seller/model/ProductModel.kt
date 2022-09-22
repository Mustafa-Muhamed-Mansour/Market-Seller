package com.market_seller.model

class ProductModel
{

    lateinit var id: String
    lateinit var randomKey: String
    lateinit var image: String
    lateinit var title: String
    lateinit var price: String
    lateinit var priceType: String
    lateinit var typeProduct: String
    lateinit var quantity: String

    constructor()
    {
    }


    constructor(id: String, randomKey: String, image: String, title: String, price: String, priceType: String, typeProduct: String, quantity: String)
    {
        this.id = id
        this.randomKey = randomKey
        this.image = image
        this.title = title
        this.price = price
        this.priceType = priceType
        this.typeProduct = typeProduct
        this.quantity = quantity
    }


}