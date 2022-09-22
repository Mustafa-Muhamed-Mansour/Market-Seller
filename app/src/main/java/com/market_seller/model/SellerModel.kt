package com.market_seller.model

class SellerModel
{
    lateinit var id: String
    lateinit var randomKey: String
    lateinit var image: String
    lateinit var email: String
    lateinit var name: String
    lateinit var phone: String
    lateinit var address: String

    constructor()
    {
    }

    constructor(id: String, randomKey: String, image: String, email: String, name: String, phone: String, address: String)
    {
        this.id = id
        this.randomKey = randomKey
        this.image = image
        this.email = email
        this.name = name
        this.phone = phone
        this.address = address
    }


}