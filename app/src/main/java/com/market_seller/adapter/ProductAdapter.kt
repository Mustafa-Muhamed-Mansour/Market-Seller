package com.market_seller.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.market_seller.R
import com.market_seller.databinding.ItemProductBinding
import com.market_seller.interfaces.DeleteProduct
import com.market_seller.model.ProductModel

class ProductAdapter(private var productModel: ArrayList<ProductModel>, private var deleteProduct: DeleteProduct): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder
    {
        return ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int)
    {
        val model: ProductModel = productModel[position]

        Glide
            .with(holder.itemView.context)
            .load(model.image)
            .placeholder(R.drawable.ic_new_product)
            .error(R.drawable.ic_new_product)
            .into(holder.bind.imgItemProduct)
        holder.bind.txtTitleItemProduct.text = model.title

        if (model.priceType == "Pound")
        {
            holder.bind.txtPriceItemProduct.text = "${model.price} £"
        }

        if (model.priceType == "Dollar")
        {
            holder.bind.txtPriceItemProduct.text = "${model.price} $"
        }

        if (model.priceType == "Euro")
        {
            holder.bind.txtPriceItemProduct.text = "${model.price} €"
        }

        if (model.priceType == "Durham")
        {
            holder.bind.txtPriceItemProduct.text = "${model.price}د.إ "
        }

        if (model.priceType == "Rial")
        {
            holder.bind.txtPriceItemProduct.text = "${model.price}ر.س "
        }

        if (model.priceType == "Dinar")
        {
            holder.bind.txtPriceItemProduct.text = "${model.price}د.ك "
        }

        holder.bind.txtTypeProductItemProduct.text = model.typeProduct
        holder.bind.txtQuantityItemProduct.text = "Quantity: ${model.quantity}"

        holder.itemView.setOnClickListener {

            deleteProduct.deleteOfProduct(model)
        }
    }

    override fun getItemCount(): Int
    {
        return productModel.size
    }

    class ProductViewHolder(binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val bind = binding
    }
}