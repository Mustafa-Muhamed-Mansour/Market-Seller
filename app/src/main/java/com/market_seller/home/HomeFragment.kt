package com.market_seller.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.market_seller.R
import com.market_seller.adapter.ProductAdapter
import com.market_seller.databinding.FragmentHomeBinding
import com.market_seller.interfaces.DeleteProduct
import com.market_seller.model.ProductModel

class HomeFragment : Fragment(), DeleteProduct
{

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        initViewModel()
        retrieveViewModel()

    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveViewModel()
    {
        binding.loadingProduct.visibility = View.VISIBLE
        viewModel
            .retrieveProduct()
            .observe(viewLifecycleOwner)
            {

                if (it == null)
                {
                    binding.loadingProduct.visibility = View.VISIBLE
                }

                else
                {
                    binding.loadingProduct.visibility = View.GONE

                    val productAdapter = ProductAdapter(it, this@HomeFragment)
                    binding.rVProduct.adapter = productAdapter
                    binding.rVProduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.rVProduct.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                    productAdapter.notifyDataSetChanged()
                }
            }
    }

    override fun deleteOfProduct(productModel: ProductModel)
    {
        val popupMenu = PopupMenu(requireContext(), requireView())
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {

            when (it.itemId)
            {
                R.id.delete_menu ->
                    viewModel
                    .deleteOfProduct(productModel.randomKey)
                    .observe(viewLifecycleOwner)
                    { booleanProduct ->

                        if (booleanProduct)
                        {
                            Toast.makeText(requireContext(), "Done, is deleted", Toast.LENGTH_SHORT).show()
                        }

                        else
                        {
                            Toast.makeText(requireContext(), "try again later", Toast.LENGTH_SHORT).show()
                        }
                    }

                else -> Toast.makeText(requireContext(), "Soon", Toast.LENGTH_SHORT).show()
            }

            true
        }

        popupMenu.show()


    }

}