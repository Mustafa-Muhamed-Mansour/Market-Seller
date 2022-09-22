package com.market_seller.common

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

class Validation
{

    fun checkEmail(context: Context, email: String): String
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
        }

        return email
    }

    fun checkPassword(context: Context, password: String): String
    {
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
        }

        return password
    }

    fun checkName(context: Context, name: String): String
    {
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
        }

        return name
    }

    fun checkPhoneNumber(context: Context, phone: String): String
    {
        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(context, "Please enter your Phone Number", Toast.LENGTH_SHORT).show()
        }

        return phone
    }

    fun checkShoppingAddress(context: Context, shoppingAddress: String): String
    {
        if (TextUtils.isEmpty(shoppingAddress))
        {
            Toast.makeText(context, "Please enter your Shopping Location Address", Toast.LENGTH_SHORT).show()
        }

        return shoppingAddress
    }

    fun checkTitle(context: Context, title: String): String
    {
        if (TextUtils.isEmpty(title))
        {
            Toast.makeText(context, "Please enter a title", Toast.LENGTH_SHORT).show()
        }

        return title
    }

    fun checkPrice(context: Context, price: String): String
    {
        if (TextUtils.isEmpty(price))
        {
            Toast.makeText(context, "Please enter a price", Toast.LENGTH_SHORT).show()
        }

        return price
    }

    fun checkQuantity(context: Context, quantity: Int): Int
    {

        if (quantity == 0)
        {
            Toast.makeText(context, "Please enter a quantity of product", Toast.LENGTH_SHORT).show()
        }

        return quantity
    }

    fun checkReply(context: Context, reply: String): String
    {
        if (TextUtils.isEmpty(reply))
        {
            Toast.makeText(context, "Please enter a reply of opinion", Toast.LENGTH_SHORT).show()
        }

        return reply
    }
}