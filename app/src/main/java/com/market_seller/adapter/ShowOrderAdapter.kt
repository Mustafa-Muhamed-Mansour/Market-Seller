package com.market_seller.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market_seller.R
import com.market_seller.databinding.ItemShowOrderBinding
import com.market_seller.interfaces.DeleteOrder
import com.market_seller.interfaces.DeleteProduct
import com.market_seller.model.OrderModel

class ShowOrderAdapter(private var orderModel: ArrayList<OrderModel>, private var deleteOrder: DeleteOrder): RecyclerView.Adapter<ShowOrderAdapter.ShowOrderViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowOrderViewHolder
    {
        return ShowOrderViewHolder(ItemShowOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShowOrderViewHolder, position: Int)
    {
        val model = orderModel[position]

        Glide
            .with(holder.itemView.context)
            .load(model.imageProduct)
            .placeholder(R.drawable.ic_new_product)
            .error(R.drawable.ic_new_product)
            .into(holder.bind.imgItemShowOrder)
        holder.bind.txtTitleItemShowOrder.text = model.titleProduct

        if (model.priceType == "Pound")
        {
            holder.bind.txtResultTotalItemShowOrder.text = "Total Price: ${model.resultPrice}" + " £"
        }

        if (model.priceType == "Dollar")
        {
            holder.bind.txtResultTotalItemShowOrder.text = "Total Price: ${model.resultPrice}" + " $"
        }

        if (model.priceType == "Euro")
        {
            holder.bind.txtResultTotalItemShowOrder.text = "Total Price: ${model.resultPrice}" + " €"
        }

        if (model.priceType == "Durham")
        {
            holder.bind.txtResultTotalItemShowOrder.text = "Total Price: ${model.resultPrice}" + "د.إ "
        }

        if (model.priceType == "Rial")
        {
            holder.bind.txtResultTotalItemShowOrder.text = "Total Price: ${model.resultPrice}" + "ر.س "
        }

        if (model.priceType == "Dinar")
        {
            holder.bind.txtResultTotalItemShowOrder.text = "Total Price: ${model.resultPrice}" + "د.ك "
        }

        holder.bind.imgBtnDeleteItemShowOrder.setOnClickListener {

            deleteOrder.deleteOrderOfUser(model)

        }
    }

    override fun getItemCount(): Int
    {
        return orderModel.size
    }

    class ShowOrderViewHolder(binding: ItemShowOrderBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val bind = binding
    }

}