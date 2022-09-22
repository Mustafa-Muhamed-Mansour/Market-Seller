package com.market_seller.profile

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.market_seller.R
import com.market_seller.databinding.FragmentProfileBinding
import com.market_seller.interfaces.EditProfile
import com.market_seller.model.SellerModel

class ProfileFragment : Fragment() , EditProfile
{

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)



        initViewModel()
        retrieveViewModel()
        clickedView()

    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    private fun retrieveViewModel()
    {
        viewModel
            .retrieveSeller()
            .observe(viewLifecycleOwner)
            {

                Glide
                    .with(requireContext())
                    .load(it.image)
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(binding.imgProfile)
                binding.txtName.text = it.name
                binding.txtPhone.text = it.phone
                binding.txtAddress.text = it.address
            }
    }

    private fun clickedView()
    {
        binding
            .imgBtnLogout
            .setOnClickListener {

                Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_loginFragment)

            }
    }

    override fun editOfProfile(sellerModel: SellerModel)
    {
        binding
            .btnEdit
            .setOnClickListener {

                Toast.makeText(requireContext(), sellerModel.name, Toast.LENGTH_SHORT).show()

            }
    }
}