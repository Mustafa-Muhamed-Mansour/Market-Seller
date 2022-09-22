package com.market_seller.model


class OpinionModel
{

    lateinit var id: String
    lateinit var randomKey: String
    lateinit var nameUser: String
    lateinit var imageUser: String
    lateinit var commentUser: String
    var numberTrue: Int?= null
    var numberFalse: Int?= null


    constructor()
    {
    }

    constructor(id: String, randomKey: String, nameUser: String, imageUser: String, commentUser: String, numberTrue: Int?, numberFalse: Int?)
    {
        this.id = id
        this.randomKey = randomKey
        this.nameUser = nameUser
        this.imageUser = imageUser
        this.commentUser = commentUser
        this.numberTrue = numberTrue
        this.numberFalse = numberFalse
    }


}