package com.market_seller.showOrder

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.market_seller.adapter.ShowOrderAdapter
import com.market_seller.databinding.FragmentShowOrderBinding
import com.market_seller.interfaces.DeleteOrder
import com.market_seller.interfaces.DeleteProduct
import com.market_seller.model.OrderModel

class ShowOrderFragment : BottomSheetDialogFragment(), DeleteOrder
{

    private lateinit var binding: FragmentShowOrderBinding
    private lateinit var viewModel: ShowOrderViewModel
    private lateinit var userOfData: ShowOrderFragmentArgs
    private lateinit var bottomSheetDialog: BottomSheetDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentShowOrderBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)



        initView()
        clickView()
        initViewModel()
        retrieveViewModel()


    }

    private fun initView()
    {
        binding.loadingShowOrder.visibility = View.VISIBLE
        userOfData = ShowOrderFragmentArgs.fromBundle(requireArguments())
        bottomSheetDialog = BottomSheetDialog(requireContext())
    }

    private fun clickView()
    {
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(requireActivity())[ShowOrderViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveViewModel()
    {
        binding.loadingShowOrder.visibility = View.VISIBLE
        viewModel
            .retrieveOrderOfUser(userOfData.dataOfUser.id)
            .observe(viewLifecycleOwner)
            {

                binding.loadingShowOrder.visibility = View.GONE

                val showOrderAdapter = ShowOrderAdapter(it, this@ShowOrderFragment)
                binding.rVShowOrder.adapter = showOrderAdapter
                binding.rVShowOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rVShowOrder.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                showOrderAdapter.notifyDataSetChanged()
            }
    }

    override fun deleteOrderOfUser(orderModel: OrderModel)
    {
        binding.loadingShowOrder.visibility = View.VISIBLE
        viewModel
            .deleteOrder(userOfData.dataOfUser.id, orderModel.randomKey)
            .observe(viewLifecycleOwner)
            {

                if (it)
                {
                    Toast.makeText(requireContext(), "Done is deleted", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.dismiss()
                }

                else
                {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

}