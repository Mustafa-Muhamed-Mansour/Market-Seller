package com.market_seller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market_seller.R
import com.market_seller.databinding.ItemOpinionBinding
import com.market_seller.interfaces.Opinion
import com.market_seller.model.OpinionModel

class OpinionAdapter(private var opinionModel: ArrayList<OpinionModel>, private var opinion: Opinion): RecyclerView.Adapter<OpinionAdapter.OpinionViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpinionViewHolder
    {
        return OpinionViewHolder(ItemOpinionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OpinionViewHolder, position: Int)
    {
        val model:OpinionModel = opinionModel[position]

        Glide
            .with(holder.itemView.context)
            .load(model.imageUser)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .into(holder.bind.imgItemOpinion)
        holder.bind.txtNameItemOpinion.text = model.nameUser
        holder.bind.txtOpinionItemOpinion.text = model.commentUser
        holder.bind.txtNumberTrueItemOpinion.text = model.numberTrue.toString()
        holder.bind.txtNumberFalseItemOpinion.text = model.numberFalse.toString()

        holder.bind.imgTrueItemOpinion.setOnClickListener {

            if (model.numberTrue!!.toInt() == 0)
            {
                opinion.trueOfOpinion(model)
            }

            else
            {
//                holder.bind.imgFalseItemOpinion.isClickable = false
                holder.bind.imgTrueItemOpinion.isClickable = false
            }

        }

        holder.bind.imgFalseItemOpinion.setOnClickListener {

            if (model.numberFalse == 0)
            {
                opinion.falseOfOpinion(model)
            }

            else
            {
//                holder.bind.imgTrueItemOpinion.isClickable = false
                holder.bind.imgFalseItemOpinion.isClickable = false
            }
        }

        holder.bind.imgReplyItemOpinion.setOnClickListener {

            opinion.toastReplyOfOpinion()

        }
    }

    override fun getItemCount(): Int
    {
        return opinionModel.size
    }

    class OpinionViewHolder(binding: ItemOpinionBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val bind = binding
    }
}