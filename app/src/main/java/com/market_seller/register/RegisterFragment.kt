package com.market_seller.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.market_seller.R
import com.market_seller.common.Validation
import com.market_seller.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment()
{

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var someLauncher: ActivityResultLauncher<Intent>
    private lateinit var resultUri: Uri
    private lateinit var image: String
    private lateinit var validation: Validation



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)



        initView()
        clickedView()
        initViewModel()


    }

    private fun initView()
    {
        someLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            val dataIntent = it.data!!

            if (it.resultCode == Activity.RESULT_OK && dataIntent.data != null)
            {
                resultUri = dataIntent.data!!
                image = resultUri.toString()
                binding.imgLocation.setImageURI(resultUri)
            }

            else
            {
                Toast.makeText(requireContext(), "Please choose your image", Toast.LENGTH_SHORT).show()
            }
        }
        validation = Validation()
    }

    private fun clickedView()
    {
        binding
            .imgLocation
            .setOnClickListener {

                openGallery()
            }

        binding
            .btnLogin
            .setOnClickListener {

                Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
            }

        binding
            .btnRegister
            .setOnClickListener {

                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()
                val name = binding.editName.text.toString()
                val phone = binding.editPhone.text.toString()
                val address = binding.editAddress.text.toString()

                if (validation.checkEmail(requireContext(), email).none())
                {
//                    validation.checkEmail(requireContext(), email)
                    binding.editEmail.requestFocus()
                    return@setOnClickListener
                }

                if (validation.checkPassword(requireContext(), password).none())
                {
//                    validation.checkPassword(requireContext(), password)
                    binding.editPassword.requestFocus()
                    return@setOnClickListener
                }

                if (validation.checkName(requireContext(), name).none())
                {
//                    validation.checkName(requireContext(), name)
                    binding.editName.requestFocus()
                    return@setOnClickListener
                }

                if (validation.checkPhoneNumber(requireContext(), phone).none())
                {
//                    validation.checkPhoneNumber(requireContext(), phone)
                    binding.editPhone.requestFocus()
                    return@setOnClickListener
                }

                if (validation.checkShoppingAddress(requireContext(), address).none())
                {
//                    validation.checkShoppingAddress(requireContext(), address)
                    binding.editAddress.requestFocus()
                    return@setOnClickListener
                }

                else
                {
                    retrieveViewModel(image, email, password, name, phone, address)
                }
            }
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    private fun retrieveViewModel(image: String, email: String, password: String, name: String, phone: String, address: String)
    {
        viewModel
            .registerSeller(image, email, password, name, phone, address)
            .observe(viewLifecycleOwner)
            {

                if (it)
                {
                    Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
                }

                else
                {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun openGallery()
    {
        val imageIntent = Intent(Intent.ACTION_PICK)
        imageIntent.type = "image/*"
        someLauncher.launch(imageIntent)
    }
}