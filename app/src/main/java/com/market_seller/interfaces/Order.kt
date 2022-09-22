package com.market_seller.interfaces

import com.market_seller.model.UserModel

interface Order
{
    fun clickOfShowOrder(userModel: UserModel)
    fun clickDeleteOfUser(userModel: UserModel)
}