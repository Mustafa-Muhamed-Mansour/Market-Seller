package com.market_seller.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.market_seller.R
import com.market_seller.common.Validation
import com.market_seller.databinding.FragmentLoginBinding

class LoginFragment : Fragment()
{

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var validation: Validation


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        validation = Validation()
    }

    private fun clickedView()
    {
        binding
            .btnRegister
            .setOnClickListener {

                Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
            }

        binding
            .txtForgetPassword
            .setOnClickListener {

                Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
            }
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
    }

    private fun retrieveViewModel()
    {
        binding
            .btnLogin
            .setOnClickListener {

                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()

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

                else
                {
                    viewModel
                        .loginSeller(email, password)
                        .observe(viewLifecycleOwner)
                        {
                            if (it)
                            {
                                Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
                                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                            }

                            else
                            {
                                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
    }


}