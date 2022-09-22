package com.market_seller.interfaces

import com.market_seller.model.OrderModel

interface DeleteOrder
{
    fun deleteOrderOfUser(orderModel: OrderModel)
}