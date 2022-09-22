package com.market_seller.order

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.market_seller.adapter.OrderAdapter
import com.market_seller.databinding.FragmentOrderBinding
import com.market_seller.interfaces.Order
import com.market_seller.model.UserModel
import com.market_seller.showOrder.ShowOrderFragment

class OrderFragment : Fragment(), Order
{

    private lateinit var binding: FragmentOrderBinding
    private lateinit var viewModel: OrderViewModel
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var showOrderFragment: ShowOrderFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)



        initView()
        clickedView()
        initViewModel()
        retrieveViewModel()


    }

    private fun initView()
    {
        bottomSheetDialog = BottomSheetDialog(requireContext())
        showOrderFragment = ShowOrderFragment()
    }

    private fun clickedView()
    {
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveViewModel()
    {
        binding.loadingOrder.visibility = View.VISIBLE
        viewModel
            .retrieveUser()
            .observe(viewLifecycleOwner)
            {

                binding.loadingOrder.visibility = View.GONE

                val orderAdapter = OrderAdapter(it, this@OrderFragment)
                binding.rVOrder.adapter = orderAdapter
                binding.rVOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rVOrder.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                orderAdapter.notifyDataSetChanged()

            }
    }


    override fun clickOfShowOrder(userModel: UserModel)
    {
        val dataOfUser = OrderFragmentDirections.actionOrderFragmentToShowOrderFragment(userModel)
        Navigation.findNavController(requireView()).navigate(dataOfUser)
    }

    override fun clickDeleteOfUser(userModel: UserModel)
    {
        viewModel
            .deleteUser(userModel.id)
            .observe(viewLifecycleOwner)
            {

                if (it)
                {
                    Toast.makeText(requireContext(), "Done user is deleted", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

            }
    }
}