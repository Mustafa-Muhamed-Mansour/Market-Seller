package com.market_seller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market_seller.R
import com.market_seller.databinding.ItemOrderBinding
import com.market_seller.interfaces.Order
import com.market_seller.model.UserModel


class OrderAdapter(private var userModel: ArrayList<UserModel>, private var order: Order): RecyclerView.Adapter<OrderAdapter.OrderViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder
    {
        return OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int)
    {
        val model: UserModel = userModel[position]

        Glide
            .with(holder.itemView.context)
            .load(model.image)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(holder.bind.imgUserItemOrder)
        holder.bind.txtNameItemOrder.text = model.name
        holder.bind.txtPhoneItemOrder.text = model.phone


        holder.bind.txtShowItemOrder.setOnClickListener {

            order.clickOfShowOrder(model)

        }

        holder.bind.imgBtnDeleteItemOrder.setOnClickListener {

            order.clickDeleteOfUser(model)

        }
    }

    override fun getItemCount(): Int
    {
        return userModel.size
    }

    class OrderViewHolder(binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val bind = binding
    }

}