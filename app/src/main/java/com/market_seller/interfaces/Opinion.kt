package com.market_seller.interfaces

import com.market_seller.model.OpinionModel

interface Opinion
{
    fun trueOfOpinion(opinionModel: OpinionModel)
    fun falseOfOpinion(opinionModel: OpinionModel)
    fun toastReplyOfOpinion()
}